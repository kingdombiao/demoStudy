package com.kingdombiao.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public interface HandlerAdapterService {

    Object[] hand(HttpServletRequest request,HttpServletResponse response,Method method, Map<String, Object> beans);
}
