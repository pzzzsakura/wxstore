package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.Combo;
import com.irecssa.mmns.enums.ComboEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 17:45
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ComboExecution {

  private int state;
  private String stateInfo;
  private Combo combo;
  private List<Combo> comboList;

  public ComboExecution(ComboEnum comboEnum) {
    this.state = comboEnum.getState();
    this.stateInfo = comboEnum.getStateInfo();
  }

  public ComboExecution(ComboEnum comboEnum, Combo combo) {
    this.state = comboEnum.getState();
    this.stateInfo = comboEnum.getStateInfo();
    this.combo = combo;
  }

  public ComboExecution(ComboEnum comboEnum, List<Combo> comboList) {
    this.state = comboEnum.getState();
    this.stateInfo = comboEnum.getStateInfo();
    this.comboList = comboList;
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

  public Combo getCombo() {
    return combo;
  }

  public void setCombo(Combo combo) {
    this.combo = combo;
  }

  public List<Combo> getComboList() {
    return comboList;
  }

  public void setComboList(List<Combo> comboList) {
    this.comboList = comboList;
  }
}
