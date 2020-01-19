package com.kingdombiao.designMode.monitorMode;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2020-01-17 14:52
 */
public class BootStrap1 {

    public static void main(String[] args) {
        BootStrap1 bootstrap = new BootStrap1();

        Work work = bootstrap.newWork();

        Wrapper wrapper = new Wrapper();
        wrapper.setWork(work);
        wrapper.setParam("hello");

        wrapper.addListener(new Listener() {
            @Override
            public void ExecResult(Object result) {
                System.out.println("监听的结果："+result);
            }
        });

        CompletableFuture<Wrapper> future = CompletableFuture.supplyAsync(()->bootstrap.doWork(wrapper));

        try {
            Object o = future.get(2, TimeUnit.SECONDS);
            System.out.println("future get result:"+o);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            //超时了
            wrapper.getListener().ExecResult("time out exception");
        }
        System.out.println(Thread.currentThread().getName());

    }

    private Wrapper doWork(Wrapper wrapper){
        Work work = wrapper.getWork();
        String result = work.action(wrapper.getParam());
        wrapper.getListener().ExecResult(result);
        return wrapper;
    }

    private Work newWork(){
        return new Work() {
            @Override
            public String action(Object param) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return param+" world";
            };
        };
    }
}
