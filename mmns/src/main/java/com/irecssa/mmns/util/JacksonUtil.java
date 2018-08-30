package com.irecssa.mmns.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 22:10
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class JacksonUtil {

  /**
   * 获取泛型的Collection Type
   * @param collectionClass 泛型的Collection
   * @param elementClasses 元素类
   * @return JavaType Java类型
   * @since 1.0
   */
  public static JavaType getCollectionType(ObjectMapper mapper,Class<?> collectionClass, Class<?>... elementClasses) {
    return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
  }
}
