package com.kingdombiao.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 描述:
 * 监听项目的启动和停止
 *
 * @author biao
 * @create 2019-09-26 10:56
 */
public class BiaoListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("BiaoListener.............contextInitialized...");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        System.out.println("BiaoListener.............contextDestroyed...");
    }
}
