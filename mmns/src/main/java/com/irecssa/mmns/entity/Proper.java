package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 15:04
 * @desc: 游戏商城道具表
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Proper {

  private String properId;
  private String properName;
  private String properDesc;
  private String properUrl;
  private Integer properIntegral;
  private Date createTime;
  private Date lastEditTime;
  private Integer priority;
  private ProperCategory properCategory;
  private String properOrder;

  public String getProperId() {
    return properId;
  }

  public void setProperId(String properId) {
    this.properId = properId;
  }

  public String getProperName() {
    return properName;
  }

  public void setProperName(String properName) {
    this.properName = properName;
  }

  public String getProperDesc() {
    return properDesc;
  }

  public void setProperDesc(String properDesc) {
    this.properDesc = properDesc;
  }

  public String getProperUrl() {
    return properUrl;
  }

  public void setProperUrl(String properUrl) {
    this.properUrl = properUrl;
  }

  public Integer getProperIntegral() {
    return properIntegral;
  }

  public void setProperIntegral(Integer properIntegral) {
    this.properIntegral = properIntegral;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getLastEditTime() {
    return lastEditTime;
  }

  public void setLastEditTime(Date lastEditTime) {
    this.lastEditTime = lastEditTime;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public ProperCategory getProperCategory() {
    return properCategory;
  }

  public void setProperCategory(ProperCategory properCategory) {
    this.properCategory = properCategory;
  }

  public String getProperOrder() {
    return properOrder;
  }

  public void setProperOrder(String properOrder) {
    this.properOrder = properOrder;
  }
}
