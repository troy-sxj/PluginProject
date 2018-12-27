package invoke;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: mika
 * @Time: 2018-11-15 15:35
 * @Description:
 *
 * <p>
 * 1. 反射出一个构造函数并调用它
 * 2. 调用静态方法
 * 3. 调用实例方法
 * 4. 获取和设置一个字段的值
 * 5. 对泛型的处理
 * </p>
 */

public class RefInvoke {

    //无参
    public static Object createObject(String className) {
        Class[] pareTypes = new Class[]{};
        Object[] pareValues = new Object[]{};

        try {
            Class r = Class.forName(className);
            return createObject(r, pareTypes, pareValues);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //无参
    public static Object createObject(Class clazz) {
        Class[] pareType = new Class[]{};
        Object[] pareValues = new Object[]{};

        return createObject(clazz, pareType, pareValues);
    }

    //一个参数
    public static Object createObject(String className, Class pareType, Object pareVaule) {
        Class[] pareTypes = new Class[]{pareType};
        Object[] pareValues = new Object[]{pareVaule};

        try {
            Class r = Class.forName(className);
            return createObject(r, pareTypes, pareValues);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //一个参数
    public static Object createObject(Class clazz, Class pareType, Object pareVaule) {
        Class[] pareTypes = new Class[]{pareType};
        Object[] pareValues = new Object[]{pareVaule};

        return createObject(clazz, pareTypes, pareValues);
    }

    //多个参数
    public static Object createObject(String className, Class[] pareTypes, Object[] pareValues) {
        try {
            Class r = Class.forName(className);
            return createObject(r, pareTypes, pareValues);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //多个参数
    public static Object createObject(Class clazz, Class[] pareTypes, Object[] pareValues) {
        try {
            Constructor ctor = clazz.getDeclaredConstructor(pareTypes);
            ctor.setAccessible(true);
            return ctor.newInstance(pareValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    //多个参数
    public static Object invokeInstanceMethod(Object obj, String methodName, Class[] pareTypes, Object[] pareValues) {
        if (obj == null)
            return null;

        try {
            //调用一个private方法
            Method method = obj.getClass().getDeclaredMethod(methodName, pareTypes); //在指定类中获取指定的方法
            method.setAccessible(true);
            return method.invoke(obj, pareValues);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //一个参数
    public static Object invokeInstanceMethod(Object obj, String methodName, Class pareType, Object pareVaule) {
        Class[] pareTypes = {pareType};
        Object[] pareValues = {pareVaule};

        return invokeInstanceMethod(obj, methodName, pareTypes, pareValues);
    }

    //无参
    public static Object invokeInstanceMethod(Object obj, String methodName) {
        Class[] pareTypes = new Class[]{};
        Object[] pareValues = new Object[]{};

        return invokeInstanceMethod(obj, methodName, pareTypes, pareValues);
    }


    //无参
    public static Object invokeStaticMethod(String className, String method_name) {
        Class[] pareTypes = new Class[]{};
        Object[] pareValues = new Object[]{};

        return invokeStaticMethod(className, method_name, pareTypes, pareValues);
    }

    //一个参数
    public static Object invokeStaticMethod(String className, String method_name, Class pareType, Object pareVaule) {
        Class[] pareTypes = new Class[]{pareType};
        Object[] pareValues = new Object[]{pareVaule};

        return invokeStaticMethod(className, method_name, pareTypes, pareValues);
    }

    //多个参数
    public static Object invokeStaticMethod(String className, String method_name, Class[] pareTypes, Object[] pareValues) {
        try {
            Class obj_class = Class.forName(className);
            return invokeStaticMethod(obj_class, method_name, pareTypes, pareValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //无参
    public static Object invokeStaticMethod(Class clazz, String method_name) {
        Class[] pareTypes = new Class[]{};
        Object[] pareValues = new Object[]{};

        return invokeStaticMethod(clazz, method_name, pareTypes, pareValues);
    }

    //一个参数
    public static Object invokeStaticMethod(Class clazz, String method_name, Class classType, Object pareVaule) {
        Class[] classTypes = new Class[]{classType};
        Object[] pareValues = new Object[]{pareVaule};

        return invokeStaticMethod(clazz, method_name, classTypes, pareValues);
    }

    //多个参数
    public static Object invokeStaticMethod(Class clazz, String method_name, Class[] pareTypes, Object[] pareValues) {
        try {
            Method method = clazz.getDeclaredMethod(method_name, pareTypes);
            method.setAccessible(true);
            return method.invoke(null, pareValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    //简写版本
    public static Object getFieldObject(Object obj, String fieldName) {
        return getFieldObject(obj.getClass(), obj, fieldName);
    }

    public static Object getFieldObject(String className, Object obj, String fieldName) {
        try {
            Class obj_class = Class.forName(className);
            return getFieldObject(obj_class, obj, fieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldObject(Class clazz, Object obj, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //简写版本
    public static void setFieldObject(Object obj, String fieldName, Object filedVaule) {
        setFieldObject(obj.getClass(), obj, fieldName, filedVaule);
    }

    public static void setFieldObject(Class clazz, Object obj, String fieldName, Object filedVaule) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, filedVaule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFieldObject(String className, Object obj, String fieldName, Object filedVaule) {
        try {
            Class obj_class = Class.forName(className);
            setFieldObject(obj_class, obj, fieldName, filedVaule);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Object getStaticFieldObject(String className, String fieldName) {
        return getFieldObject(className, null, fieldName);
    }

    public static Object getStaticFieldObject(Class clazz, String fieldName) {
        return getFieldObject(clazz, null, fieldName);
    }

    public static void setStaticFieldObject(String classname, String fieldName, Object filedVaule) {
        setFieldObject(classname, null, fieldName, filedVaule);
    }

    public static void setStaticFieldObject(Class clazz, String fieldName, Object filedVaule) {
        setFieldObject(clazz, null, fieldName, filedVaule);
    }
}