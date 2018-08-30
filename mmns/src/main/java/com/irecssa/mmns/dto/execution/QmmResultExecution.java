package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.QmmResult;
import com.irecssa.mmns.enums.QmmResultEnum;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/05 23:15
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class QmmResultExecution {

  private int state;
  private String stateInfo;
  private QmmResult qmmResult;

  public QmmResultExecution(QmmResultEnum qmmResultEnum,QmmResult qmmResult) {
    this.qmmResult = qmmResult;
    this.state = qmmResultEnum.getState();
    this.stateInfo = qmmResultEnum.getStateInfo();
  }

  public QmmResultExecution(QmmResultEnum qmmResultEnum) {
    this.state = qmmResultEnum.getState();
    this.stateInfo = qmmResultEnum.getStateInfo();
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

  public QmmResult getQmmResult() {
    return qmmResult;
  }

  public void setQmmResult(QmmResult qmmResult) {
    this.qmmResult = qmmResult;
  }
}
