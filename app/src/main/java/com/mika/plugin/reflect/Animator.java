package com.mika.plugin.reflect;

/**
 * @Author: mika
 * @Time: 2018-11-15 10:55
 * @Description:
 */
public class Animator {

    private static final Singleton<ClassB2Interface> getDefault = new Singleton<ClassB2Interface>() {
        @Override
        protected ClassB2Interface create() {
            ClassB2 classB2 = new ClassB2();
            classB2.id = 2;
            return classB2;
        }
    };

    static public ClassB2Interface getDefault(){
        return getDefault.get();
    }
}
