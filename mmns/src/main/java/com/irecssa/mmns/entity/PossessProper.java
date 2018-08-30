package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 23:27
 * @desc: 拥有道具表
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class PossessProper {

  private String possessProperId;
  private String properId;
  private String personInfoId;
  private String propercateId;
  private Integer enableStatus;

  public String getPropercateId() {
    return propercateId;
  }

  public void setPropercateId(String propercateId) {
    this.propercateId = propercateId;
  }

  public String getPossessProperId() {
    return possessProperId;
  }

  public void setPossessProperId(String possessProperId) {
    this.possessProperId = possessProperId;
  }

  public String getProperId() {
    return properId;
  }

  public void setProperId(String properId) {
    this.properId = properId;
  }

  public String getPersonInfoId() {
    return personInfoId;
  }

  public void setPersonInfoId(String personInfoId) {
    this.personInfoId = personInfoId;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }
}
