package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 16:35
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductDesc {

  private String prodescId;
  private String prodescText;//描述
  private Date createTime;//创建时间

  public String getProdescId() {
    return prodescId;
  }

  public void setProdescId(String prodescId) {
    this.prodescId = prodescId;
  }

  public String getProdescText() {
    return prodescText;
  }

  public void setProdescText(String prodescText) {
    this.prodescText = prodescText;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
