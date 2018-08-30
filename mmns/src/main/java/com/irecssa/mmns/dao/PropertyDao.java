package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.Property;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 14:11
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface PropertyDao {

 int insertProperty(Property property);

 List<Property> queryPropertyList();

 int updateProperty(Property property);

 Property queryPropertyByName(String propertyName);

 Property queryPropertyById(String propertyId);
}
