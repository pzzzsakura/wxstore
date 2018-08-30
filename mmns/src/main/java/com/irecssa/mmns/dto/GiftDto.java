package com.irecssa.mmns.dto;

import com.irecssa.mmns.entity.Reward;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 13:45
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class GiftDto {

  private Reward reward;//奖品

  private double prob;//获奖概率

  public Reward getReward() {
    return reward;
  }

  public void setReward(Reward reward) {
    this.reward = reward;
  }

  public double getProb() {
    return prob;
  }

  public void setProb(double prob) {
    this.prob = prob;
  }
}
