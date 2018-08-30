package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 13:59
 * @desc: 商品类别属性表
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Property {

  private String propertyId;
  private Integer id;
  private String propertyName;
  private String propertyOrder;//编号

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPropertyId() {
    return propertyId;
  }

  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getPropertyOrder() {
    return propertyOrder;
  }

  public void setPropertyOrder(String propertyOrder) {
    this.propertyOrder = propertyOrder;
  }

}
