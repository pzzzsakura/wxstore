package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.Proper;
import com.irecssa.mmns.enums.ProperEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 15:51
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProperExecution {

  private int state;
  private String stateInfo;
  private Proper proper;
  private List<Proper> properList;


  public ProperExecution(ProperEnum properEnum,List<Proper> properList) {
    this.state=properEnum.getState();
    this.stateInfo=properEnum.getStateInfo();
    this.properList = properList;
  }

  public ProperExecution(ProperEnum properEnum,Proper proper) {
    this.state=properEnum.getState();
    this.stateInfo=properEnum.getStateInfo();
    this.proper = proper;
  }
  public ProperExecution(ProperEnum properEnum) {
    this.state=properEnum.getState();
    this.stateInfo=properEnum.getStateInfo();
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

  public Proper getProper() {
    return proper;
  }

  public void setProper(Proper proper) {
    this.proper = proper;
  }

  public List<Proper> getProperList() {
    return properList;
  }

  public void setProperList(List<Proper> properList) {
    this.properList = properList;
  }
}
