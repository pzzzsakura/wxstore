package com.irecssa.mmns.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 16:50
 * @desc: 获取properties文件属性值，用于util
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class PropertiesUtil {

  public static final String source = Thread.currentThread().getContextClassLoader()
      .getResource("/").getPath();


  public static String getValue(String propertiesName) {

    String url = "/wechatconfig.properties";
    try {
      InputStream in = new BufferedInputStream(new FileInputStream(source + url));
      Properties p = new Properties();
      p.load(in);
      return p.getProperty(propertiesName);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }




}
