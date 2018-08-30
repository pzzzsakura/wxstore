package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 10:13
 * @desc: 主页轮播图
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class MainBanner {

  private String bannerId;
  private String bannerUrl;//本图片链接
  private Integer priority;//权重
  private String bannerRedirectId;//跳转详情主键
  private Date createTime;//创建时间
  private Date lastEditTime;//更新时间
  private MainBannerCategory mainBannerCategory;//种类
  private Integer enableStatus;//状态 0 不可用 1 可用

  public String getBannerRedirectId() {
    return bannerRedirectId;
  }

  public void setBannerRedirectId(String bannerRedirectId) {
    this.bannerRedirectId = bannerRedirectId;
  }

  public String getBannerId() {
    return bannerId;
  }

  public void setBannerId(String bannerId) {
    this.bannerId = bannerId;
  }

  public String getBannerUrl() {
    return bannerUrl;
  }

  public void setBannerUrl(String bannerUrl) {
    this.bannerUrl = bannerUrl;
  }

  public MainBannerCategory getMainBannerCategory() {
    return mainBannerCategory;
  }

  public void setMainBannerCategory(MainBannerCategory mainBannerCategory) {
    this.mainBannerCategory = mainBannerCategory;
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
}
