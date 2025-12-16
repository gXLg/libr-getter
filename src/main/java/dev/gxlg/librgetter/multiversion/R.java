package dev.gxlg.librgetter.multiversion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
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
            Object type = types[i];
            Class<?> c;
            if (type instanceof Class<?> clz) {
                c = clz;
            } else if (type instanceof RClass rclz) {
                c = rclz.clz;
            } else if (type instanceof String str) {
                c = clz(str).clz;
            } else {
                throw new RuntimeException("Is not a valid Type: " + type.getClass());
            }
            array[i] = c;
        }
        return array;
    }

    public static RClass clz(String names) {
        String[] classes = names.split("/");
        Class<?> clz = cache(clazzCache, null, classes, () -> {
            for (String clazz : classes) {
                try {
                    return Thread.currentThread().getContextClassLoader().loadClass(clazz);
                } catch (ClassNotFoundException ignored) {
                }
            }
            throw new RuntimeException("Class not found from " + Arrays.toString(classes));
        });
        return clz(clz);
    }

    public static RClass clz(Class<?> clz) {
        return new RClass(clz);
    }

    public static class RClass {
        private final Class<?> clz;

        private RClass(Class<?> clz) {
            this.clz = clz;
        }

        public RInstance inst(Object inst) {
            try {
                return new RInstance(clz, clz.cast(inst));
            } catch (ClassCastException e) {
                throw new RuntimeException("Object is not of type " + clz.getName() + ", instead: " + inst.getClass().getName());
            }
        }

        public RConstructor constr(Object... types) {
            Class<?>[] params = types(types);
            Constructor<?> constr = cache(constructorsCache, clz, params, () -> {
                try {
                    return clz.getConstructor(params);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Constructor not found for class " + clz.getName() + " with args " + Arrays.toString(params));
                }
            });
            return new RConstructor(clz, constr);
        }

        public RField fld(String names) {
            String[] fields = names.split("/");
            Field fld = cache(fieldsCache, clz, fields, () -> {
                for (String field : fields) {
                    try {
                        return clz.getField(field);
                    } catch (NoSuchFieldException ignored) {
                    }
                }
                throw new RuntimeException("Field not found from " + Arrays.toString(fields) + " for class " + clz.getName());
            });
            return new RField(null, fld);
        }

        public RMethod mthd(String names, Object... types) {
            String[] methods = names.split("/");
            Class<?>[] params = types(types);
            Method mthd = cache(methodsCache, clz, methods, () -> {
                for (String method : methods) {
                    try {
                        return clz.getMethod(method, params);
                    } catch (NoSuchMethodException ignored) {
                    }
                }
                throw new RuntimeException("Method not found from " + Arrays.toString(methods) + " for class " + clz.getName() + " with params " + Arrays.toString(params));
            });
            return new RMethod(null, mthd);
        }

        public Class<?> self() {
            return clz;
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
            String[] fields = names.split("/");
            Field fld = cache(fieldsCache, clz, fields, () -> {
                for (String field : fields) {
                    try {
                        return clz.getField(field);
                    } catch (NoSuchFieldException ignored) {
                    }
                }
                throw new RuntimeException("Field not found from " + Arrays.toString(fields) + " for class " + clz.getName());
            });
            return new RField(inst, fld);
        }

        public RMethod mthd(String names, Object... types) {
            String[] methods = names.split("/");
            Class<?>[] params = types(types);
            Method mthd = cache(methodsCache, clz, methods, () -> {
                for (String method : methods) {
                    try {
                        return clz.getMethod(method, params);
                    } catch (NoSuchMethodException ignored) {
                    }
                }
                throw new RuntimeException("Method not found from " + Arrays.toString(methods) + " for class " + clz.getName() + " with params " + Arrays.toString(params));
            });
            return new RMethod(inst, mthd);
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
}