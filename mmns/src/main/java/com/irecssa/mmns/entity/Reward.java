package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 11:53
 * @desc: 大转盘奖品表
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Reward {

  private String rewardId;
  private String rewardName;//奖品名称
  private Integer rewardNum;//奖品当前数量
  private String rewardDesc;//奖品描述
  private String rewardImg;//奖品img
  private Integer enableStatus;//状态
  private Integer rewardSaleNum;//奖品已送出数量

  public String getRewardId() {
    return rewardId;
  }

  public void setRewardId(String rewardId) {
    this.rewardId = rewardId;
  }

  public String getRewardName() {
    return rewardName;
  }

  public void setRewardName(String rewardName) {
    this.rewardName = rewardName;
  }

  public Integer getRewardNum() {
    return rewardNum;
  }

  public void setRewardNum(Integer rewardNum) {
    this.rewardNum = rewardNum;
  }

  public String getRewardDesc() {
    return rewardDesc;
  }

  public void setRewardDesc(String rewardDesc) {
    this.rewardDesc = rewardDesc;
  }

  public String getRewardImg() {
    return rewardImg;
  }

  public void setRewardImg(String rewardImg) {
    this.rewardImg = rewardImg;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }

  public Integer getRewardSaleNum() {
    return rewardSaleNum;
  }

  public void setRewardSaleNum(Integer rewardSaleNum) {
    this.rewardSaleNum = rewardSaleNum;
  }
}
