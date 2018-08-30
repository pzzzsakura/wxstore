package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 16:15
 * @desc: 商品订单
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductOrder {

  private String proorderId;
  private String accepter;//收款方
  private String proorderNumber;//订单编号 6位随机大写字符加6位随机数字+时间戳
  private Date createTime;//创建时间
  private String proorderDesc;//订单描述
  private String proorderMsg;//买家留言
  private Integer totalMobi;//总价
  private Integer expressFee;//快递费
  private Integer enableStatus;//状态 ，1待支付，2代发货，3待收货，4已完成
  private Date lastEditTime;
  private String addressId;//收货地址
  private String personInfoId;//个人信息
  private Date payTime;//支付时间
  private Date putTime;//发货时间
  private Integer productMobi;//商品价格
  private Integer productIntegral;//总积分


  public Date getLastEditTime() {
    return lastEditTime;
  }

  public void setLastEditTime(Date lastEditTime) {
    this.lastEditTime = lastEditTime;
  }

  public Integer getProductIntegral() {
    return productIntegral;
  }

  public void setProductIntegral(Integer productIntegral) {
    this.productIntegral = productIntegral;
  }

  public Date getPutTime() {
    return putTime;
  }

  public void setPutTime(Date putTime) {
    this.putTime = putTime;
  }

  public Integer getProductMobi() {
    return productMobi;
  }

  public void setProductMobi(Integer productMobi) {
    this.productMobi = productMobi;
  }

  public Date getPayTime() {
    return payTime;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

  public String getPersonInfoId() {
    return personInfoId;
  }

  public void setPersonInfoId(String personInfoId) {
    this.personInfoId = personInfoId;
  }

  public String getProorderId() {
    return proorderId;
  }

  public void setProorderId(String proorderId) {
    this.proorderId = proorderId;
  }

  public String getAccepter() {
    return accepter;
  }

  public void setAccepter(String accepter) {
    this.accepter = accepter;
  }

  public String getProorderNumber() {
    return proorderNumber;
  }

  public void setProorderNumber(String proorderNumber) {
    this.proorderNumber = proorderNumber;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getProorderDesc() {
    return proorderDesc;
  }

  public void setProorderDesc(String proorderDesc) {
    this.proorderDesc = proorderDesc;
  }

  public String getProorderMsg() {
    return proorderMsg;
  }

  public void setProorderMsg(String proorderMsg) {
    this.proorderMsg = proorderMsg;
  }

  public Integer getTotalMobi() {
    return totalMobi;
  }

  public void setTotalMobi(Integer totalMobi) {
    this.totalMobi = totalMobi;
  }

  public Integer getExpressFee() {
    return expressFee;
  }

  public void setExpressFee(Integer expressFee) {
    this.expressFee = expressFee;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }
}
