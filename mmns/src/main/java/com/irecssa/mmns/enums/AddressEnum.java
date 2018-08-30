package com.irecssa.mmns.enums;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 13:32
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public enum AddressEnum {
 CANNOTOPERATION(-2,"默认地址不能删除"), LOGINFAIL(-1, "参数输入有误"), SUCCESS(0, "操作成功"), NULL_AUTH_INFO(-1006, "参数为空"),NULLOFRESULT(-1007,"结果为空");

  private int state;

  private String stateInfo;


  AddressEnum(int state, String stateInfo) {
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
