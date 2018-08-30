package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 17:03
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductRepertory {

  private String prorepId;
  private Integer saleNum;//销量
  private Integer currentNum;//当前存量
  private Date createTime;//创建时间
  private Date lastEditTime;//更新时间

  public String getProrepId() {
    return prorepId;
  }

  public void setProrepId(String prorepId) {
    this.prorepId = prorepId;
  }

  public Integer getSaleNum() {
    return saleNum;
  }

  public void setSaleNum(Integer saleNum) {
    this.saleNum = saleNum;
  }

  public Integer getCurrentNum() {
    return currentNum;
  }

  public void setCurrentNum(Integer currentNum) {
    this.currentNum = currentNum;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getLastEditTime() {
    return lastEditTime;
  }

  public void setLastEditTime(Date lastEditTime) {
    this.lastEditTime = lastEditTime;
  }
}
