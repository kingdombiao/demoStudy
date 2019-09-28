package com.kingdombiao.HandlerAdapter;

import com.kingdombiao.ArgsResolver.ArgumentResolver;
import com.kingdombiao.annotation.KingdomBiaoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@KingdomBiaoService("handlerAdapterService")
public class HandlerAdapterServiceImpl implements HandlerAdapterService{

    @Override
    public Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method, Map<String, Object> beans) {

        Class<?>[] paramClazz = method.getParameterTypes();

        Object[] args = new Object[paramClazz.length];


        Map<String, Object> argResolverBeans = getArgResolverBeansOfType(beans, ArgumentResolver.class);

        int paramIndex=0;

        int index=0;

        for (Class<?> paraClazz : paramClazz) {
            for (Map.Entry<String, Object> entry : argResolverBeans.entrySet()) {
                ArgumentResolver argumentResolver= (ArgumentResolver) entry.getValue();
                if(argumentResolver.support(paraClazz,paramIndex,method)){
                    args[index++]=argumentResolver.argumentResolver(request,response,paraClazz,paramIndex,method);
                }
            }
            paramIndex++;
        }



        return args;
    }

    private Map<String,Object> getArgResolverBeansOfType(Map<String,Object> beans, Class<ArgumentResolver> argumentResolverClass) {

        Map<String,Object> argResolverBeans=new HashMap<>();

        for (Map.Entry<String, Object> entry : beans.entrySet()) {

            Class<?>[] interfaces = entry.getValue().getClass().getInterfaces();

            if(interfaces!=null && interfaces.length>0){
                for (Class<?> interfs : interfaces) {
                    if(interfs.isAssignableFrom(argumentResolverClass)){
                        argResolverBeans.put(entry.getKey(),entry.getValue());
                    }
                }
            }
        }
        return argResolverBeans;
    }
}
