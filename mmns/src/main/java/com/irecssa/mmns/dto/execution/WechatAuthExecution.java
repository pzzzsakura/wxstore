package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.WechatAuth;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import java.util.List;

/**
 * Ma.li.ran 2017/11/20 0020 14:16
 */
public class WechatAuthExecution {

  private int state;
  private String stateInfo;
  private WechatAuth wechatAuth;
  private List<WechatAuth> wechatAuthList;
  private int count;

  public List<WechatAuth> getWechatAuthList() {
    return wechatAuthList;
  }

  public void setWechatAuthList(List<WechatAuth> wechatAuthList) {
    this.wechatAuthList = wechatAuthList;
  }

  public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum) {
    this.state = wechatAuthStateEnum.getState();
    this.stateInfo = wechatAuthStateEnum.getStateInfo();
  }

  public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum,WechatAuth wechatAuth) {
    this.state = wechatAuthStateEnum.getState();
    this.stateInfo = wechatAuthStateEnum.getStateInfo();
    this.wechatAuth = wechatAuth;
  }

  public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum,List<WechatAuth> wechatAuthList) {
    this.wechatAuthList = wechatAuthList;
    this.state = wechatAuthStateEnum.getState();
    this.stateInfo = wechatAuthStateEnum.getStateInfo();
  }

  public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum,WechatAuth wechatAuth,int count) {
    this.state = wechatAuthStateEnum.getState();
    this.stateInfo = wechatAuthStateEnum.getStateInfo();
    this.wechatAuth = wechatAuth;
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
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

  public WechatAuth getWechatAuth() {
    return wechatAuth;
  }

  public void setWechatAuth(WechatAuth wechatAuth) {
    this.wechatAuth = wechatAuth;
  }
}
