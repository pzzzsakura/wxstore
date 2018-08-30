package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 09:24
 * @desc: 用户所选择得商品列表,购物车和订单列表
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class SPNum {

  private String spNumId;//主键
  private Integer spNumNum;//商品数量
  private Date createTime;//创建时间
  private Integer shoppingCart;//是否是在购物车种
  private Integer enableStatus;//状态
  private String proOrderId;//订单主键
  private Product product;
  private String ppManageId;
  private String personInfoId;

  public String getPersonInfoId() {
    return personInfoId;
  }

  public void setPersonInfoId(String personInfoId) {
    this.personInfoId = personInfoId;
  }

  public String getPpManageId() {
    return ppManageId;
  }

  public void setPpManageId(String ppManageId) {
    this.ppManageId = ppManageId;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getSpNumId() {
    return spNumId;
  }

  public void setSpNumId(String spNumId) {
    this.spNumId = spNumId;
  }

  public Integer getSpNumNum() {
    return spNumNum;
  }

  public void setSpNumNum(Integer spNumNum) {
    this.spNumNum = spNumNum;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getShoppingCart() {
    return shoppingCart;
  }

  public void setShoppingCart(Integer shoppingCart) {
    this.shoppingCart = shoppingCart;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }

  public String getProOrderId() {
    return proOrderId;
  }

  public void setProOrderId(String proOrderId) {
    this.proOrderId = proOrderId;
  }
}
