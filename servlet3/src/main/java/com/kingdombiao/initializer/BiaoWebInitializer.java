package com.kingdombiao.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-26 14:17
 */
public class BiaoWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //获取根容器的配置类：spring 的配置文件 (父容器)
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{BiaoRootConfig.class};
    }

    //获取web容器的配置类：springMVC配置文件 (子容器)
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{BiaoAppConfig.class};
    }

    //获取DispatcherServlet的映射信息
    /*
         /：拦截所有请求（包括静态资源 xx.js,xx.png等），但是不包括*.jsp;
         /*:拦截所有请求：连*.jsp页面都拦截；jsp页面是tomcat的jsp引擎解析的；
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
