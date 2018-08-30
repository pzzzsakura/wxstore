package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.RewardDao;
import com.irecssa.mmns.dto.execution.RewardExecution;
import com.irecssa.mmns.entity.Reward;
import com.irecssa.mmns.enums.RewardEnum;
import com.irecssa.mmns.exceptions.RewardException;
import com.irecssa.mmns.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 14:22
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class RewardServiceImpl implements RewardService {

  @Autowired
  private RewardDao rewardDao;

  @Transactional
  public RewardExecution addReward(Reward reward) {
    if(reward!=null){
      try {
        int result = rewardDao.insertReward(reward);
        if(result>0){
          return new RewardExecution(RewardEnum.SUCCESS,reward);
        }else{
          throw new RewardException("addReward error");
        }
      }catch (Exception e){
        throw new RewardException(e.getMessage());
      }
    }else{
      return new RewardExecution(RewardEnum.NULL_AUTH_INFO);
    }
  }

  public RewardExecution getRewardList() {
    return new RewardExecution(RewardEnum.SUCCESS,rewardDao.queryRewardList());
  }

  public RewardExecution getRewardById(String rewardId) {
    if(rewardId!=null&&rewardId!=""){
      Reward reward = rewardDao.queryRewardById(rewardId);
      return new RewardExecution(RewardEnum.SUCCESS,reward);
    }else{
      return new RewardExecution(RewardEnum.NULL_AUTH_INFO);
    }
  }

  @Transactional
  public RewardExecution modifyReward(Reward reward) {
    if(reward!=null){
      try {
        int result = rewardDao.updateReward(reward);
        if(result>0){
          return new RewardExecution(RewardEnum.SUCCESS,reward);
        }else{
          throw new RewardException("updateReward error");
        }
      }catch (Exception e){
        throw new RewardException(e.getMessage());
      }
    }else{
      return new RewardExecution(RewardEnum.NULL_AUTH_INFO);
    }
  }

  @Transactional
  public RewardExecution removeReward(String rewardId) {
    if(rewardId!=null&&rewardId!=""){
      try {
        int result = rewardDao.deleteReward(rewardId);
        if(result>0){
          return new RewardExecution(RewardEnum.SUCCESS);
        }else{
          throw new RewardException("deleteReward error");
        }
      }catch (Exception e){
        throw new RewardException(e.getMessage());
      }
    }else{
      return new RewardExecution(RewardEnum.NULL_AUTH_INFO);
    }
  }
}
