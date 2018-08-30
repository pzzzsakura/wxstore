package com.irecssa.mmns.enums;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 11:16
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public enum WechatOrderEnum {
  LOGINFAIL(-1, "openId输入有误"), SUCCESS(0, "操作成功"), NULL_AUTH_INFO(-1006, "注册信息为空"),
  WECHATAUTHNULL(-1009,"表是空的");
  private int state;

  private String stateInfo;

  private WechatOrderEnum(int state, String stateInfo) {
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
