package com.kingdombiao.servlet;

import com.kingdombiao.HandlerAdapter.HandlerAdapterServiceImpl;
import com.kingdombiao.annotation.KingdomBiaoController;
import com.kingdombiao.annotation.KingdomBiaoQualifier;
import com.kingdombiao.annotation.KingdomBiaoRequestMapping;
import com.kingdombiao.annotation.KingdomBiaoService;
import com.kingdombiao.controller.BiaoController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BiaoDispatcherServlet extends HttpServlet {

    List<String> classNames = new ArrayList<String>();

    Map<String, Object> beansMap = new ConcurrentHashMap<>();

    Map<String, Object> handleMap = new ConcurrentHashMap<>();

    private static String HANDLERADAPTER = "handlerAdapterService";

    public BiaoDispatcherServlet() {
    }

    @Override
    public void init() throws ServletException {

        //扫描类路径下的包以及子包
        doScanPackage("com.kingdombiao");

        for (String className : classNames) {
            System.out.println(className);
        }

        //实例化扫描出来的类
        instance();

        for (Map.Entry<String, Object> entry : beansMap.entrySet()) {
            System.out.println("beanName:{" + entry.getKey() + "}--------beanInstance:{" + entry.getValue() + "}");
        }

        //依赖注入，把serice层的实例注入到controller
        dependencyInjection();

        //构建请求路径path与方法method的映射关系
        handlerMapping();


    }

    private void handlerMapping() {

        if (beansMap.entrySet().size() <= 0) {
            System.out.println("没有类的实例化，暂时构建请求路径path与方法method的映射关系");
            return;
        }

        for (Map.Entry<String, Object> entry : beansMap.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(KingdomBiaoController.class)) {
                KingdomBiaoRequestMapping parentRequestMapping = clazz.getAnnotation(KingdomBiaoRequestMapping.class);
                String parentPath = parentRequestMapping.value();
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method method : declaredMethods) {
                    if (method.isAnnotationPresent(KingdomBiaoRequestMapping.class)) {
                        KingdomBiaoRequestMapping childRequestMapping = method.getAnnotation(KingdomBiaoRequestMapping.class);
                        String childPath = childRequestMapping.value();
                        handleMap.put(parentPath + childPath, method);
                    }
                }
            }
        }
    }


    private void dependencyInjection() {

        if (beansMap.entrySet().size() <= 0) {
            System.out.println("没有类的实例化，暂时无法依赖注入");
            return;
        }

        for (Map.Entry<String, Object> entry : beansMap.entrySet()) {
            Object instance = entry.getValue();

            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(KingdomBiaoController.class)) {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(KingdomBiaoQualifier.class)) {
                        KingdomBiaoQualifier biaoQualifier = field.getAnnotation(KingdomBiaoQualifier.class);
                        String beanName = biaoQualifier.value();
                        field.setAccessible(true);
                        try {
                            field.set(instance, beansMap.get(beanName));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void instance() {
        if (classNames.size() <= 0) {
            System.out.println("包扫描失败");
            return;
        }

        for (String className : classNames) {

            String classStr = className.replace(".class", "");
            try {
                Class<?> clazz = Class.forName(classStr);

                //如果类上标注有  KingdomBiaoController 注解
                if (clazz.isAnnotationPresent(KingdomBiaoController.class)) {
                    Object instance = clazz.newInstance();
                    KingdomBiaoRequestMapping requestMapping = clazz.getAnnotation(KingdomBiaoRequestMapping.class);
                    //请求路径key
                    String key = requestMapping.value();
                    beansMap.put(key, instance);

                } else if (clazz.isAnnotationPresent(KingdomBiaoService.class)) {
                    KingdomBiaoService biaoService = clazz.getAnnotation(KingdomBiaoService.class);
                    String beaName = biaoService.value();
                    Object instance = clazz.newInstance();
                    beansMap.put(beaName, instance);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void doScanPackage(String basePackage) {
        //扫描编译好的类路径下所有类
        URL resource = this.getClass().getClassLoader().getResource("/" + replaceTo(basePackage));

        String fileStr = resource.getFile();

        File file = new File(fileStr);

        String[] fileList = file.list();

        for (String path : fileList) {

            File filePath = new File(fileStr + path);

            //递归扫描，如果是文件夹，继续扫描
            if (filePath.isDirectory()) {
                doScanPackage(basePackage + "." + path);
            } else {
                //如果是文件，加入list集合(待生成的bean)
                classNames.add(basePackage + "." + filePath.getName());
            }

        }


    }

    private String replaceTo(String basePackage) {
        return basePackage.replaceAll("\\.", "/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();

        String contextPath = req.getContextPath();

        String reqPath = requestURI.replace(contextPath, "");

        Method method = (Method) handleMap.get(reqPath);

        Object instance = beansMap.get("/" + reqPath.split("/")[1]);

        HandlerAdapterServiceImpl handlerAdapterService = (HandlerAdapterServiceImpl) beansMap.get(HANDLERADAPTER);

        Object[] args = handlerAdapterService.hand(req, resp, method, beansMap);

        try {
            method.invoke(instance,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
