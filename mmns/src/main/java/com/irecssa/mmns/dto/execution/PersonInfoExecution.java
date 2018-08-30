package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.enums.PersonInfoEnum;

/**
 * Ma.li.ran 2017/11/20 0020 14:56
 */
public class PersonInfoExecution {

  private int state;
  private String stateInfo;
  private PersonInfo personInfo;

  public PersonInfoExecution(PersonInfoEnum personInfoEnum) {
    this.state = personInfoEnum.getState();
    this.stateInfo = personInfoEnum.getStateInfo();
  }
  public PersonInfoExecution(PersonInfoEnum personInfoEnum,PersonInfo personInfo) {
    this.state = personInfoEnum.getState();
    this.stateInfo = personInfoEnum.getStateInfo();
    this.personInfo = personInfo;
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

  public PersonInfo getPersonInfo() {
    return personInfo;
  }

  public void setPersonInfo(PersonInfo personInfo) {
    this.personInfo = personInfo;
  }
}
