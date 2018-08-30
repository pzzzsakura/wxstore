package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 15:09
 * @desc: 游戏商城道具种类
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProperCategory {

  private String propercateId;
  private String propercateName;
  private String propercateOrder;
  private Date createTime;
  private Date lastEditTime;

  public String getPropercateId() {
    return propercateId;
  }

  public void setPropercateId(String propercateId) {
    this.propercateId = propercateId;
  }

  public String getPropercateName() {
    return propercateName;
  }

  public void setPropercateName(String propercateName) {
    this.propercateName = propercateName;
  }

  public String getPropercateOrder() {
    return propercateOrder;
  }

  public void setPropercateOrder(String propercateOrder) {
    this.propercateOrder = propercateOrder;
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
}
