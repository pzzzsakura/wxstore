package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 17:00
 * @desc: 商品介绍图
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductImg {

  private String proimgId;
  private String proimgUrl;
  private Integer priority;
  private Date createTime;
  private Integer enableStatus;
  private String productId;

  public String getProimgId() {
    return proimgId;
  }

  public void setProimgId(String proimgId) {
    this.proimgId = proimgId;
  }

  public String getProimgUrl() {
    return proimgUrl;
  }

  public void setProimgUrl(String proimgUrl) {
    this.proimgUrl = proimgUrl;
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

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }
}
