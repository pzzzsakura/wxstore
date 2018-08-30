package com.irecssa.mmns.controller.game;

import com.irecssa.mmns.service.RewardService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 16:36
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/game")
public class RewardController {

  @Autowired
  private RewardService rewardService;

  /**
   * 得到奖品信息
   * @return
   */
  @RequestMapping(value = "getrewardlist",method = RequestMethod.POST)
  private Map<String,Object> getReward(){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    modelMap.put("success",true);
    modelMap.put("rewardList",rewardService.getRewardList());
    return modelMap;
  }


}
