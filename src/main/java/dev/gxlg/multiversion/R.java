package dev.gxlg.multiversion;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings({ "unused" })
public class R {

    private static final Map<Integer, Class<?>> clazzCache = new HashMap<>();

    private static final Map<Integer, Constructor<?>> constructorsCache = new HashMap<>();

    private static final Map<Integer, Method> methodsCache = new HashMap<>();

    private static final Map<Integer, Field> fieldsCache = new HashMap<>();

    private static <T> T cache(Map<Integer, T> cache, Object base, Object[] lookup, Supplier<T> supplier) {
        return cache.computeIfAbsent(Objects.hash(base, Arrays.hashCode(lookup)), i -> supplier.get());
    }

    private static Class<?>[] types(Object[] types) {
        Class<?>[] array = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            array[i] = type(types[i]);
        }
        return array;
    }

    private static Class<?> type(Object type) {
        Class<?> c;
        if (type instanceof Class<?> clz) {
            c = clz;
        } else if (type instanceof RClass rclz) {
            c = rclz.self();
        } else if (type instanceof String str) {
            c = clz(str).self();
        } else {
            throw new RuntimeException("Is not a valid Type: " + type.getClass());
        }
        return c;
    }

    public static RClass clz(String names) {
        return new RClass(names);
    }

    public static RClass clz(Class<?> clz) {
        return new RClass(clz);
    }

    @SuppressWarnings("unchecked")
    public static <T> Function<Object, T[]> arrayWrapper(Function<Object, T> wrapperT) {
        return obj -> (T[]) Stream.of((Object[]) obj).map(wrapperT).toArray();
    }

    public static <T> Function<T[], Object> arrayUnwrapper(Function<T, Object> unwrapperT) {
        return wrap -> Stream.of(wrap).map(unwrapperT).toArray();
    }

    public static boolean methodMatches(Method method, Object returnType, Object... types) {
        Class<?> type = type(returnType);
        Class<?>[] params = types(types);
        Class<?>[] methodParams = method.getParameterTypes();
        if (params.length != methodParams.length) {
            return false;
        }
        if (!type.isAssignableFrom(method.getReturnType())) {
            return false;
        }
        for (int i = 0; i < params.length; i++) {
            if (!methodParams[i].isAssignableFrom(params[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean fieldMatches(Field field, Object fieldType) {
        Class<?> type = type(fieldType);
        return type.isAssignableFrom(field.getType());
    }

    @SuppressWarnings("resource")
    public static <T extends RWrapper<?>> RClass extendWrapper(Class<T> superClass, Class<? extends T> extendingWrapper) {
        try {
            Class<?> superClz = ((RClass) clz(superClass).fld("clazz", RClass.class).get()).self();
            Class<?> intercept = superClass.getDeclaredClasses()[0];
            return R.clz(new ByteBuddy().subclass(superClz).name(extendingWrapper.getName() + "Impl").defineField("__wrapper", extendingWrapper, Visibility.PUBLIC)
                                        .method(ElementMatchers.isVirtual().and(ElementMatchers.not(ElementMatchers.isFinalizer()))).intercept(MethodDelegation.to(intercept)).make()
                                        .load(superClz.getClassLoader()).getLoaded());
        } catch (Exception e) {
            throw new RuntimeException("Failed to extend class", e);
        }
    }

    public interface RWrapperInterface<T extends RWrapper<T>> {
        T wrapper();
    }

    public static class RClass {
        private final Supplier<Class<?>> lazyClz;

        private Class<?> clz = null;

        private RClass(String names) {
            lazyClz = () -> {
                String[] classes = names.split("/");
                return cache(
                    clazzCache, null, classes, () -> {
                        for (String clazz : classes) {
                            try {
                                return Thread.currentThread().getContextClassLoader().loadClass(clazz);
                            } catch (ClassNotFoundException ignored) {
                            }
                        }
                        throw new RuntimeException("Class not found from " + Arrays.toString(classes));
                    }
                );
            };
        }

        private RClass(Class<?> clz) {
            lazyClz = () -> clz;
        }

        public RInstance inst(Object inst) {
            try {
                return new RInstance(self(), self().cast(inst));
            } catch (ClassCastException e) {
                throw new RuntimeException("Object is not of type " + self().getName() + ", instead: " + inst.getClass().getName());
            }
        }

        public RConstructor constr(Object... types) {
            return new RConstructor(self(), types);
        }

        public RDeclConstructor dconstr(Object... types) {
            return new RDeclConstructor(self(), types);
        }

        public RField fld(String names, Object type) {
            return new RField(null, names, self(), type);
        }

        public RDeclField dfld(String names, Object type) {
            return new RDeclField(null, names, self(), type);
        }

        public RMethod mthd(String names, Object returnType, Object... types) {
            return new RMethod(null, names, self(), returnType, types);
        }

        public RDeclMethod dmthd(String names, Object returnType, Object... types) {
            return new RDeclMethod(null, names, self(), returnType, types);
        }

        public Class<?> self() {
            if (clz == null) {
                clz = lazyClz.get();
            }
            return clz;
        }

        public RClass arrayType() {
            return clz(self().arrayType());
        }
    }

    public static class RInstance {
        private final Class<?> clz;

        private final Object inst;

        private RInstance(Class<?> clz, Object inst) {
            this.clz = clz;
            this.inst = inst;
        }

        public RField fld(String names, Object type) {
            return new RField(inst, names, clz, type);
        }

        public RDeclField dfld(String names, Object type) {
            return new RDeclField(inst, names, clz, type);
        }

        public RMethod mthd(String names, Object returnType, Object... types) {
            return new RMethod(inst, names, clz, returnType, types);
        }

        public RDeclMethod dmthd(String names, Object returnType, Object... types) {
            return new RDeclMethod(inst, names, clz, returnType, types);
        }

        public Object self() {
            return inst;
        }
    }

    public static class RMethod {
        private final Object inst;

        private final Supplier<Method> lazyMethod;

        private Method method = null;

        public RMethod(Object inst, String names, Class<?> clz, Object returnType, Object[] types) {
            this.inst = inst;
            this.lazyMethod = () -> {
                String[] methodNames = names.split("/");
                return cache(
                    methodsCache, clz, methodNames, () -> {
                        for (Method method : clz.getMethods()) {
                            if (Arrays.stream(methodNames).anyMatch(n -> method.getName().equals(n)) && methodMatches(method, returnType, types)) {
                                return method;
                            }
                        }
                        throw new RuntimeException(
                            "Method not found from " + Arrays.toString(methodNames) + " for class " + clz.getName() + " with params " + Arrays.toString(types(types)) + " and return type " +
                            type(returnType));
                    }
                );
            };
        }

        public Object invk(Object... args) {
            try {
                return self().invoke(inst, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public Method self() {
            if (method == null) {
                method = lazyMethod.get();
            }
            return method;
        }
    }

    public static class RDeclMethod {
        private final Object inst;

        private final Supplier<Method> lazyMethod;

        private Method method = null;

        public RDeclMethod(Object inst, String names, Class<?> clz, Object returnType, Object[] types) {
            this.inst = inst;
            this.lazyMethod = () -> {
                String[] methodNames = names.split("/");
                return cache(
                    methodsCache, clz, methodNames, () -> {
                        for (Method method : clz.getDeclaredMethods()) {
                            if (Arrays.stream(methodNames).anyMatch(n -> method.getName().equals(n)) && methodMatches(method, returnType, types)) {
                                return method;
                            }
                        }
                        throw new RuntimeException(
                            "Method not found from " + Arrays.toString(methodNames) + " for class " + clz.getName() + " with params " + Arrays.toString(types(types)) + " and return type " +
                            type(returnType));
                    }
                );
            };
        }

        public Object invk(Object... args) {
            try {
                return self().invoke(inst, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public Method self() {
            if (method == null) {
                method = lazyMethod.get();
                method.setAccessible(true);
            }
            return method;
        }
    }

    public static class RField {
        private final Object inst;

        private final Supplier<Field> lazyField;

        private Field fld = null;

        public RField(Object inst, String names, Class<?> clz, Object fieldType) {
            this.inst = inst;
            this.lazyField = () -> {
                String[] fieldNames = names.split("/");
                return cache(
                    fieldsCache, clz, fieldNames, () -> {
                        for (Field field : clz.getFields()) {
                            if (Arrays.stream(fieldNames).anyMatch(n -> field.getName().equals(n)) && fieldMatches(field, fieldType)) {
                                return field;
                            }
                        }
                        throw new RuntimeException("Field not found from " + Arrays.toString(fieldNames) + " for class " + clz.getName() + " of type " + type(fieldType));
                    }
                );
            };
        }

        public void set(Object value) {
            try {
                self().set(inst, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public Object get() {
            try {
                return self().get(inst);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public Field self() {
            if (fld == null) {
                fld = lazyField.get();
            }
            return fld;
        }
    }

    public static class RDeclField {
        private final Object inst;

        private final Supplier<Field> lazyField;

        private Field fld = null;

        public RDeclField(Object inst, String names, Class<?> clz, Object fieldType) {
            this.inst = inst;
            this.lazyField = () -> {
                String[] fieldNames = names.split("/");
                return cache(
                    fieldsCache, clz, fieldNames, () -> {
                        for (Field field : clz.getDeclaredFields()) {
                            if (Arrays.stream(fieldNames).anyMatch(n -> field.getName().equals(n)) && fieldMatches(field, fieldType)) {
                                return field;
                            }
                        }
                        throw new RuntimeException("Field not found from " + Arrays.toString(fieldNames) + " for class " + clz.getName() + " of type " + type(fieldType));
                    }
                );
            };
        }

        public Object get() {
            try {
                return self().get(inst);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public Field self() {
            if (fld == null) {
                fld = lazyField.get();
                fld.setAccessible(true);
            }
            return fld;
        }
    }

    public static class RConstructor {
        private final Class<?> clz;

        private final Supplier<Constructor<?>> lazyConstr;

        private Constructor<?> constr = null;

        private RConstructor(Class<?> clz, Object... types) {
            this.clz = clz;
            this.lazyConstr = () -> {
                Class<?>[] params = types(types);
                return cache(
                    constructorsCache, clz, params, () -> {
                        try {
                            return clz.getConstructor(params);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException("Constructor not found for class " + clz.getName() + " with args " + Arrays.toString(params));
                        }
                    }
                );
            };
        }

        public RInstance newInst(Object... args) {
            try {
                return new RInstance(clz, self().newInstance(args));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public Constructor<?> self() {
            if (constr == null) {
                constr = lazyConstr.get();
            }
            return constr;
        }
    }

    public static class RDeclConstructor {
        private final Class<?> clz;

        private final Supplier<Constructor<?>> lazyConstr;

        private Constructor<?> constr = null;

        private RDeclConstructor(Class<?> clz, Object... types) {
            this.clz = clz;
            this.lazyConstr = () -> {
                Class<?>[] params = types(types);
                return cache(
                    constructorsCache, clz, params, () -> {
                        try {
                            return clz.getDeclaredConstructor(params);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException("Constructor not found for class " + clz.getName() + " with args " + Arrays.toString(params));
                        }
                    }
                );
            };
        }

        public RInstance newInst(Object... args) {
            try {
                return new RInstance(clz, self().newInstance(args));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public Constructor<?> self() {
            if (constr == null) {
                constr = lazyConstr.get();
                constr.setAccessible(true);
            }
            return constr;
        }
    }

    public static abstract class RWrapper<S extends RWrapper<S>> {
        protected final Object instance;

        protected RWrapper(Object instance) {
            if (instance == null) {
                throw new RuntimeException("Cannot wrap null instance");
            }
            this.instance = instance;
        }

        public Object unwrap() {
            return instance;
        }

        public <T> T unwrap(Class<T> clz) {
            return clz.cast(instance);
        }

        public <T extends S> boolean isInstanceOf(Class<T> wrapperType) {
            return ((RClass) clz(wrapperType).fld("clazz", RClass.class).get()).self().isAssignableFrom(instance.getClass());
        }

        public <T extends S> T downcast(Class<T> wrapperType) {
            try {
                return wrapperType.cast(((RClass) clz(wrapperType).fld("clazz", RClass.class).get()).inst(instance).fld("__wrapper", RWrapper.class).get());
            } catch (Exception ignored) {
                return wrapperType.cast(clz(wrapperType).mthd("inst", RWrapper.class, Object.class).invk(instance));
            }
        }

        public boolean equals(S wrapper) {
            if (wrapper == null) {
                return false;
            }
            return Objects.equals(instance, wrapper.instance);
        }

        public static class Interceptor {
            @RuntimeType
            public static Object intercept(
                @Origin Method method, @FieldValue(
                    "__wrapper"
                ) RWrapper<?> wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall
            ) throws Exception {
                return superCall.call();
            }
        }
    }
}
