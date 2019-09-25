package com.kingdombiao.lock;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-25 13:43
 */
public abstract class AbstractLock implements Lock {

    @Override
    public void getLock() {
        if(tryLock()){
            System.out.println("*******获取lock锁资源******");
        }else {
            //等待
            waitLock();

            //重新获取锁资源
            getLock();
        }

    }

    public abstract boolean tryLock();

    public abstract void waitLock();


}
