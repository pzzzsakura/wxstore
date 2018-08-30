package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.ProductOrder;
import com.irecssa.mmns.entity.Track;
import com.irecssa.mmns.enums.ProductOrderEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 16:42
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductOrderExecution {

  private int state;
  private String stateInfo;
  private ProductOrder productOrder;
  private List<ProductOrder> productOrderList;
  private Track track;

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
  }

  public ProductOrderExecution(ProductOrderEnum productOrderEnum, ProductOrder productOrder) {
    this.state = productOrderEnum.getState();
    this.stateInfo = productOrderEnum.getStateInfo();
    this.productOrder = productOrder;
  }

  public ProductOrderExecution(ProductOrderEnum productOrderEnum, ProductOrder productOrder,Track track) {
    this.state = productOrderEnum.getState();
    this.stateInfo = productOrderEnum.getStateInfo();
    this.productOrder = productOrder;
    this.track=track;
  }

  public ProductOrderExecution(ProductOrderEnum productOrderEnum,
      List<ProductOrder> productOrderList) {
    this.state = productOrderEnum.getState();
    this.stateInfo = productOrderEnum.getStateInfo();
    this.productOrderList = productOrderList;
  }

  public ProductOrderExecution(ProductOrderEnum productOrderEnum) {
    this.stateInfo = productOrderEnum.getStateInfo();
    this.state = state;
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

  public ProductOrder getProductOrder() {
    return productOrder;
  }

  public void setProductOrder(ProductOrder productOrder) {
    this.productOrder = productOrder;
  }

  public List<ProductOrder> getProductOrderList() {
    return productOrderList;
  }

  public void setProductOrderList(List<ProductOrder> productOrderList) {
    this.productOrderList = productOrderList;
  }
}
