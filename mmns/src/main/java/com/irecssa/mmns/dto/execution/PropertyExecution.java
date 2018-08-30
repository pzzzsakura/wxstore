package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.Property;
import com.irecssa.mmns.enums.PropertyEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 18:10
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class PropertyExecution {

  private int state;
  private String stateInfo;
  private Property property;
  private List<Property> propertyList;

  public PropertyExecution(PropertyEnum propertyEnum,Property property) {
    this.state = propertyEnum.getState();
    this.stateInfo = propertyEnum.getStateInfo();
    this.property = property;
  }

  public PropertyExecution(PropertyEnum propertyEnum,List<Property> propertyList) {
    this.state = propertyEnum.getState();
    this.stateInfo = propertyEnum.getStateInfo();
    this.propertyList = propertyList;
  }
  public PropertyExecution(PropertyEnum propertyEnum) {
    this.state = propertyEnum.getState();
    this.stateInfo = propertyEnum.getStateInfo();
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

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  public List<Property> getPropertyList() {
    return propertyList;
  }

  public void setPropertyList(List<Property> propertyList) {
    this.propertyList = propertyList;
  }
}
