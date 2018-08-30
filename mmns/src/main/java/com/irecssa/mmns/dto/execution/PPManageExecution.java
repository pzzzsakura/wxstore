package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.PPManage;
import com.irecssa.mmns.enums.PPManageEnum;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 00:44
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class PPManageExecution {
  private int state;
  private String stateInfo;
  private PPManage ppManage;

  public PPManageExecution(PPManageEnum ppManageEnum,PPManage ppManage) {

    this.state = ppManageEnum.getState();
    this.stateInfo = ppManageEnum.getStateInfo();
    this.ppManage = ppManage;
  }

  public PPManageExecution(PPManageEnum ppManageEnum) {
    this.state = ppManageEnum.getState();
    this.stateInfo = ppManageEnum.getStateInfo();
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

  public PPManage getPpManage() {
    return ppManage;
  }

  public void setPpManage(PPManage ppManage) {
    this.ppManage = ppManage;
  }
}
