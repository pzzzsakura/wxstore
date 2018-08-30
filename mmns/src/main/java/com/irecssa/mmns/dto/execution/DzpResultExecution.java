package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.DzpResult;
import com.irecssa.mmns.enums.DzpResultEnum;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/06 00:10
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class DzpResultExecution {

  private int state;
  private String stateInfo;
  private DzpResult dzpResult;

  public DzpResultExecution(DzpResultEnum dzpResultEnum,DzpResult dzpResult) {
    this.dzpResult = dzpResult;
    this.state=dzpResultEnum.getState();
    this.stateInfo=dzpResultEnum.getStateInfo();
  }
  public DzpResultExecution(DzpResultEnum dzpResultEnum) {
    this.state=dzpResultEnum.getState();
    this.stateInfo=dzpResultEnum.getStateInfo();
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

  public DzpResult getDzpResult() {
    return dzpResult;
  }

  public void setDzpResult(DzpResult dzpResult) {
    this.dzpResult = dzpResult;
  }
}
