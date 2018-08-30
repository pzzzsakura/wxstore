package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 11:00
 * @desc: 充值订单
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WechatOrder {

  private String orderId;
  private String name;
  private String openId;
  private String ip;
  private Integer payMoney;
  private String orderNum;
  private String accepter;
  private Date createTime;
  private Date finishTime;
  private Integer enableStatus;
  private String payBody;
  private String personInfoId;

  public String getPersonInfoId() {
    return personInfoId;
  }

  public void setPersonInfoId(String personInfoId) {
    this.personInfoId = personInfoId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Integer getPayMoney() {
    return payMoney;
  }

  public void setPayMoney(Integer payMoney) {
    this.payMoney = payMoney;
  }

  public String getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(String orderNum) {
    this.orderNum = orderNum;
  }

  public String getAccepter() {
    return accepter;
  }

  public void setAccepter(String accepter) {
    this.accepter = accepter;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Date finishTime) {
    this.finishTime = finishTime;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }

  public String getPayBody() {
    return payBody;
  }

  public void setPayBody(String payBody) {
    this.payBody = payBody;
  }
}
