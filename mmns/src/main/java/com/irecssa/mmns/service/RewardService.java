package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.RewardExecution;
import com.irecssa.mmns.entity.Reward;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 14:21
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface RewardService {

  RewardExecution addReward(Reward reward);

  RewardExecution getRewardList();

  RewardExecution getRewardById(String rewardId);

  RewardExecution modifyReward(Reward reward);

  RewardExecution removeReward(String rewardId);

}
