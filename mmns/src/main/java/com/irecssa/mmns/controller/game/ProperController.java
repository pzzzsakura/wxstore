package com.irecssa.mmns.controller.game;

import com.irecssa.mmns.dto.execution.ProperExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.entity.PossessProper;
import com.irecssa.mmns.entity.Proper;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.service.PossessProperService;
import com.irecssa.mmns.service.ProperService;
import com.irecssa.mmns.service.WechatAuthService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 23:11
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/game")
public class ProperController {


  @Autowired
  private PossessProperService possessProperService;
  @Autowired
  private ProperService properService;
  @Autowired
  private WechatAuthService wechatAuthService;

  @RequestMapping(value = "addproper", method = RequestMethod.POST)
  private Map<String, Object> addProper(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    //上传信息
    //上传图片

    return modelMap;

  }

  /**
   * 得到游戏的商城道具列表
   */
  @RequestMapping(value = "getproperlist", method = RequestMethod.POST)
  private Map<String, Object> getProperList(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    //判断当前用户拥有什么头饰和角色，如果拥有则商城不予展示，体力豆除外
    String personInfoId="";
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService.getWechatAuthServiceByOpenId(openId);
    if(wechatAuthExecution.getState()== WechatAuthStateEnum.SUCCESS.getState()){
      PersonInfo personInfo = wechatAuthExecution.getWechatAuth().getPersonInfo();
      personInfoId = personInfo.getPersonInfoId();
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
    //过滤掉用户拥有的角色和道具
    ProperExecution properExecution = properService.queryProperList();
    PossessProper possessProper1 = new PossessProper();
    possessProper1.setPersonInfoId(personInfoId);
    List<PossessProper> possessProperList = (List<PossessProper>) possessProperService.queryPossessProperList(possessProper1);
    List<Proper> properList = properExecution.getProperList();
    for(Proper proper:properList){
      for(PossessProper possessProper:possessProperList){
        if(proper.getProperId()==possessProper.getProperId()){
          properList.remove(proper);
        }
      }
    }
    modelMap.put("success", true);
    modelMap.put("properList", properExecution.getProperList());
    return modelMap;
  }
}