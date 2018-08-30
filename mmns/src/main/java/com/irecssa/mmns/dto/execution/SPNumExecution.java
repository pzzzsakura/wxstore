package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.SPNum;
import com.irecssa.mmns.enums.SPNumEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 09:31
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class SPNumExecution {

  private int state;
  private String stateInfo;
  private SPNum spNum;
  private List<SPNum> spNumList;

  public SPNumExecution(SPNumEnum spNumEnum) {
    this.state = spNumEnum.getState();
    this.stateInfo = spNumEnum.getStateInfo();
  }

  public SPNumExecution(SPNumEnum spNumEnum,SPNum spNum) {
    this.state = spNumEnum.getState();
    this.stateInfo = spNumEnum.getStateInfo();
    this.spNum = spNum;
  }
  public SPNumExecution(SPNumEnum spNumEnum, List<SPNum> spNumList) {
    this.state = spNumEnum.getState();
    this.stateInfo = spNumEnum.getStateInfo();
    this.spNumList = spNumList;
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

  public SPNum getSpNum() {
    return spNum;
  }

  public void setSpNum(SPNum spNum) {
    this.spNum = spNum;
  }

  public List<SPNum> getSpNumList() {
    return spNumList;
  }

  public void setSpNumList(List<SPNum> spNumList) {
    this.spNumList = spNumList;
  }
}
