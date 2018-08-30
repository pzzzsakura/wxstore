package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/01 11:31
 * @desc: 游戏界面
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class GameImg {

  private String gameImgId;//游戏界面图片id
  private String gameImgUrl;//链接
  private Integer priority;//权重
  private Date createTime;//创建时间
  private Integer enableStatus;//状态

  public String getGameImgId() {
    return gameImgId;
  }

  public void setGameImgId(String gameImgId) {
    this.gameImgId = gameImgId;
  }

  public String getGameImgUrl() {
    return gameImgUrl;
  }

  public void setGameImgUrl(String gameImgUrl) {
    this.gameImgUrl = gameImgUrl;
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
