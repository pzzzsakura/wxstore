package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.PropertyValueExecution;
import com.irecssa.mmns.entity.PropertyValue;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/30 13:30
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface PropertyValueService {
  PropertyValueExecution addPropertyValueList(List<PropertyValue> propertyValueList);

  PropertyValueExecution getPropertyValueList(String propertyId);

}
