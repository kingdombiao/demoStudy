package com.kingdombiao.lock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-25 13:55
 */
public class ZkDistrbuteLock  extends ZkAbstractLock{

    private CountDownLatch countDownLatch=null;

    @Override
    public boolean tryLock() {
       try {
           zkClient.createEphemeral(PATH);
           return true;
       }catch (Exception e){
           return false;
       }
    }

    @Override
    public void waitLock() {

        IZkDataListener iZkDataListener = new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                //唤醒被等待的线程
                if(countDownLatch!=null){
                    countDownLatch.countDown();
                }
            }
        };

        //注册事件
        zkClient.subscribeDataChanges(PATH,iZkDataListener);

        if(zkClient.exists(PATH)){
            countDownLatch=new CountDownLatch(1);
            try {
                //等待，一直等到接受到事件通知
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //删除监听
        zkClient.unsubscribeDataChanges(PATH,iZkDataListener);

    }

    @Override
    public void unLock() {
        if(zkClient!=null){
            zkClient.delete(PATH);
            zkClient.close();
            System.out.println("******释放锁资源******");
        }
    }
}
