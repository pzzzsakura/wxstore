package com.irecssa.mmns.entity;

import java.util.Date;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 15:59
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Product {

  private String productId;
  private String productName;//商品名称
  private Integer normalMobi;//商品原价 单位膜币
  private Integer promotionMobi;//商品折扣价 单位膜币
  private Integer isBoom;//是否为爆款商品 1是 0否
  private Integer isCombo;//是否为套餐商品
  private String freeId;
  private Integer productIntegral;//商品对应积分
  private String productImg;//商品缩略图
  private Integer enableStatus;//商品可用状态 0不可用 1可用
  private String productIntro;//商品简介
  private Integer priority;//商品权重
  private Date boomDeadline;//爆款截止日期
  private Date createTime;//创建时间
  private Date lastEditTime;//更新时间
  private ProductCategory productCategory;//商品类别
  private ProductDesc productDesc;//商品描述
  private List<ProductBanner> productBannerList;//介绍轮播图
  private List<ProductImg> productImgList;//详情图
  private ProductRepertory productRepertory;//商品库存

  public String getFreeId() {
    return freeId;
  }

  public void setFreeId(String freeId) {
    this.freeId = freeId;
  }

  public Integer getIsCombo() {
    return isCombo;
  }
  public void setIsCombo(Integer isCombo) {
    this.isCombo = isCombo;
  }
  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Integer getNormalMobi() {
    return normalMobi;
  }

  public void setNormalMobi(Integer normalMobi) {
    this.normalMobi = normalMobi;
  }

  public Integer getPromotionMobi() {
    return promotionMobi;
  }

  public void setPromotionMobi(Integer promotionMobi) {
    this.promotionMobi = promotionMobi;
  }

  public Integer getIsBoom() {
    return isBoom;
  }

  public void setIsBoom(Integer isBoom) {
    this.isBoom = isBoom;
  }

  public Integer getProductIntegral() {
    return productIntegral;
  }

  public void setProductIntegral(Integer productIntegral) {
    this.productIntegral = productIntegral;
  }

  public String getProductImg() {
    return productImg;
  }

  public void setProductImg(String productImg) {
    this.productImg = productImg;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }

  public String getProductIntro() {
    return productIntro;
  }

  public void setProductIntro(String productIntro) {
    this.productIntro = productIntro;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Date getBoomDeadline() {
    return boomDeadline;
  }

  public void setBoomDeadline(Date boomDeadline) {
    this.boomDeadline = boomDeadline;
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

  public ProductCategory getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ProductCategory productCategory) {
    this.productCategory = productCategory;
  }

  public ProductDesc getProductDesc() {
    return productDesc;
  }

  public void setProductDesc(ProductDesc productDesc) {
    this.productDesc = productDesc;
  }

  public List<ProductBanner> getProductBannerList() {
    return productBannerList;
  }

  public void setProductBannerList(List<ProductBanner> productBannerList) {
    this.productBannerList = productBannerList;
  }

  public List<ProductImg> getProductImgList() {
    return productImgList;
  }

  public void setProductImgList(List<ProductImg> productImgList) {
    this.productImgList = productImgList;
  }

  public ProductRepertory getProductRepertory() {
    return productRepertory;
  }

  public void setProductRepertory(ProductRepertory productRepertory) {
    this.productRepertory = productRepertory;
  }
}
