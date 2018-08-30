package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.PropertyValue;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/30 12:55
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface PropertyValueDao {

  int batchInsertPropertyValue(List<PropertyValue> propertyValueList);

  List<PropertyValue> queryPropertyValueList(String propertyId);

  PropertyValue queryProperty(String pvId);

  List<PropertyValue> getByIds(String[] pvIds);

}
