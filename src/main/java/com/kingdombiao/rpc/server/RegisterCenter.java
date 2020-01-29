package com.kingdombiao.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterCenter {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static Map<String, Class> serviceRegister = new HashMap<>();

    private static int port;

    public RegisterCenter(int port) {
        this.port = port;
    }

    public  void register(Class serviceInterface, Class serviceImpl) {
        serviceRegister.put(serviceInterface.getSimpleName(), serviceImpl);
    }

    public  void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server is started");

            while (true) {
                executorService.execute(new ServiceTask(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static class ServiceTask implements Runnable {

        private Socket socket;

        public ServiceTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //读取socket中的数据流
            ObjectInputStream inputStream = null;
            ObjectOutputStream outputStream = null;

            //类名 方法名 方法类型列表  方法入参列表
             try {
                inputStream = new ObjectInputStream(socket.getInputStream());
                String serviceImpl = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) inputStream.readObject();
                Object[] args = (Object[]) inputStream.readObject();
                Class serviceClass = serviceRegister.get(serviceImpl);
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), args);

                //将结果返回给客户端
                outputStream=new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(result);
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    outputStream.close();
                    inputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
