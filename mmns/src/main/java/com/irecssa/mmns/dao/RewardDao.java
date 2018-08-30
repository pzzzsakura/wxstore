package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.Reward;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 14:03
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface RewardDao {

  int insertReward(Reward reward);

  List<Reward> queryRewardList();

  Reward queryRewardById(String rewardId);

  int updateReward(Reward reward);

  int deleteReward(String rewardId);
}
