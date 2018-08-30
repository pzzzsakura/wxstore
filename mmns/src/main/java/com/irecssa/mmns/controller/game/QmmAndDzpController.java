package com.irecssa.mmns.controller.game;

import com.irecssa.mmns.dto.GiftDto;
import com.irecssa.mmns.dto.execution.DzpResultExecution;
import com.irecssa.mmns.dto.execution.PersonInfoExecution;
import com.irecssa.mmns.dto.execution.PossessProperExecution;
import com.irecssa.mmns.dto.execution.ProperExecution;
import com.irecssa.mmns.dto.execution.QmmResultExecution;
import com.irecssa.mmns.dto.execution.RewardExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.DzpResult;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.entity.PossessProper;
import com.irecssa.mmns.entity.QmmResult;
import com.irecssa.mmns.entity.Reward;
import com.irecssa.mmns.enums.DzpResultEnum;
import com.irecssa.mmns.enums.PersonInfoEnum;
import com.irecssa.mmns.enums.QmmResultEnum;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.service.DzpResultService;
import com.irecssa.mmns.service.PersonInfoService;
import com.irecssa.mmns.service.PossessProperService;
import com.irecssa.mmns.service.ProperService;
import com.irecssa.mmns.service.QmmResultService;
import com.irecssa.mmns.service.RewardService;
import com.irecssa.mmns.service.WechatAuthService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 10:42
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/game")
public class QmmAndDzpController {

  @Value("${perconsumepower}")
  private String perConsumePower;
  @Value("${maxintegral}")
  private String maxIntegral;
  @Value("${perconsumeintegral}")
  private String perConsumeIntegral;
  @Value("${prob}")
  private String prob;
  @Value("${probsw}")
  private String probsw;
  @Value("${xiexie}")
  private String xiexie;
  @Value("${rolestr}")
  private String roleStr;

  @Autowired
  private PersonInfoService personInfoService;
  @Autowired
  private PossessProperService possessProperService;
  @Autowired
  private ProperService properService;
  @Autowired
  private WechatAuthService wechatAuthService;
  @Autowired
  private DzpResultService dzpResultService;
  @Autowired
  private RewardService rewardService;
  @Autowired
  private QmmResultService qmmResultService;

  /**
   * 初始化判断用户信息
   */
  @RequestMapping(value = "gameinit", method = RequestMethod.POST)
  private Map<String, Object> Gameinit(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    int check = HttpServletRequestUtil.getInt(request, "check");//1 抢面膜 2 大转盘
    String openId = (String) request.getSession().getAttribute("openId");
    if (openId == null || openId == "") {
      modelMap.put("success", false);
      modelMap.put("errCode", -10006);
      modelMap.put("errMsg", "登录过期");
      return modelMap;
    }
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      PersonInfo personInfo = wechatAuthExecution.getWechatAuth().getPersonInfo();
      if (personInfo.getPower() < Integer.valueOf(perConsumePower)) {
        modelMap.put("success", false);
        modelMap.put("errCode", -10007);
        modelMap.put("errMsg", "体力不足");
        return modelMap;
      }
      if (check == 1) {
        if (personInfo.getChanceQmm() == 0) {
          modelMap.put("success", false);
          modelMap.put("errCode", -10008);
          modelMap.put("errMsg", "您已经没有机会了");
          return modelMap;
        } else {
          //取出之前设置的默认角色
          PossessProper possessProper = new PossessProper();
          possessProper.setEnableStatus(1);
          possessProper.setPropercateId(roleStr);
          possessProper.setPersonInfoId(personInfo.getPersonInfoId());
          PossessProperExecution possessProperExecution = possessProperService
              .queryPossessProperList(possessProper);
          String properId = possessProperExecution.getPossessProper().getProperId();
          ProperExecution properExecution = properService.queryProperById(properId);
          if (properExecution.getProper() == null) {
            modelMap.put("success", false);
            modelMap.put("errCode", -10009);
            modelMap.put("errMsg", "没有角色");
            return modelMap;
          }
          modelMap.put("proper", properExecution.getProper());
        }
      }
      if (check == 2) {
        if (personInfo.getChanceDzp() == 0) {
          modelMap.put("success", false);
          modelMap.put("errCode", -10008);
          modelMap.put("errMsg", "您已经没有机会了");
          return modelMap;
        }
        if (personInfo.getGameIntegral() + personInfo.getShopIntegral() < Integer
            .valueOf(perConsumeIntegral)) {
          modelMap.put("success", false);
          modelMap.put("errCode", -10000);
          modelMap.put("errMsg", "积分不足");
          return modelMap;
        }
      }

      modelMap.put("success", true);
      modelMap.put("wechatAuth", wechatAuthExecution.getWechatAuth());
      return modelMap;
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 抢面膜结算
   */
  @RequestMapping(value = "qmmclearing", method = RequestMethod.POST)
  private Map<String, Object> QmmClearing(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Integer gameIntegral = HttpServletRequestUtil.getInt(request, "gameIntegral");
    if (gameIntegral > Integer.valueOf(maxIntegral)) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "产生系统异常，请重新开始");
      return modelMap;
    }
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      PersonInfo personInfo = new PersonInfo();
      if (wechatAuthExecution.getWechatAuth().getPersonInfo().getChanceQmm() >= 1
          && wechatAuthExecution.getWechatAuth().getPersonInfo().getPower() >= Integer
          .valueOf(perConsumePower)) {
        personInfo.setChanceQmm(-1);
        personInfo.setPower(-Integer.valueOf(perConsumePower));
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", "系统异常");
        return modelMap;
      }
      personInfo.setGameIntegral(gameIntegral);
      personInfo.setPersonInfoId(personInfoId);
      PersonInfoExecution personInfoExecution = personInfoService.modifyPersonInfo(personInfo);
      if (personInfoExecution.getState() == PersonInfoEnum.SUCCESS.getState()) {
        QmmResult qmmResult = new QmmResult();
        qmmResult.setOpenId(openId);
        qmmResult.setQrId(UUIDUtil.createUUID());
        qmmResult.setQrIntegral(gameIntegral);
        QmmResultExecution qmmResultExecution = qmmResultService.insertQmmResult(qmmResult);
        if (qmmResultExecution.getState() == QmmResultEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
          modelMap.put("qmmResult", qmmResult);//得到的主键为分享跳转页面链接参数
          modelMap.put("personInfo", personInfoExecution.getPersonInfo());
          return modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", qmmResultExecution.getStateInfo());
          return modelMap;
        }

      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", personInfoExecution.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }


  /**
   * 结算大转盘抽奖
   */
  @RequestMapping(value = "dzpclearing", method = RequestMethod.POST)
  private Map<String, Object> dzpClearing(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      PersonInfo personInfo = new PersonInfo();
      personInfo
          .setPersonInfoId(wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId());
      if ((wechatAuthExecution.getWechatAuth().getPersonInfo().getGameIntegral()
          + wechatAuthExecution.getWechatAuth().getPersonInfo().getShopIntegral()) < Integer
          .valueOf(perConsumeIntegral)
          && wechatAuthExecution.getWechatAuth().getPersonInfo().getChanceDzp() < 1) {
        modelMap.put("success", false);
        modelMap.put("errMsg", "系统异常");
        return modelMap;
      }
      if (personInfo.getGameIntegral() > Integer.valueOf(perConsumeIntegral)) {
        personInfo.setGameIntegral(-Integer.valueOf(perConsumeIntegral));
      } else {
        personInfo.setGameIntegral(-personInfo.getGameIntegral());
        personInfo
            .setShopIntegral(-(Integer.valueOf(perConsumeIntegral) - personInfo.getGameIntegral()));
      }
      personInfo.setChanceDzp(-1);
      Reward reward;
      synchronized (this) {
        reward = getResult();
      }
      DzpResult dzpResult = new DzpResult();
      dzpResult.setDrId(UUIDUtil.createUUID());
      dzpResult.setOpenId(openId);
      dzpResult.setReward(reward);

      try {
        PersonInfoExecution personInfoExecution = personInfoService.modifyPersonInfo(personInfo);
        if (personInfoExecution.getState() == PersonInfoEnum.SUCCESS.getState()) {
          try {
            DzpResultExecution dzpResultExecution = dzpResultService.insertDzpResult(dzpResult);
            if (dzpResultExecution.getState() == DzpResultEnum.SUCCESS.getState()) {
              modelMap.put("success", true);
              modelMap.put("personInfo", personInfoExecution.getPersonInfo());
              modelMap.put("reward", reward);
              modelMap.put("dzpResult",dzpResult);
              return modelMap;
            } else {
              modelMap.put("success", false);
              modelMap.put("errMsg", dzpResultExecution.getStateInfo());
              return modelMap;
            }
          } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
          }
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", personInfoExecution.getStateInfo());
          return modelMap;
        }
      } catch (Exception e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }

  /***
   * 奖品概率集合
   * @param giftList
   * @return
   */
  public static int drawGift(List<GiftDto> giftList) {

    if (null != giftList && giftList.size() > 0) {
      List<Double> orgProbList = new ArrayList<Double>(giftList.size());
      for (GiftDto gift : giftList) {
        //按顺序将概率添加到集合中
        orgProbList.add(gift.getProb());
      }
      return draw(orgProbList);

    }
    return -1;
  }

  /**
   * 产生随机数落入概率区间得到区间索引，概率区间相加获得
   */

  public static int draw(List<Double> giftProbList) {

    List<Double> sortRateList = new ArrayList<Double>();

    // 计算概率总和
    Double sumRate = 0D;
    for (Double prob : giftProbList) {
      sumRate += prob;
    }

    if (sumRate != 0) {
      double rate = 0D;   //概率所占比例
      for (Double prob : giftProbList) {
        rate += prob;
        // 构建一个比例区段组成的集合(避免概率和不为1)
        sortRateList.add(rate / sumRate);
      }

      // 随机生成一个随机数，并排序
      double random = Math.random();
      sortRateList.add(random);
      Collections.sort(sortRateList);

      // 返回该随机数在比例集合中的索引
      return sortRateList.indexOf(random);
    }

    return -1;
  }

  /**
   * 得到抽奖结果
   */
  public Reward getResult() {
    RewardExecution rewardExecution = rewardService.getRewardList();
    RewardExecution rewardExecution1 = rewardService.getRewardById(xiexie);
    Reward xReward = rewardExecution1.getReward();
    List<Reward> rewardList = rewardExecution.getRewardList();
    List<GiftDto> list = new ArrayList<GiftDto>();
    for (Reward reward : rewardList) {
      GiftDto gift = new GiftDto();
      if (reward.getEnableStatus() == 2) {//2邮递实物
        if (reward.getRewardSaleNum() + 1 == reward.getRewardNum()) {
          gift.setReward(xReward);
          gift.setProb((1 - Double.valueOf(prob)) * Double.valueOf(probsw));
        }
        gift.setReward(reward);
        gift.setProb((1 - Double.valueOf(prob)) * Double.valueOf(probsw));
      } else if (reward.getEnableStatus() == 1) {//1虚拟物品
        if (reward.getRewardSaleNum() + 1 == reward.getRewardNum()) {
          gift.setReward(xReward);
          gift.setProb((1 - Double.valueOf(prob)) * (1 - Double.valueOf(probsw)));
        }
        gift.setReward(reward);
        gift.setProb((1 - Double.valueOf(prob)) * (1 - Double.valueOf(probsw)));
      } else {//0 谢谢
        gift.setProb(Double.valueOf(prob));
        gift.setReward(reward);
      }
      list.add(gift);
    }
    int index = drawGift(list);
    Reward reward = list.get(index).getReward();
    reward.setRewardSaleNum(1);
    return reward;
  }
}
