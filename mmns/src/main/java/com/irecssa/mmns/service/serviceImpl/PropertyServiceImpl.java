package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.PropertyDao;
import com.irecssa.mmns.dto.execution.PropertyExecution;
import com.irecssa.mmns.entity.Property;
import com.irecssa.mmns.enums.PropertyEnum;
import com.irecssa.mmns.exceptions.PropertyOperationException;
import com.irecssa.mmns.service.PropertyService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 18:13
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class PropertyServiceImpl implements PropertyService {

  @Autowired
  private PropertyDao propertyDao;

  @Transactional
  public PropertyExecution addProperty(Property property) {
    if (property != null) {
      try {
        property.setPropertyId(UUIDUtil.createUUID());
        property.setPropertyOrder(UUIDUtil.createNum());
        int result = propertyDao.insertProperty(property);
        if (result > 0) {
          return new PropertyExecution(PropertyEnum.SUCCESS, property);
        } else {
          throw new PropertyOperationException("addProperty error");
        }
      } catch (Exception e) {
        throw new PropertyOperationException("addProperty error" + e.getMessage());
      }
    }else{
      return new PropertyExecution(PropertyEnum.NULL_AUTH_INFO);
    }

  }

  public PropertyExecution getPropertyList() {
    List<Property> propertyList = propertyDao.queryPropertyList();
    return new PropertyExecution(PropertyEnum.SUCCESS,propertyList);
  }
}
