package com.kingdombiao.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-26 10:53
 */
public class BiaoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("BiaoFilter............doFilter.................");

        //放行
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
