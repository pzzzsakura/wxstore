package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.Reward;
import com.irecssa.mmns.enums.RewardEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 14:17
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class RewardExecution {

  private int state;
  private String stateInfo;
  private List<Reward> rewardList;
  private Reward reward;

  public RewardExecution(RewardEnum rewardEnum,List<Reward> rewardList) {
    this.rewardList = rewardList;
    this.state = rewardEnum.getState();
    this.stateInfo = rewardEnum.getStateInfo();
  }

  public RewardExecution(RewardEnum rewardEnum,Reward reward) {
    this.stateInfo = rewardEnum.getStateInfo();
    this.state = rewardEnum.getState();
    this.reward = reward;
  }
  public RewardExecution(RewardEnum rewardEnum) {
    this.stateInfo = rewardEnum.getStateInfo();
    this.state = rewardEnum.getState();
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

  public List<Reward> getRewardList() {
    return rewardList;
  }

  public void setRewardList(List<Reward> rewardList) {
    this.rewardList = rewardList;
  }

  public Reward getReward() {
    return reward;
  }

  public void setReward(Reward reward) {
    this.reward = reward;
  }
}
