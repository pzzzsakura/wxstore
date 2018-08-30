package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.Free;
import com.irecssa.mmns.enums.FreeEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 17:11
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class FreeExecution {

  private int state;
  private String stateInfo;
  private Free free;
  private List<Free> freeList;

  public FreeExecution(FreeEnum freeEnum,Free free) {
    this.state = freeEnum.getState();
    this.stateInfo = freeEnum.getStateInfo();
    this.free = free;
  }
  public FreeExecution(FreeEnum freeEnum) {
    this.state = freeEnum.getState();
    this.stateInfo = freeEnum.getStateInfo();
  }
  public FreeExecution(FreeEnum freeEnum,List<Free> freeList) {
    this.state = freeEnum.getState();
    this.stateInfo = freeEnum.getStateInfo();
    this.freeList = freeList;
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

  public Free getFree() {
    return free;
  }

  public void setFree(Free free) {
    this.free = free;
  }

  public List<Free> getFreeList() {
    return freeList;
  }

  public void setFreeList(List<Free> freeList) {
    this.freeList = freeList;
  }
}
