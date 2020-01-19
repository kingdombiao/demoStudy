package com.kingdombiao.designMode.monitorMode;


import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2020-01-17 14:52
 */
public class BootStrap {

    public static void main(String[] args) {
        BootStrap bootstrap = new BootStrap();

        Work work = bootstrap.newWork();

        Wrapper wrapper = new Wrapper();
        wrapper.setWork(work);
        wrapper.setParam("hello");

        bootstrap.doWork(wrapper).addListener(new Listener() {
            @Override
            public void ExecResult(Object result) {
                System.out.println(Thread.currentThread().getName());
                System.out.println("监听的结果："+result);
            }
        });

        System.out.println(Thread.currentThread().getName());

    }

    private Wrapper doWork(Wrapper wrapper){
        new Thread(){
            @Override
            public void run() {
                Work work = wrapper.getWork();
                String result = work.action(wrapper.getParam());
                wrapper.getListener().ExecResult(result);
            }
        }.start();

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
