package com.jxufe.ljw.thesis.util;

import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @Classname ClassUtil
 * @Author: LeJunWen
 * @Date: 2020/3/3 11:21
 */
@Component
public class ClassUtil {
    private static  final Logger logger= LoggerFactory.getLogger(ClassUtil.class);
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
    //上传文件

    /**
     *
     * @param multipartFile
     * @param url 原先的文件 url 不存在可为""
     * @param sysPath 文件路径
     * @param fileName 文件名
     * @return
     */
    public  static Boolean uploadFile(MultipartFile multipartFile,String url,String sysPath,String fileName){
        if (multipartFile.isEmpty()) {
            return false;
        }
        if ((!StringUtils.isEmpty(url))&&url!="") {
            File file = new File(sysPath, url);
            file.renameTo(new File(sysPath, url + ".bak"));
        }
        logger.info("==================开始上传单个文件==================");
        // 获得原始后缀
        try {
            File filePath = new File(sysPath, fileName);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
            logger.info("文件上传路径：" + filePath);
            out.write(multipartFile.getBytes());
            out.flush();
            out.close();
            File file = new File(sysPath, url + ".bak");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.info("==================文件上传异常，e={}==================", e);
            File file = new File(sysPath, url + ".bak");
            if (file.exists()) {
                file.renameTo(new File(sysPath, url));
            }
            return  false;
        }
        return true;
    }

    /**
     *
     * @param response
     * @param path 文件路径
     * @param filename 文件名
     * @param s 出错日志
     */
    public  static void downloadFile(HttpServletResponse response, String path, String filename, String s) {
        try (
                InputStream inputStream = new FileInputStream(new File(path, filename));
                OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download;charset=UTF-8");
            response.addHeader("content-disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(filename, "UTF-8"));
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            logger.info(s);
        }
    }

    /**
     * 删除文件
     * @param file
     * @return
     */
    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }
}
