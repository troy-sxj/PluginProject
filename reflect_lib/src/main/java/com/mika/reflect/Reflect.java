package com.mika.reflect;


import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: mika
 * @Time: 2018-11-15 11:04
 * @Description:
 */

public class Reflect {

    // ---------------------------------------------------------------------
    // Static API used as entrance points to the fluent API
    // ---------------------------------------------------------------------
//
//    public static Reflect compile(String name, String content) throws ReflectException {
//        return compile(name, content, new CompileOptions());
//    }
//
//
//    public static Reflect compile(String name, String content, CompileOptions options) throws ReflectException {
//        return on(Compile.compile(name, content, options));
//    }
    /* [/java-8] */

    public static Reflect on(String name) throws ReflectException {
        return on(forName(name));
    }


    public static Reflect on(String name, ClassLoader classLoader) throws ReflectException {
        return on(forName(name, classLoader));
    }

    public static Reflect on(Class<?> clazz) {
        return new Reflect(clazz);
    }

    public static Reflect on(Object object) {
        return new Reflect(object == null ? Object.class : object.getClass(), object);
    }

    private static Reflect on(Class<?> type, Object object) {
        return new Reflect(type, object);
    }

    /**
     * Get the initialisation or default value for any given type.
     * <p>
     * This returns:
     * <ul>
     * <li><code>null</code> for reference types (including wrapper types)</li>
     * <li><code>0</code> for numeric primitive types (including
     * <code>char</code>)</li>
     * <li><code>false</code> for the <code>boolean</code> primitive type.
     * </ul>
     */
    @SuppressWarnings("unchecked")
    public static <T> T initValue(Class<T> type) {
        return type == boolean.class
                ? (T) Boolean.FALSE
                : type == byte.class
                ? (T) Byte.valueOf((byte) 0)
                : type == short.class
                ? (T) Short.valueOf((short) 0)
                : type == int.class
                ? (T) Integer.valueOf(0)
                : type == long.class
                ? (T) Long.valueOf(0L)
                : type == double.class
                ? (T) Double.valueOf(0.0)
                : type == float.class
                ? (T) Float.valueOf(0.0f)
                : type == char.class
                ? (T) Character.valueOf((char) 0)
                : (T) null;
    }


    public static <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) {
            return null;
        }

        if (accessible instanceof Member) {
            Member member = (Member) accessible;

            if (Modifier.isPublic(member.getModifiers()) &&
                    Modifier.isPublic(member.getDeclaringClass().getModifiers())) {

                return accessible;
            }
        }

        // [jOOQ #3392] The accessible flag is set to false by default, also for public members.
        if (!accessible.isAccessible())
            accessible.setAccessible(true);

        return accessible;
    }

    // ---------------------------------------------------------------------
    // Members
    // ---------------------------------------------------------------------

    /* [java-8] */
    static final Constructor<MethodHandles.Lookup> CACHED_LOOKUP_CONSTRUCTOR;

    static {
        Constructor<MethodHandles.Lookup> result;

        /* [java-9] */
        if (true)
            result = null;
        else
            /* [/java-9] */
            try {
                result = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);

                if (!result.isAccessible())
                    result.setAccessible(true);
            }

            // Can no longer access the above in JDK 9
            catch (Throwable ignore) {
                result = null;
            }

        CACHED_LOOKUP_CONSTRUCTOR = result;
    }
    /* [/java-8] */

    /**
     * The type of the wrapped object.
     */
    private final Class<?> type;

    /**
     * The wrapped object.
     */
    private final Object object;

    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------

    private Reflect(Class<?> type) {
        this(type, type);
    }

    private Reflect(Class<?> type, Object object) {
        this.type = type;
        this.object = object;
    }

    // ---------------------------------------------------------------------
    // Fluent Reflection API
    // ---------------------------------------------------------------------

    /**
     * Get the wrapped object
     *
     * @param <T> A convenience generic parameter for automatic unsafe casting
     */
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }

    public Reflect set(String name, Object value) throws ReflectException {
        try {
            Field field = field0(name);

            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                try {
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                }

                // [#48] E.g. Android doesn't have this field
                catch (NoSuchFieldException ignore) {
                }
            }

            field.set(object, unwrap(value));
            return this;
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public <T> T get(String name) throws ReflectException {
        return field(name).<T>get();
    }

    public Reflect field(String name) throws ReflectException {
        try {
            Field field = field0(name);
            return on(field.getType(), field.get(object));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private Field field0(String name) throws ReflectException {
        Class<?> t = type();

        // Try getting a public field
        try {
            return accessible(t.getField(name));
        }

        // Try again, getting a non-public field
        catch (NoSuchFieldException e) {
            do {
                try {
                    return accessible(t.getDeclaredField(name));
                } catch (NoSuchFieldException ignore) {
                }

                t = t.getSuperclass();
            }
            while (t != null);

            throw new ReflectException(e);
        }
    }


    public Map<String, Reflect> fields() {
        Map<String, Reflect> result = new LinkedHashMap<String, Reflect>();
        Class<?> t = type();

        do {
            for (Field field : t.getDeclaredFields()) {
                if (type != object ^ Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();

                    if (!result.containsKey(name))
                        result.put(name, field(name));
                }
            }

            t = t.getSuperclass();
        }
        while (t != null);

        return result;
    }

    public Reflect call(String name) throws ReflectException {
        return call(name, new Object[0]);
    }


    public Reflect call(String name, Object... args) throws ReflectException {
        Class<?>[] types = types(args);

        // Try invoking the "canonical" method, i.e. the one with exact
        // matching argument types
        try {
            Method method = exactMethod(name, types);
            return on(method, object, args);
        }

        // If there is no exact match, try to find a method that has a "similar"
        // signature if primitive argument types are converted to their wrappers
        catch (NoSuchMethodException e) {
            try {
                Method method = similarMethod(name, types);
                return on(method, object, args);
            } catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }

    /**
     * Searches a method with the exact same signature as desired.
     * <p>
     * If a public method is found in the class hierarchy, this method is returned.
     * Otherwise a private method with the exact same signature is returned.
     * If no exact match could be found, we let the {@code NoSuchMethodException} pass through.
     */
    private Method exactMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> t = type();

        // first priority: find a public method with exact signature match in class hierarchy
        try {
            return t.getMethod(name, types);
        }

        // second priority: find a private method with exact signature match on declaring class
        catch (NoSuchMethodException e) {
            do {
                try {
                    return t.getDeclaredMethod(name, types);
                } catch (NoSuchMethodException ignore) {
                }

                t = t.getSuperclass();
            }
            while (t != null);

            throw new NoSuchMethodException();
        }
    }

    private Method similarMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> t = type();

        // first priority: find a public method with a "similar" signature in class hierarchy
        // similar interpreted in when primitive argument types are converted to their wrappers
        for (Method method : t.getMethods()) {
            if (isSimilarSignature(method, name, types)) {
                return method;
            }
        }

        // second priority: find a non-public method with a "similar" signature on declaring class
        do {
            for (Method method : t.getDeclaredMethods()) {
                if (isSimilarSignature(method, name, types)) {
                    return method;
                }
            }

            t = t.getSuperclass();
        }
        while (t != null);

        throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found on type " + type() + ".");
    }

    /**
     * Determines if a method has a "similar" signature, especially if wrapping
     * primitive argument types would result in an exactly matching signature.
     */
    private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    public Reflect create() throws ReflectException {
        return create(new Object[0]);
    }

    public Reflect create(Object... args) throws ReflectException {
        Class<?>[] types = types(args);

        // Try invoking the "canonical" constructor, i.e. the one with exact
        // matching argument types
        try {
            Constructor<?> constructor = type().getDeclaredConstructor(types);
            return on(constructor, args);
        }

        // If there is no exact match, try to find one that has a "similar"
        // signature if primitive argument types are converted to their wrappers
        catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : type().getDeclaredConstructors()) {
                if (match(constructor.getParameterTypes(), types)) {
                    return on(constructor, args);
                }
            }

            throw new ReflectException(e);
        }
    }


    public <P> P as(final Class<P> proxyType) {
        final boolean isMap = (object instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();

                // Actual method name matches always come first
                try {
                    return on(type, object).call(name, args).get();
                }

                // [#14] Emulate POJO behaviour on wrapped map objects
                catch (ReflectException e) {
                    if (isMap) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        int length = (args == null ? 0 : args.length);

                        if (length == 0 && name.startsWith("get")) {
                            return map.get(property(name.substring(3)));
                        } else if (length == 0 && name.startsWith("is")) {
                            return map.get(property(name.substring(2)));
                        } else if (length == 1 && name.startsWith("set")) {
                            map.put(property(name.substring(3)), args[0]);
                            return null;
                        }
                    }

//                    /* [java-8] */
//                    if (method.isDefault()) {
//
//                        /* [java-9] */
//                        // Java 9 version
//                        if (CACHED_LOOKUP_CONSTRUCTOR == null) {
//                            return MethodHandles
//                                    .privateLookupIn(proxyType, MethodHandles.lookup())
//                                    .in(proxyType)
//                                    .unreflectSpecial(method, proxyType)
//                                    .bindTo(proxy)
//                                    .invokeWithArguments(args);
//                        }
//                        /* [/java-9] */
//
//                        // Java 8 version
//                        return CACHED_LOOKUP_CONSTRUCTOR
//                                .newInstance(proxyType)
//                                .unreflectSpecial(method, proxyType)
//                                .bindTo(proxy)
//                                .invokeWithArguments(args);
//                    }
//                    /* [/java-8] */

                    throw e;
                }
            }
        };

        return (P) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
    }

    /**
     * Get the POJO property name of an getter/setter
     */
    private static String property(String string) {
        int length = string.length();

        if (length == 0) {
            return "";
        } else if (length == 1) {
            return string.toLowerCase();
        } else {
            return string.substring(0, 1).toLowerCase() + string.substring(1);
        }
    }

    // ---------------------------------------------------------------------
    // Object API
    // ---------------------------------------------------------------------

    /**
     * Check whether two arrays of types match, converting primitive types to
     * their corresponding wrappers.
     */
    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (actualTypes[i] == NULL.class)
                    continue;

                if (wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i])))
                    continue;

                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return object.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Reflect) {
            return object.equals(((Reflect) obj).get());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(object);
    }

    // ---------------------------------------------------------------------
    // Utility methods
    // ---------------------------------------------------------------------

    /**
     * Wrap an object created from a constructor
     */
    private static Reflect on(Constructor<?> constructor, Object... args) throws ReflectException {
        try {
            return on(constructor.getDeclaringClass(), accessible(constructor).newInstance(args));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * Wrap an object returned from a method
     */
    private static Reflect on(Method method, Object object, Object... args) throws ReflectException {
        try {
            accessible(method);

            if (method.getReturnType() == void.class) {
                method.invoke(object, args);
                return on(object);
            } else {
                return on(method.invoke(object, args));
            }
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Object unwrap(Object object) {
        if (object instanceof Reflect) {
            return ((Reflect) object).get();
        }

        return object;
    }

    private static Class<?>[] types(Object... values) {
        if (values == null) {
            return new Class[0];
        }

        Class<?>[] result = new Class[values.length];

        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }

        return result;
    }

    private static Class<?> forName(String name) throws ReflectException {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> forName(String name, ClassLoader classLoader) throws ReflectException {
        try {
            return Class.forName(name, true, classLoader);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public Class<?> type() {
        return type;
    }


    public static Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        } else if (type.isPrimitive()) {
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }

        return type;
    }

    private static class NULL {
    }
}