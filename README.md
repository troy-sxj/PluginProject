## 代理
> 代理主要分为静态代理和动态代理，在插件化中分别表现为对Instrumentation和ActivityManagerNative进行Hook。

### 代理使用场景

1. 远程代理（AIDL）
2. 保护代理（权限控制）
3. 虚代理（图片占位）
4. 协作开发（Mock Class）
5. 生活加料（记日志）

### 代理类型

1. 静态代理

    每一个类都要有一个对应的Proxy类，Proxy类的数量会很多，但逻辑大同小异，这种实现为"静态代理"。

2. 动态代理

    Proxy类的newProxyInstance，声明如下：

        /**
         * loader: 设置为目标对象class1对应的classLoader
         * interfaces: 设置为目标对象class1所实现的接口类型，这里是Class1Interface
         * 第3个参数是一个实现了InvocationHandler接口的类对象，我们通过它的构造函数把目标对象class1注入
         */
        static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)


## 对startActivity进行hook

