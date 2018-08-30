package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 16:44
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductBanner {

  private String probannerId;
  private String probannerUrl;//链接
  private Integer priority;//权重
  private Date createTime;//创建时间
  private Date lastEditTime;//更新时间
  private Integer enableStatus;//状态 0 不可用 1可用
  private String productId;//商品主键

  public String getProbannerId() {
    return probannerId;
  }

  public void setProbannerId(String probannerId) {
    this.probannerId = probannerId;
  }

  public String getProbannerUrl() {
    return probannerUrl;
  }

  public void setProbannerUrl(String probannerUrl) {
    this.probannerUrl = probannerUrl;
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

  public Date getLastEditTime() {
    return lastEditTime;
  }

  public void setLastEditTime(Date lastEditTime) {
    this.lastEditTime = lastEditTime;
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
