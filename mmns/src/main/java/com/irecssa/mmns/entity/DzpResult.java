package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/06 00:08
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class  DzpResult {

  private String drId;
  private String openId;
  private Reward reward;

  public String getDrId() {
    return drId;
  }

  public void setDrId(String drId) {
    this.drId = drId;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public Reward getReward() {
    return reward;
  }

  public void setReward(Reward reward) {
    this.reward = reward;
  }
}
