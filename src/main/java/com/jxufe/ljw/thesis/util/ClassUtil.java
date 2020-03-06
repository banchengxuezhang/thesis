package com.jxufe.ljw.thesis.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Classname ClassUtil
 * @Author: LeJunWen
 * @Date: 2020/3/3 11:21
 */
@Component
public class ClassUtil {
    //去除null
    public static Object checkNull(Object obj) {
        Class<? extends Object> clazz = obj.getClass();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 可访问私有变量
            field.setAccessible(true);
            // 获取属性类型
            String type = field.getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if ("class java.lang.String".equals(type)) {
                // 将属性的首字母大写
                String methodName = field.getName().replaceFirst(field.getName().substring(0, 1),
                        field.getName().substring(0, 1).toUpperCase());
                try {
                    Method methodGet = clazz.getMethod("get" + methodName);
                    // 调用getter方法获取属性值
                    String str = (String) methodGet.invoke(obj);
                    if (StringUtils.isBlank(str)) {
                        // Method methodSet = clazz.getMethod("set" +
                        // methodName, new Class[] { String.class });
                        // methodSet.invoke(o, new Object[] { "" });
                        //  System.out.println(field.getType()); // class java.lang.String
                        // 如果为null的String类型的属性则重新复制为空字符串
                        field.set(obj, field.getType().getConstructor(field.getType()).newInstance(""));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
    //硬核分页
    public static Object getObject(int page, int rows, Map<String, Object> map, List filterList, List subList) {
        int totalcount = filterList.size();
        int pagecount = 0;
        int m = totalcount % rows;
        if (m > 0) {
            pagecount = totalcount / rows + 1;
        } else {
            pagecount = totalcount / rows;
        }

        if (m == 0) {
            if (filterList.size() != 0 && filterList != null) {
                subList = filterList.subList((page - 1) * rows, rows * (page));
            }
        } else {
            if (page == pagecount) {
                subList = filterList.subList((page - 1) * rows, totalcount);
            } else {
                subList = filterList.subList((page - 1) * rows, rows * (page));
            }
        }
        map.put("total", filterList.size());
        map.put("rows", subList);
        return map;
    }
}
