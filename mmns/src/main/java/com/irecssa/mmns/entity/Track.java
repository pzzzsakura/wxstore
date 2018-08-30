package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/29 10:17
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Track {

  private String trackId;

  private String trackName;//快递公司名称

  private String proorderId;//订单编号

  private String com;//快递公司编号

  private String num;//快递单号

  public String getTrackId() {
    return trackId;
  }

  public void setTrackId(String trackId) {
    this.trackId = trackId;
  }

  public String getProorderId() {
    return proorderId;
  }

  public void setProorderId(String proorderId) {
    this.proorderId = proorderId;
  }

  public String getCom() {
    return com;
  }

  public void setCom(String com) {
    this.com = com;
  }

  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }
}
