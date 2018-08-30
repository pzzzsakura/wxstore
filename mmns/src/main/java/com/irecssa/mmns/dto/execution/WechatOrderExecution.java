package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.WechatOrder;
import com.irecssa.mmns.enums.WechatOrderEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 11:18
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WechatOrderExecution {

  private int state;
  private String stateInfo;
  private List<WechatOrder> wechatOrderList;
  private WechatOrder wechatOrder;

  public WechatOrderExecution(WechatOrderEnum wechatOrderEnum,List<WechatOrder> wechatOrderList) {
    this.wechatOrderList = wechatOrderList;
    this.state=wechatOrderEnum.getState();
    this.stateInfo=wechatOrderEnum.getStateInfo();
  }

  public WechatOrderExecution(WechatOrderEnum wechatOrderEnum,WechatOrder wechatOrder) {
    this.wechatOrder = wechatOrder;
    this.state = wechatOrderEnum.getState();
    this.stateInfo = wechatOrderEnum.getStateInfo();
  }

  public WechatOrderExecution(WechatOrderEnum wechatOrderEnum) {
    this.state = wechatOrderEnum.getState();
    this.stateInfo = wechatOrderEnum.getStateInfo();
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

  public List<WechatOrder> getWechatOrderList() {
    return wechatOrderList;
  }

  public void setWechatOrderList(List<WechatOrder> wechatOrderList) {
    this.wechatOrderList = wechatOrderList;
  }

  public WechatOrder getWechatOrder() {
    return wechatOrder;
  }

  public void setWechatOrder(WechatOrder wechatOrder) {
    this.wechatOrder = wechatOrder;
  }
}
