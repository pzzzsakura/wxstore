package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 15:30
 * @desc: 商品参数组合表
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class PPManage {

  private String ppManageId;
  private Integer ppmanageMobi;
  private Integer ppManageNum;
  private String productId;
  private Integer ppManageIntegral;
  private String ppUnique;//唯一串

  public String getPpUnique() {
    return ppUnique;
  }

  public void setPpUnique(String ppUnique) {
    this.ppUnique = ppUnique;
  }

  public Integer getPpManageIntegral() {
    return ppManageIntegral;
  }

  public void setPpManageIntegral(Integer ppManageIntegral) {
    this.ppManageIntegral = ppManageIntegral;
  }

  public String getPpManageId() {
    return ppManageId;
  }

  public void setPpManageId(String ppManageId) {
    this.ppManageId = ppManageId;
  }

  public Integer getPpmanageMobi() {
    return ppmanageMobi;
  }

  public void setPpmanageMobi(Integer ppmanageMobi) {
    this.ppmanageMobi = ppmanageMobi;
  }

  public Integer getPpManageNum() {
    return ppManageNum;
  }

  public void setPpManageNum(Integer ppManageNum) {
    this.ppManageNum = ppManageNum;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }
}
