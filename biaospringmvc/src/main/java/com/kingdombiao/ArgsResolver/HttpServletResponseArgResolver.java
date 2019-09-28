package com.kingdombiao.ArgsResolver;

import com.kingdombiao.annotation.KingdomBiaoService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@KingdomBiaoService("httpServletResponseArgResolver")
public class HttpServletResponseArgResolver implements ArgumentResolver{

    @Override
    public boolean support(Class<?> type, int paramIndex, Method method){
        return ServletResponse.class.isAssignableFrom(type);
    }



    @Override
    public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int paramIndex, Method method) {
        return response;
    }
}
