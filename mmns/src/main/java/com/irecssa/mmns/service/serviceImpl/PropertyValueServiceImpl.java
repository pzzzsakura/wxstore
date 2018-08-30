package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.PropertyValueDao;
import com.irecssa.mmns.dto.execution.PropertyValueExecution;
import com.irecssa.mmns.entity.PropertyValue;
import com.irecssa.mmns.enums.PropertyValueEnum;
import com.irecssa.mmns.exceptions.PropertyValueException;
import com.irecssa.mmns.service.PropertyValueService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/30 13:38
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService{

  @Autowired
  private PropertyValueDao propertyValueDao;
  public PropertyValueExecution addPropertyValueList(List<PropertyValue> propertyValueList) {
    if(propertyValueList!=null&&propertyValueList.size()>0){
      for (PropertyValue propertyValue:propertyValueList){
        propertyValue.setPvId(UUIDUtil.createUUID());
      }
      try {
        propertyValueDao.batchInsertPropertyValue(propertyValueList);
        return new PropertyValueExecution(PropertyValueEnum.SUCCESS,propertyValueList);
      }catch (Exception e){
        throw new PropertyValueException(e.getMessage());
      }
    }else {
      return new PropertyValueExecution(PropertyValueEnum.NULL_AUTH_INFO);
    }
  }

  public PropertyValueExecution getPropertyValueList(String propertyId) {
    return new PropertyValueExecution(PropertyValueEnum.SUCCESS,propertyValueDao.queryPropertyValueList(propertyId));
  }
}
