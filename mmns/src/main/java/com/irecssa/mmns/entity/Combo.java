package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 17:33
 * @desc: 套餐详情
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Combo extends Product{

  private String comboId;//同时在product表里统一主键
  private String comboName;//套餐名称
  private Free free;//赠品
  private Product aProduct;//商品A
  private Product bProduct;//商品B
  private Product cProduct;//商品C

  public String getComboName() {
    return comboName;
  }

  public void setComboName(String comboName) {
    this.comboName = comboName;
  }

  public String getComboId() {
    return comboId;
  }

  public void setComboId(String comboId) {
    this.comboId = comboId;
  }

  public Free getFree() {
    return free;
  }

  public void setFree(Free free) {
    this.free = free;
  }

  public Product getaProduct() {
    return aProduct;
  }

  public void setaProduct(Product aProduct) {
    this.aProduct = aProduct;
  }

  public Product getbProduct() {
    return bProduct;
  }

  public void setbProduct(Product bProduct) {
    this.bProduct = bProduct;
  }

  public Product getcProduct() {
    return cProduct;
  }

  public void setcProduct(Product cProduct) {
    this.cProduct = cProduct;
  }
}

