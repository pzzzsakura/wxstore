package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.enums.ProductEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/22 09:53
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductExecution {

  private int state;
  private String stateInfo;
  private Product product;
  private int count;
  private List<Product> productList;

  public ProductExecution(ProductEnum productEnum) {
    this.state = productEnum.getState();
    this.stateInfo = productEnum.getStateInfo();
  }
  public ProductExecution(ProductEnum productEnum,Product product) {
    this.state = productEnum.getState();
    this.stateInfo = productEnum.getStateInfo();
    this.product = product;
  }
  public ProductExecution(ProductEnum productEnum,List<Product> productList,int count) {
    this.state = productEnum.getState();
    this.stateInfo = productEnum.getStateInfo();
    this.productList = productList;
    this.count = count;
  }
  public ProductExecution(ProductEnum productEnum,int count) {
    this.state = productEnum.getState();
    this.stateInfo = productEnum.getStateInfo();
    this.count = count;
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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<Product> getProductList() {
    return productList;
  }

  public void setProductList(List<Product> productList) {
    this.productList = productList;
  }
}
