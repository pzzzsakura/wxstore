package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.ProductProperty;
import com.irecssa.mmns.enums.ProductPropertyEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 15:13
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductPropertyExecution {

  private int state;
  private String stateInfo;
  private List<ProductProperty> productPropertyList;
  private ProductProperty productProperty;

  public ProductPropertyExecution(ProductPropertyEnum productPropertyEnum,List<ProductProperty> productPropertyList) {
    this.state = productPropertyEnum.getState();
    this.stateInfo = productPropertyEnum.getStateInfo();
    this.productPropertyList = productPropertyList;
  }

  public ProductPropertyExecution(ProductPropertyEnum productPropertyEnum,ProductProperty productProperty) {
    this.state = productPropertyEnum.getState();
    this.stateInfo = productPropertyEnum.getStateInfo();
    this.productProperty = productProperty;
  }

  public ProductPropertyExecution(ProductPropertyEnum productPropertyEnum) {
    this.state = productPropertyEnum.getState();
    this.stateInfo = productPropertyEnum.getStateInfo();
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

  public List<ProductProperty> getProductPropertyList() {
    return productPropertyList;
  }

  public void setProductPropertyList(List<ProductProperty> productPropertyList) {
    this.productPropertyList = productPropertyList;
  }

  public ProductProperty getProductProperty() {
    return productProperty;
  }

  public void setProductProperty(ProductProperty productProperty) {
    this.productProperty = productProperty;
  }
}
