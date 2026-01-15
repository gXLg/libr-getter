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

    private static RField getField(String names, Class<?> clz, Object inst) {
        String[] fields = names.split("/");
        Field fld = cache(
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
        return new RField(null, fld);
    }

    private static RMethod getMethod(String names, Class<?> clz, Object inst, Object[] types) {
        String[] methods = names.split("/");
        Class<?>[] params = types(types);
        Method mthd = cache(
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
        return new RMethod(inst, mthd);
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
            Class<?>[] params = types(types);
            Constructor<?> constr = cache(
                constructorsCache, self(), params, () -> {
                    try {
                        return self().getConstructor(params);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("Constructor not found for class " + self().getName() + " with args " + Arrays.toString(params));
                    }
                }
            );
            return new RConstructor(self(), constr);
        }

        public RField fld(String names) {
            return getField(names, self(), null);
        }

        public RMethod mthd(String names, Object... types) {
            return getMethod(names, self(), null, types);
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
            return getField(names, clz, inst);
        }

        public RMethod mthd(String names, Object... types) {
            return getMethod(names, clz, inst, types);
        }

        public Object self() {
            return inst;
        }
    }

    public static class RMethod {
        private final Object inst;

        private final Method method;

        public RMethod(Object inst, Method method) {
            this.inst = inst;
            this.method = method;
        }

        public Object invk(Object... args) {
            try {
                return method.invoke(inst, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class RField {
        private final Object inst;

        private final Field fld;

        public RField(Object inst, Field fld) {
            this.inst = inst;
            this.fld = fld;
        }

        public void set(Object value) {
            try {
                fld.set(inst, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public Object get() {
            try {
                return fld.get(inst);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class RConstructor {
        private final Class<?> clz;

        private final Constructor<?> constr;

        private RConstructor(Class<?> clz, Constructor<?> constr) {
            this.clz = clz;
            this.constr = constr;
        }

        public RInstance newInst(Object... args) {
            try {
                return new RInstance(clz, constr.newInstance(args));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static abstract class RWrapper<S extends RWrapper<S>> {
        protected final RInstance instance;

        protected RWrapper(RInstance instance) {
            this.instance = instance;
        }

        public Object unwrap() {
            return instance.self();
        }

        public <T> T unwrap(Class<T> type) {
            return type.cast(instance.self());
        }

        public boolean isNull() {
            return instance.self() == null;
        }

        public <T extends S> T downcast(Class<T> wrapperType) {
            return wrapperType.cast(R.clz(wrapperType).mthd("inst", Object.class).invk(instance.self()));
        }
    }

    public static <T> Function<?, T[]> arrayWrapper(Function<Object, T> func) {
        //noinspection unchecked
        return obj -> (T[]) Stream.of((Object[]) obj).map(func).toArray();
    }

    public static <T> Function<T[], Object> arrayUnwrapper(Function<T, Object> func) {
        return wrap -> Stream.of(wrap).map(func).toArray();
    }
}
