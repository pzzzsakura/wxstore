package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/05 23:09
 * @desc: 抢面膜结果记录表
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class QmmResult {

  private String qrId;
  private String openId;
  private Integer qrIntegral;

  public String getQrId() {
    return qrId;
  }

  public void setQrId(String qrId) {
    this.qrId = qrId;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public Integer getQrIntegral() {
    return qrIntegral;
  }

  public void setQrIntegral(Integer qrIntegral) {
    this.qrIntegral = qrIntegral;
  }
}
