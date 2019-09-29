package com.kingdombiao.servlet;

import com.kingdombiao.filter.BiaoFilter;
import com.kingdombiao.listener.BiaoListener;
import com.kingdombiao.service.BiaoService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-26 10:07
 */
@HandlesTypes(value = BiaoService.class)
public class BiaoServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("*******感兴趣的类**********");
        for (Class<?> cls : set) {
            System.out.println(cls);
        }

        //注冊serlvet组件
        ServletRegistration.Dynamic orderServlet = servletContext.addServlet("orderServlet", new OrderServlet());
        orderServlet.addMapping("/order");

        //注册监听器
        servletContext.addListener(BiaoListener.class);

        //注册filter
        FilterRegistration.Dynamic biaoFilter = servletContext.addFilter("biaoFilter", new BiaoFilter());

        biaoFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
    }
}
