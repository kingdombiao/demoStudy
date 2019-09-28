package com.kingdombiao.ArgsResolver;

import com.kingdombiao.annotation.KingdomBiaoRequestParam;
import com.kingdombiao.annotation.KingdomBiaoService;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@KingdomBiaoService("requestParamArgResolver")
public class ReqquestParamArgResolver implements ArgumentResolver {

    @Override
    public boolean support(Class<?> type, int paramIndex, Method method) {

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        Annotation[] parameterAnnotation = parameterAnnotations[paramIndex];
        for (Annotation annotation : parameterAnnotation) {
            if (KingdomBiaoRequestParam.class.isAssignableFrom(annotation.getClass())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int paramIndex, Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        Annotation[] parameterAnnotation = parameterAnnotations[paramIndex];
        for (Annotation annotation : parameterAnnotation) {
            if (KingdomBiaoRequestParam.class.isAssignableFrom(annotation.getClass())) {
                KingdomBiaoRequestParam requestParam = (KingdomBiaoRequestParam) annotation;
                return request.getParameter(requestParam.value());
            }
        }

        return null;
    }
}
