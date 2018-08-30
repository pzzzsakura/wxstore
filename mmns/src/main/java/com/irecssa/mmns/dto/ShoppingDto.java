package com.irecssa.mmns.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/26 09:50
 * @desc: 封装购物车结算商品
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingDto {

  @JsonProperty("ppManageId")
  private String ppManageId;//参数组合
  @JsonProperty("productId")
  private String productId;//当前商品
  @JsonProperty("checkNum")
  private Integer checkNum;//选择数量
  @JsonProperty("spNumId")
  private String spNumId;//购物车id

  public String getSpNumId() {
    return spNumId;
  }
  public void setSpNumId(String spNumId) {
    this.spNumId = spNumId;
  }

  public String getPpManageId() {
    return ppManageId;
  }

  public void setPpManageId(String ppManageId) {
    this.ppManageId = ppManageId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Integer getCheckNum() {
    return checkNum;
  }

  public void setCheckNum(Integer checkNum) {
    this.checkNum = checkNum;
  }
}
