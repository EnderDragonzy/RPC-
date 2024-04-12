# Java rmi踩坑总结

**服务端报错：**

```shell
java.rmi.UnmarshalException: error unmarshalling arguments; nested exception is:…………
Caused by: java.lang.ClassNotFoundException: com.basics.RMI…………
```

**原因是rmiregistry注册中心没有找到接口，这个跟rmiregistry启动的位置有关。必须在编译文件（class文件）的最上层目录下启动rmiregistry才能让注册中心找到正确的类。**

**解决方法：例如我编译好的class文件都放在output目录下，如图所示：**

![image-20240412150553666](C:\Users\zhangyi\AppData\Roaming\Typora\typora-user-images\image-20240412150553666.png)

打开一个终端，一定要cd到output目录下，再启动rmiregistry,就OK了

![image-20240412151223044](C:\Users\zhangyi\AppData\Roaming\Typora\typora-user-images\image-20240412151223044.png)

再次运行服务端，发现没有报错，一切正常。

![image-20240412151323153](C:\Users\zhangyi\AppData\Roaming\Typora\typora-user-images\image-20240412151323153.png)



**客户端报错：**

```shell
Exception:java.lang.ClassCastException: class jdk.proxy1.$Proxy0 cannot be cast to class com.stu.Client.RPCinterface (jdk.proxy1.$Proxy0 is in module jdk.proxy1 of loader 'app'; com.stu.Client.RPCinterface is in unnamed module of loader 'app')
java.lang.ClassCastException: class jdk.proxy1.$Proxy0 cannot be cast to class com.stu.Client.RPCinterface (jdk.proxy1.$Proxy0 is in module jdk.proxy1 of loader 'app'; com.stu.Client.RPCinterface is in unnamed module of loader 'app')
	at com.stu.Client.RMIClient.main(RMIClient.java:20)
```

**原因是Java rmi要求服务端与客户端的接口的类的全限定名必须保持完全一致（全限定名=包名 + 类型名）。例如下图的错误示例中，我之前错把客户端Clinet与服务端Server的RPCinterface分别放到了两个包中（com.stu.Client和com.stu.Server)，这就导致了RPCinterface接口的全限定名不一致，进而导致类型转换错误。**

![image-20240412143841804](C:\Users\zhangyi\AppData\Roaming\Typora\typora-user-images\image-20240412143841804.png)

**解决方法，把RPCinterface放在一个单独的包里，不再区分Client和Server，如下图所示：新建一个com.stu.Com包，单独放RPCinterface。（注意，该方法只适合用于客户端与服务端都是同一个本地机运行的情况，用于调试程序。更加通用的做法是把RPCinterface打成一个jar包来用）**。

![image-20240412145833754](C:\Users\zhangyi\AppData\Roaming\Typora\typora-user-images\image-20240412145833754.png)



## 序列化问题

```shell
Exception:java.rmi.MarshalException: error marshalling arguments; nested exception is: 
	java.io.NotSerializableException: com.stu.Entity.Student
java.rmi.MarshalException: error marshalling arguments; nested exception is: 
	java.io.NotSerializableException: com.stu.Entity.Student
	at java.rmi/sun.rmi.server.UnicastRef.invoke(UnicastRef.java:161)
	at java.rmi/java.rmi.server.RemoteObjectInvocationHandler.invokeRemoteMethod(RemoteObjectInvocationHandler.java:215)
	at java.rmi/java.rmi.server.RemoteObjectInvocationHandler.invoke(RemoteObjectInvocationHandler.java:160)
	at jdk.proxy1/jdk.proxy1.$Proxy0.add(Unknown Source)
	at com.stu.Client.RMIClient.main(RMIClient.java:45)
Caused by: java.io.NotSerializableException: com.stu.Entity.Student
	at java.base/java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1197)
	at java.base/java.io.ObjectOutputStream.writeObject(ObjectOutputStream.java:354)
	at java.rmi/sun.rmi.server.UnicastRef.marshalValue(UnicastRef.java:294)
	at java.rmi/sun.rmi.server.UnicastRef.invoke(UnicastRef.java:156)
	... 4 more

Process finished with exit code 0

```

如果自定义了类，需要考虑序列化的问题。请在自定义类中实现Serializable接口，添加UID字段。

例如：

```java
public class User implements Serializable {
    // 该字段必须存在
    private static final long serialVersionUID = 42L;
    // setter和getter可以没有
    String name;
    int id;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
```



## Mybatis问题

数据添加不上。原因是没有commit。

加上代码session.commit()就ok了