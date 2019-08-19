package com.kingdombiao.ConcurrentContainer;

import com.kingdombiao.utils.ReflectUtils;
import com.sun.deploy.util.ReflectionUtil;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * 并发容器 CopyOnWriteArrayList
 *
 * @author biao
 * @create 2019-08-19 10:41
 */
public class CopyOnWriteArrayListDemo {

    @Test
    public void testAdd() {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList<Integer>();

        copyOnWriteArrayList.add(1);
        System.out.println(ReflectUtils.getFieldValue("array", copyOnWriteArrayList));

        copyOnWriteArrayList.add(2);
        System.out.println(ReflectUtils.getFieldValue("array", copyOnWriteArrayList));

        copyOnWriteArrayList.add(3);
        System.out.println(ReflectUtils.getFieldValue("array", copyOnWriteArrayList));

    }


    @Test
    public void testHappenBefore() throws InterruptedException {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("test");
        Thread threadB = new Thread() {
            @Override
            public void run() {
                System.out.println("thread remove before:" + ReflectUtils.getFieldValue("array", copyOnWriteArrayList));
                copyOnWriteArrayList.remove("test");
                System.out.println("thread remove after:" + ReflectUtils.getFieldValue("array", copyOnWriteArrayList));
            }
        };
        threadB.start();
        System.out.println("main add before:" + ReflectUtils.getFieldValue("array", copyOnWriteArrayList));
        copyOnWriteArrayList.add("test");
        System.out.println("main add after:" + ReflectUtils.getFieldValue("array", copyOnWriteArrayList));

    }
}
