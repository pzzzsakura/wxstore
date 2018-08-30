package com.irecssa.mmns.enums;

/**
 * Ma.li.ran 2017/11/20 0020 14:59
 */
public enum PersonInfoEnum {
  MOBIISDECLINE(-2,"膜币不足"),LOGINFAIL(-1, "参数输入有误"), SUCCESS(0, "操作成功"), NULL_AUTH_INFO(-1006, "注册信息为空");

  private int state;

  private String stateInfo;

  PersonInfoEnum(int state, String stateInfo) {
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
