package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/30 12:53
 * @desc: 参数属性值
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class PropertyValue {

  private String pvId;
  private Integer vid;
  private Property property;
  private String value;

  public String getPvId() {
    return pvId;
  }

  public void setPvId(String pvId) {
    this.pvId = pvId;
  }

  public Integer getVid() {
    return vid;
  }

  public void setVid(Integer vid) {
    this.vid = vid;
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
