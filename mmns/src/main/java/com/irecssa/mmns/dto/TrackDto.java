package com.irecssa.mmns.dto;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/29 00:11
 * @desc: 快递查询dto
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class TrackDto {


  private String id;//身份授权key，请 快递查询接口 进行申请（大小写敏感）

  private String com;//要查询的快递公司代码，不支持中文

  private String nu;//快递单号

  private String show;//返回字符串 0 json，1xml,2html,3text

  private String muti;//返回信息数量

  private String order;//desc,asc 按时间排序

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCom() {
    return com;
  }

  public void setCom(String com) {
    this.com = com;
  }

  public String getNu() {
    return nu;
  }

  public void setNu(String nu) {
    this.nu = nu;
  }

  public String getShow() {
    return show;
  }

  public void setShow(String show) {
    this.show = show;
  }

  public String getMuti() {
    return muti;
  }

  public void setMuti(String muti) {
    this.muti = muti;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }
}
