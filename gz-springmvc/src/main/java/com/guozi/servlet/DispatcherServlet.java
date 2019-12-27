package com.guozi.servlet;

import com.guozi.annotation.GZController;
import com.guozi.annotation.GZQualifier;
import com.guozi.annotation.GZRequestMapping;
import com.guozi.annotation.GZService;
import com.guozi.commonBeans.Handler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：ye.guolin
 * 日期：2019/08/12
 * 描述：前端控制器
 * 根据请求路径定位具体类的具体方法
 */
public class DispatcherServlet extends HttpServlet {

    // 存当前加载的所有类的类名
    List<String> classNames = new ArrayList<>();

    // 存储路径地址url（key）和类对象（value）
    Map<String, Object> beans = new HashMap<>();

    // 存储路径地址url（key）和方法的map集合
    Map<String, Handler> handlerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // 获取要加载的类名
        doScanPackage("com.guozi");

        // 根据类名实例化类对象
        doInstance();

        // 3.将IOC容器中的service对象设置给controller层定义的field上
        doIoc();

        // 4.建立path与method的映射关系
        handlerMapping();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // 获取请求的url
        String uri = request.getRequestURI();
        //获得项目名称
        String contextPath = request.getContextPath();
        // 截取请求路径中的需要的部分
        String path = uri.substring(contextPath.length());

        // 根据截取的请求路劲获取对应的method执行
        Handler handler = handlerMap.get(path);
        //这里不考虑参数执行方法
        Object returnVal = null;
        PrintWriter pw;
        try{
            Method mh = handler.getMh();
            Object obj = handler.getObj();
            returnVal = mh.invoke(obj);
            // 将返回结果返回到页面
            pw = response.getWriter();
            pw.write(returnVal.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * 作者: ye.guolin
     * 日期: 2019/08/12 17:24
     * 描述: 获取需要加载包下的所有类名，并存入类名集合中
     */
    private void doScanPackage(String basePackage) {
        URL resource = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
        String fileStr = resource.getFile();
        File file = new File(fileStr);
        String[] listFiles = file.list();
        for (String path : listFiles) {
            File filePath = new File(fileStr + path);
            // 如果当前是目录，则递归
            if (filePath.isDirectory()) {
                doScanPackage(basePackage + "." + path);
                // 如果是文件，则直接添加到classNames
            } else {
                classNames.add(basePackage + "." + filePath.getName());
            }
        }
    }

    /**
     * 作者: ye.guolin
     * 日期: 2019/08/12 17:33
     * 描述: 实例化需要加载的类
     */
    private void doInstance() {
        for (String className : classNames) {
            String cn = className.replaceAll(".class", "");
            try {
                // 根据名称获取类对象
                Class<?> clazz = Class.forName(cn);
                // 判断类上是否有GZController注解
                if (clazz.isAnnotationPresent(GZController.class)) {
                    // 将@GZRequestMapping的值作为key,bean作为value存入IOC容器
                    GZRequestMapping requestMapping = clazz.getAnnotation(GZRequestMapping.class);
                    beans.put(requestMapping.value(), clazz.newInstance());
                }
                // 判断类上是否有@GZService注解
                if (clazz.isAnnotationPresent(GZService.class)) {
                    // 将@GZService上的值作为key，service对象作为value存入IOC容器
                    GZService service = clazz.getAnnotation(GZService.class);
                    beans.put(service.value(), clazz.newInstance());
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 作者: ye.guolin
     * 日期: 2019/08/12 17:51
     * 描述: 将IOC容器中的service对象设置给controller层定义的field上
     */
    private void doIoc() {
        // 遍历实例化的beans,给里面的成员变量依赖注入
        for (Map.Entry<String, Object> map : beans.entrySet()) {
            Object instance = map.getValue();
            // 获取类对象
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(GZController.class)) {
                // 获取类下所有的成员变量
                Field[] fields = clazz.getDeclaredFields();
                // 遍历所有成员变量，给带有@GZQualifier的变量赋值
                for (Field field : fields) {
                    if (field.isAnnotationPresent(GZQualifier.class)) {
                        GZQualifier qualifier = field.getAnnotation(GZQualifier.class);
                        String value = qualifier.value();
                        // 由于此类成员变量设置为private，需要强行设置
                        field.setAccessible(true);
                        // 从IOC中取得对应的bean给变量赋值
                        Object bean = beans.get(value);
                        try {
                            field.set(instance, beans.get(value));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    /**
     * 作者: ye.guolin
     * 日期: 2019/08/13 09:59
     * 描述: 建立path与method的映射关系
     */
    public void handlerMapping() {
        // 遍历实例化的beans，给里面的方法添加映射关系
        for (Map.Entry<String, Object> map : beans.entrySet()) {
            Object instance = map.getValue();
            // 得到类对象
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(GZController.class)) {
                // 获取类上路径
                GZRequestMapping cRequestMapping = clazz.getAnnotation(GZRequestMapping.class);
                String cPath = cRequestMapping.value();
                // 获取该类下所有方法
                Method[] methods = clazz.getDeclaredMethods();
                // 遍历所有方法
                for (Method method : methods) {
                    if(method.isAnnotationPresent(GZRequestMapping.class)){
                        // 获取方法上路径
                        GZRequestMapping requestMapping = method.getAnnotation(GZRequestMapping.class);
                        String mPath = requestMapping.value();
                        // 将类上的路径+方法上的路径 设置为key，方法设置为value
                        handlerMap.put(cPath + mPath, new Handler(method,instance));
                    }else {
                        continue;
                    }
                }
            }else {
                continue;
            }
        }
    }

}
