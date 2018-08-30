package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.PropertyValue;
import com.irecssa.mmns.enums.PropertyValueEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/30 13:32
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class PropertyValueExecution {

  private int state;
  private String stateInfo;
  private List<PropertyValue> propertyValueList;

  public PropertyValueExecution(PropertyValueEnum propertyValueEnum,List<PropertyValue> propertyValueList) {
    this.propertyValueList = propertyValueList;
    this.state = propertyValueEnum.getState();
    this.stateInfo = propertyValueEnum.getStateInfo();
  }

  public PropertyValueExecution(PropertyValueEnum propertyValueEnum) {
    this.state = propertyValueEnum.getState();
    this.stateInfo = propertyValueEnum.getStateInfo();
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getStateInfo() {
    return stateInfo;
  }

  public void setStateInfo(String stateInfo) {
    this.stateInfo = stateInfo;
  }

  public List<PropertyValue> getPropertyValueList() {
    return propertyValueList;
  }

  public void setPropertyValueList(List<PropertyValue> propertyValueList) {
    this.propertyValueList = propertyValueList;
  }
}
