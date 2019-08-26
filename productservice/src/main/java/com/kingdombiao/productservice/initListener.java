package com.kingdombiao.productservice;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 10:31
 */
public class initListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            ServerRegister.register(hostAddress,9091);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
