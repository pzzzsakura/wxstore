package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 14:18
 * @desc: 类别属性联系
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductProperty {

  private String ppId;
  private PropertyValue propertyValue;//属性值主键
  private Integer id;
  private String ppManageId;//组合表主键
  private Date createTime;//创建时间
  private Date lastEditTime;//更新时间
  private String productId;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Date getLastEditTime() {
    return lastEditTime;
  }

  public void setLastEditTime(Date lastEditTime) {
    this.lastEditTime = lastEditTime;
  }

  public PropertyValue getPropertyValue() {
    return propertyValue;
  }

  public void setPropertyValue(PropertyValue propertyValue) {
    this.propertyValue = propertyValue;
  }

  public String getPpId() {
    return ppId;
  }

  public void setPpId(String ppId) {
    this.ppId = ppId;
  }



  public String getPpManageId() {
    return ppManageId;
  }

  public void setPpManageId(String ppManageId) {
    this.ppManageId = ppManageId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
