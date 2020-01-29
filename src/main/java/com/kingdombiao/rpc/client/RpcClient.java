package com.kingdombiao.rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * rpc客户端代理
 */
public class RpcClient {

    /**
     * 动态代理类，实现对远程服务的访问
     */
    private static class DynProxy implements InvocationHandler {

        /**
         * 远程服务
         */
        private Class serviceInterface;
        /**
         * 远程调用地址
         */
        private final SocketAddress socketAddress;


        public DynProxy(Class serviceInterface, SocketAddress socketAddress) {
            this.serviceInterface = serviceInterface;
            this.socketAddress = socketAddress;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            ObjectInputStream inputStream = null;
            ObjectOutputStream outputStream = null;

            Socket socket = null;

            try {
                socket = new Socket();
                socket.connect(socketAddress);
                //类名 方法名 方法类型列表  方法入参列表
                outputStream=new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeUTF(serviceInterface.getSimpleName());
                outputStream.writeUTF(method.getName());
                outputStream.writeObject(method.getParameterTypes());
                outputStream.writeObject(args);
                outputStream.flush();

                inputStream=new ObjectInputStream(socket.getInputStream());
                return inputStream.readObject();


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                inputStream.close();
                outputStream.close();
                socket.close();
            }
            return null;
        }
    }


    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final SocketAddress socketAddress) {

        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class<?>[]{serviceInterface},
                new DynProxy(serviceInterface, socketAddress));
    }


}
