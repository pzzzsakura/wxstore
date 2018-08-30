package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 16:29
 * @desc: 商品类别
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductCategory {

  private String procateId;
  private String procateName;//名称
  private Integer priority;//权重
  private Date createTime;//创建时间
  private Date lastEditTime;//更新时间
  private String procateNum;//编号
  private Integer enableStatus;//状态 1可用 0不可用
  private ProductCategory parent;//父级信息

  public ProductCategory getParent() {
    return parent;
  }

  public void setParent(ProductCategory parent) {
    this.parent = parent;
  }

  public String getProcateId() {
    return procateId;
  }

  public void setProcateId(String procateId) {
    this.procateId = procateId;
  }

  public String getProcateName() {
    return procateName;
  }

  public void setProcateName(String procateName) {
    this.procateName = procateName;
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

  public String getProcateNum() {
    return procateNum;
  }

  public void setProcateNum(String procateNum) {
    this.procateNum = procateNum;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }
}
