package dev.gxlg.multiversion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

        public RField fld(String names) {
            return new RField(null, names, self());
        }

        public RMethod mthd(String names, Object... types) {
            return new RMethod(null, names, self(), types);
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

        public RField fld(String names) {
            return new RField(inst, names, clz);
        }

        public RMethod mthd(String names, Object... types) {
            return new RMethod(inst, names, clz, types);
        }

        public Object self() {
            return inst;
        }
    }

    public static class RMethod {
        private final Object inst;

        private final Supplier<Method> lazyMethod;

        private Method method = null;

        public RMethod(Object inst, String names, Class<?> clz, Object[] types) {
            this.inst = inst;
            this.lazyMethod = () -> {
                String[] methods = names.split("/");
                Class<?>[] params = types(types);
                return cache(
                    methodsCache, clz, methods, () -> {
                        for (String method : methods) {
                            try {
                                return clz.getMethod(method, params);
                            } catch (NoSuchMethodException ignored) {
                            }
                        }
                        throw new RuntimeException("Method not found from " + Arrays.toString(methods) + " for class " + clz.getName() + " with params " + Arrays.toString(params));
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

    public static class RField {
        private final Object inst;

        private final Supplier<Field> lazyField;

        private Field fld = null;

        public RField(Object inst, String names, Class<?> clz) {
            this.inst = inst;
            this.lazyField = () -> {
                String[] fields = names.split("/");
                return cache(
                    fieldsCache, clz, fields, () -> {
                        for (String field : fields) {
                            try {
                                return clz.getField(field);
                            } catch (NoSuchFieldException ignored) {
                            }
                        }
                        throw new RuntimeException("Field not found from " + Arrays.toString(fields) + " for class " + clz.getName());
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

    public static abstract class RWrapper<S extends RWrapper<S>> {
        protected final Object instance;

        protected RWrapper(Object instance) {
            this.instance = instance;
        }

        public Object unwrap() {
            return instance;
        }

        public <T> T unwrap(Class<T> type) {
            return type.cast(instance);
        }

        public boolean isNull() {
            return instance == null;
        }

        public <T extends S> T downcast(Class<T> wrapperType) {
            return wrapperType.cast(R.clz(wrapperType).mthd("inst", Object.class).invk(instance));
        }

        public boolean equals(S wrapper) {
            return Objects.equals(instance, wrapper.instance);
        }
    }
}
