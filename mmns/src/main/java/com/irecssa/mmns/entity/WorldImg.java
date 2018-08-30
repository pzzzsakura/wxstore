package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/02 23:49
 * @desc: 世界图
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WorldImg {

  private String worldImgId;
  private String worldImgUrl;
  private Integer priority;
  private Date createTime;
  private Integer enableStatus;

  public String getWorldImgId() {
    return worldImgId;
  }

  public void setWorldImgId(String worldImgId) {
    this.worldImgId = worldImgId;
  }

  public String getWorldImgUrl() {
    return worldImgUrl;
  }

  public void setWorldImgUrl(String worldImgUrl) {
    this.worldImgUrl = worldImgUrl;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }
}
