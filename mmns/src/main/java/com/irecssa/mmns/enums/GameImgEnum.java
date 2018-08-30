package com.irecssa.mmns.enums;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/02 13:57
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public enum GameImgEnum {
  LOGINFAIL(-1, "参数输入有误"), SUCCESS(0, "操作成功"),RESULTNULL(-1003,"列表为空");

  private int state;

  private String stateInfo;


  GameImgEnum(int state, String stateInfo) {
    this.state = state;
    this.stateInfo = stateInfo;
  }

  public int getState() {
    return state;
  }

  public String getStateInfo() {
    return stateInfo;
  }
}
