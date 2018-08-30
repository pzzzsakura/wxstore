package com.irecssa.mmns.controller.game;

import com.irecssa.mmns.dto.execution.PersonInfoExecution;
import com.irecssa.mmns.dto.execution.PossessProperExecution;
import com.irecssa.mmns.dto.execution.ProperExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.entity.PossessProper;
import com.irecssa.mmns.entity.Proper;
import com.irecssa.mmns.entity.WechatAuth;
import com.irecssa.mmns.enums.PersonInfoEnum;
import com.irecssa.mmns.enums.PossessProperEnum;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.service.PersonInfoService;
import com.irecssa.mmns.service.PossessProperService;
import com.irecssa.mmns.service.ProperService;
import com.irecssa.mmns.service.WechatAuthService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 09:38
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/game")
public class
GameManageController {

  @Value("${tldvalue}")
  private String tldValue;
  @Value("${rolestr}")
  private String roleStr;
  @Value("${tsstr}")
  private String tsStr;
  @Value("${tldstr}")
  private String tldStr;

  @Autowired
  private WechatAuthService wechatAuthService;
  @Autowired
  private ProperService properService;
  @Autowired
  private PossessProperService possessProperService;
  @Autowired
  private PersonInfoService personInfoService;

  /**
   * 得到游戏积分排行
   */
  @RequestMapping(value = "getrank", method = RequestMethod.POST)
  private Map<String, Object> getRank(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    //String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthListOrderByGameIntegral();
    List<WechatAuth> wechatAuths = wechatAuthExecution.getWechatAuthList();
    modelMap.put("success", true);
    modelMap.put("wechatAuthList", wechatAuths);
    return modelMap;
  }

  /**
   * 得到我的角色和头饰列表
   */
  @RequestMapping(value = "getmypropers", method = RequestMethod.POST)
  private Map<String, Object> getPropers(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    int state = HttpServletRequestUtil.getInt(request, "state");//1 角色 2 头饰
    if (state != -1) {
      String openId = (String) request.getSession().getAttribute("openId");
      if (openId != null) {
        WechatAuthExecution wechatAuthExecution = wechatAuthService
            .getWechatAuthServiceByOpenId(openId);
        if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
          PersonInfo personInfo = wechatAuthExecution.getWechatAuth().getPersonInfo();
          String personInfoId = personInfo.getPersonInfoId();
          PossessProper p = new PossessProper();
          p.setPersonInfoId(personInfoId);
          PossessProperExecution possessProperExecution = possessProperService
              .queryPossessProperList(p);
          List<PossessProper> possessProperList = possessProperExecution.getPossessProperList();
          List<Proper> properList = new ArrayList<Proper>();
          List<Proper> myTss = new ArrayList<Proper>();
          if (state == 1) {
            List<Proper> myRoles = new ArrayList<Proper>();
            for (PossessProper possessProper : possessProperList) {
              if (possessProper.getProperId().equals(roleStr)) {
                ProperExecution properExecution = properService
                    .queryProperById(possessProper.getProperId());
                myRoles.add(properExecution.getProper());
              }
            }
            modelMap.put("myRoles", myRoles);//我的角色
          } else {
            for (PossessProper possessProper : possessProperList) {
              if (possessProper.getProperId().equals(tsStr)) {
                ProperExecution properExecution = properService
                    .queryProperById(possessProper.getProperId());
                myTss.add(properExecution.getProper());
              }
            }
            modelMap.put("myTss", myTss);//我的头饰
          }
          modelMap.put("success", true);
          return modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
          return modelMap;
        }
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", "请先关注公众号");
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "参数为空");
      return modelMap;
    }

  }

  /**
   * 更改默认头饰和角色
   */
  @RequestMapping(value ="changeproper", method = RequestMethod.POST)
  private Map<String, Object> changeHeadImg(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    if (openId != null) {
      int state = HttpServletRequestUtil.getInt(request, "state");//判断是角色还是头饰
      String properId = HttpServletRequestUtil.getString(request, "properId");
      String possessProperId = HttpServletRequestUtil.getString(request, "possessProperId");
      if (properId != null) {
        WechatAuthExecution wechatAuthExecution = wechatAuthService
            .getWechatAuthServiceByOpenId(openId);
        PersonInfo personInfo = wechatAuthExecution.getWechatAuth().getPersonInfo();
        String personInfoId = personInfo.getPersonInfoId();
        PossessProper possessProper = new PossessProper();
        if(state==2){
          possessProper.setPropercateId(tsStr);//头饰主键
        }
        if(state==1){
          possessProper.setPropercateId(roleStr);//角色主键
        }
        possessProper.setPersonInfoId(personInfoId);
        possessProper.setEnableStatus(0);
        try {
          PossessProperExecution possessProperExecution = possessProperService
              .updatePossessProper(possessProper);
          if (possessProperExecution.getState() == PossessProperEnum.SUCCESS.getState()) {
            possessProper.setPossessProperId(possessProperId);
            possessProper.setEnableStatus(1);
            try {
              PossessProperExecution ppe = possessProperService.updatePossessProper(possessProper);
              if (ppe.getState() == PossessProperEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                ProperExecution properExecution = properService.queryProperById(properId);
                modelMap.put("possessProper", possessProper);
                modelMap.put("proper", properExecution.getProper());
                return modelMap;
              } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", ppe.getStateInfo());
                return modelMap;
              }
            } catch (Exception e) {
              modelMap.put("success", false);
              modelMap.put("errMsg", e.getMessage());
              return modelMap;
            }
          } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", possessProperExecution.getStateInfo());
            return modelMap;
          }
        } catch (Exception e) {
          modelMap.put("success", false);
          modelMap.put("errMsg", e.getMessage());
          return modelMap;
        }

      } else {
        modelMap.put("success", true);
        modelMap.put("errMsg", "参数为空");
        return modelMap;
      }
    } else {
      modelMap.put("success", true);
      modelMap.put("errMsg", "登陆过期");
      return modelMap;
    }
  }


  /**
   * 购买道具
   */
  @Transactional
  @RequestMapping(value = "buyproper",method = RequestMethod.POST)
  public Map<String, Object> buyProper(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    //获取当前个人信息
    String properId = HttpServletRequestUtil.getString(request, "properId");
    if (properId != null || properId != "") {
      String openId = (String) request.getSession().getAttribute("openId");
      if (openId != null) {
        WechatAuthExecution wechatAuthExecution = wechatAuthService
            .getWechatAuthServiceByOpenId(openId);
        PersonInfo personInfo = wechatAuthExecution.getWechatAuth().getPersonInfo();
        String personInfoId = personInfo.getPersonInfoId();
        //判断是否积分充足
        ProperExecution properExecution = properService.queryProperById(properId);
        //得到种类id
        String propercateId = properExecution.getProper().getProperCategory().getPropercateId();
        int needIntegral = properExecution.getProper().getProperIntegral();
        if (needIntegral > (personInfo.getGameIntegral() + personInfo.getShopIntegral())) {
          modelMap.put("success", false);
          modelMap.put("errCode", -2001);//积分不足
          modelMap.put("errMsg", "您的积分不足噢");
          return modelMap;
        }
        //足够则减去积分然后增加体力
        //优先减去游戏积分，其次减去商城积分
        if (personInfo.getGameIntegral() > needIntegral) {
          personInfo.setGameIntegral(-needIntegral);
        } else {
          personInfo.setGameIntegral(-personInfo.getGameIntegral());
          personInfo.setShopIntegral(-(needIntegral - personInfo.getGameIntegral()));
        }
        //判断购买的是否为体力豆
        if (propercateId == tldStr) {
          personInfo.setPower(Integer.valueOf(tldValue));
          PersonInfoExecution personInfoExecution = personInfoService.modifyPersonInfo(personInfo);
          if (personInfoExecution.getState() == PersonInfoEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
            return modelMap;
          } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", personInfoExecution.getStateInfo());
            return modelMap;
          }
        }
        //购买角色,如果第一次设置为默认
        if (propercateId == roleStr) {
          PossessProper possessProper1 = new PossessProper();
          possessProper1.setPersonInfoId(personInfoId);
          List<PossessProper> roleList = (List<PossessProper>) possessProperService
              .queryPossessProperList(possessProper1);
          int count = 0;
          for (PossessProper possessProper : roleList) {
            ProperExecution p = properService.queryProperById(possessProper.getProperId());
            if (p.getProper().getProperCategory().getPropercateId() == propercateId) {
              count++;
            }
          }
          //设置默认
          PossessProper possessProper = new PossessProper();
          if (count == 0) {
            possessProper.setEnableStatus(1);
            //直接加入我的角色
          } else {
            possessProper.setEnableStatus(0);
          }
          possessProper.setPossessProperId(UUIDUtil.createUUID());
          possessProper.setPersonInfoId(personInfoId);
          possessProper.setProperId(properId);
          possessProper.setPropercateId(propercateId);
          PossessProperExecution possessProperExecution;

          try {
            possessProperExecution = possessProperService.insertPossessProper(possessProper);
            if (possessProperExecution.getState() == PossessProperEnum.SUCCESS.getState()) {
              modelMap.put("success", true);
              return modelMap;
            } else {
              modelMap.put("success", false);
              modelMap.put("errMsg", possessProperExecution.getStateInfo());
              return modelMap;
            }
          } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
          }
        }
        //购买头饰
        if (propercateId == tsStr) {
          PossessProper possessProper = new PossessProper();
          possessProper.setEnableStatus(0);
          possessProper.setProperId(properId);
          possessProper.setPersonInfoId(personInfoId);
          possessProper.setPossessProperId(UUIDUtil.createUUID());
          possessProper.setPropercateId(propercateId);
          try {
            PossessProperExecution possessProperExecution = possessProperService
                .insertPossessProper(possessProper);
            if (possessProperExecution.getState() == PossessProperEnum.SUCCESS.getState()) {
              modelMap.put("success", true);
              return modelMap;
            } else {
              modelMap.put("success", false);
              modelMap.put("errMsg", possessProperExecution.getStateInfo());
              return modelMap;
            }
          } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
          }
        }
        modelMap.put("success", false);
        modelMap.put("errMsg", "系统错误");
        return modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", "登陆过期");
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "参数为空");
      return modelMap;
    }
  }


}
