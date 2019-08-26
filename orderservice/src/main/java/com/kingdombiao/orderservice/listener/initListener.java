package com.kingdombiao.orderservice.listener;

import com.kingdombiao.orderservice.zookeeper.ServerSubscribe;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 14:30
 */
public class initListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServerSubscribe.subscribe();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
