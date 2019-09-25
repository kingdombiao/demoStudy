package com.kingdombiao.lock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-25 15:47
 */
public class ZkDistrbuteLockPlus extends ZkAbstractLock {

    private CountDownLatch countDownLatch=null;

    private String beforePath; //当前请求节点的前一个节点
    private String currentPath; //当前请求的节点

    public ZkDistrbuteLockPlus() {
        if(!this.zkClient.exists(PATH2)){
            zkClient.createPersistent(PATH2);
        }
    }

    @Override
    public boolean tryLock() {

        //如果currentPath 为空则尝试第一次加锁
        if(currentPath ==null || currentPath.length()<=0){
            //创建第一个临时有序节点
            currentPath=this.zkClient.createEphemeralSequential(PATH2+"/","lock");
        }

        //获取所有临时节点并排序
        List<String> childrenS = this.zkClient.getChildren(PATH2);
        Collections.sort(childrenS);

        if(currentPath.equals(PATH2+"/"+childrenS.get(0))){
            //如果当前节点在所有节点中排名第一，则获取锁成功
            return true;
        }else {
            final int index = Collections.binarySearch(childrenS, currentPath.substring(PATH2.length() + 1));

            beforePath=PATH2+"/"+childrenS.get(index-1);
        }

        return false;
    }

    @Override
    public void waitLock() {
        IZkDataListener iZkDataListener=new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

                if(countDownLatch!=null){
                    countDownLatch.countDown();
                }
            }
        };

        //给排在前面的节点增加数据删除的监听，本质是启动另外一个线程去监听前置节点
        this.zkClient.subscribeDataChanges(beforePath,iZkDataListener);

        if(this.zkClient.exists(beforePath)){
            countDownLatch=new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.zkClient.unsubscribeDataChanges(beforePath,iZkDataListener);

    }

    @Override
    public void unLock() {
        this.zkClient.delete(currentPath);
        this.zkClient.close();
    }
}
