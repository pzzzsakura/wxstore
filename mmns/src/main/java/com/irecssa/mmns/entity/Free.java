package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 17:05
 * @desc: 赠送物品信息
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Free {

  private String freeId;
  private String freeName;//名称
  private Integer freeMobi;//膜币
  private String freeDesc;//描述
  private Integer freeNum;

  public Integer getFreeNum() {
    return freeNum;
  }

  public void setFreeNum(Integer freeNum) {
    this.freeNum = freeNum;
  }

  public String getFreeId() {
    return freeId;
  }

  public void setFreeId(String freeId) {
    this.freeId = freeId;
  }

  public String getFreeName() {
    return freeName;
  }

  public void setFreeName(String freeName) {
    this.freeName = freeName;
  }

  public Integer getFreeMobi() {
    return freeMobi;
  }

  public void setFreeMobi(Integer freeMobi) {
    this.freeMobi = freeMobi;
  }

  public String getFreeDesc() {
    return freeDesc;
  }

  public void setFreeDesc(String freeDesc) {
    this.freeDesc = freeDesc;
  }
}
