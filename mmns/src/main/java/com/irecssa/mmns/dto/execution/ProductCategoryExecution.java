package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.ProductCategory;
import com.irecssa.mmns.enums.ProductCategoryEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 17:33
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductCategoryExecution {

  private int state;
  private String stateInfo;
  private ProductCategory productCategory;
  private List<ProductCategory> productCategoryList;

  public List<ProductCategory> getProductCategoryList() {
    return productCategoryList;
  }

  public void setProductCategoryList(List<ProductCategory> productCategoryList) {
    this.productCategoryList = productCategoryList;
  }

  public ProductCategoryExecution(ProductCategoryEnum productCategoryEnum,
      List<ProductCategory> productCategoryList) {
    this.state = productCategoryEnum.getState();
    this.stateInfo = productCategoryEnum.getStateInfo();
    this.productCategoryList = productCategoryList;
  }

  public ProductCategoryExecution(ProductCategoryEnum productCategoryEnum,
     ProductCategory productCategory) {
    this.state = productCategoryEnum.getState();
    this.stateInfo = productCategoryEnum.getStateInfo();
    this.productCategory = productCategory;
  }

  public ProductCategoryExecution(ProductCategoryEnum productCategoryEnum) {
    this.state = productCategoryEnum.getState();
    this.stateInfo = productCategoryEnum.getStateInfo();
  }


  public ProductCategory getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ProductCategory productCategory) {
    this.productCategory = productCategory;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getStateInfo() {
    return stateInfo;
  }

  public void setStateInfo(String stateInfo) {
    this.stateInfo = stateInfo;
  }


}
