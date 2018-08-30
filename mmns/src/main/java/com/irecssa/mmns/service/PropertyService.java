package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.PropertyExecution;
import com.irecssa.mmns.entity.Property;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 18:08
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface PropertyService {

  PropertyExecution addProperty(Property property);
  PropertyExecution getPropertyList();
}
