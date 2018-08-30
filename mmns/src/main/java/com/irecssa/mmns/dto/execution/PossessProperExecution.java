package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.PossessProper;
import com.irecssa.mmns.enums.PossessProperEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 23:37
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class PossessProperExecution {

  private int state;
  private String stateInfo;
  private PossessProper possessProper;
  private List<PossessProper> possessProperList;

  public PossessProperExecution(PossessProperEnum possessProperEnum,List<PossessProper> possessProperList) {
    this.possessProperList = possessProperList;
    this.state = possessProperEnum.getState();
    this.stateInfo = possessProperEnum.getStateInfo();
  }

  public PossessProperExecution(PossessProperEnum possessProperEnum,PossessProper possessProper) {
    this.state = possessProperEnum.getState();
    this.stateInfo = possessProperEnum.getStateInfo();
    this.possessProper = possessProper;
  }
  public PossessProperExecution(PossessProperEnum possessProperEnum) {
    this.state = possessProperEnum.getState();
    this.stateInfo = possessProperEnum.getStateInfo();
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

  public PossessProper getPossessProper() {
    return possessProper;
  }

  public void setPossessProper(PossessProper possessProper) {
    this.possessProper = possessProper;
  }

  public List<PossessProper> getPossessProperList() {
    return possessProperList;
  }

  public void setPossessProperList(List<PossessProper> possessProperList) {
    this.possessProperList = possessProperList;
  }
}
