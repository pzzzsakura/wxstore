package com.irecssa.mmns.controller.game;

import com.irecssa.mmns.dto.execution.DzpResultExecution;
import com.irecssa.mmns.dto.execution.PersonInfoExecution;
import com.irecssa.mmns.dto.execution.QmmResultExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.enums.DzpResultEnum;
import com.irecssa.mmns.enums.PersonInfoEnum;
import com.irecssa.mmns.enums.QmmResultEnum;
import com.irecssa.mmns.service.DzpResultService;
import com.irecssa.mmns.service.PersonInfoService;
import com.irecssa.mmns.service.QmmResultService;
import com.irecssa.mmns.service.WechatAuthService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/05 21:58
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Controller
@RequestMapping(value = "/game")
public class GameShareController {


  @Value("${maxchanceqmm}")
  private String maxChanceQmm;
  @Value("${peraddchance}")
  private String perAddChance;
  @Autowired
  private QmmResultService qmmResultService;
  @Autowired
  private WechatAuthService wechatAuthService;
  @Autowired
  private PersonInfoService personInfoService;
  @Autowired
  private DzpResultService dzpResultService;

  /**
   * 获取抢面膜分享页面信息
   * @return
   */
  @RequestMapping(value = "qmmshareresult",method = RequestMethod.POST)
  private Map<String, Object> qmmShareResult(HttpServletRequest request) {
    Map<String,Object> modelMap = new HashMap<String,Object>();
    String qrId = HttpServletRequestUtil.getString(request,"qrId");//结算时给的id
    //获取抢面膜结果当条信息
    QmmResultExecution qmmResultExecution = qmmResultService.queryQmmResult(qrId);
    if(qmmResultExecution.getState()== QmmResultEnum.SUCCESS.getState()){
      WechatAuthExecution wechatAuthExecution = wechatAuthService.getWechatAuthServiceByOpenId(qmmResultExecution.getQmmResult().getOpenId());
      modelMap.put("success",true);
      modelMap.put("qmmResult",qmmResultExecution.getQmmResult());
      modelMap.put("wechatAuth",wechatAuthExecution.getWechatAuth());
      return modelMap;
    }else{
      modelMap.put("success",false);
      modelMap.put("errMsg",qmmResultExecution.getStateInfo());
      return modelMap;
    }
  }
  /**
   * 获取大转盘分享页面信息
   * @return
   */
  @RequestMapping(value = "dzpshareresult",method = RequestMethod.POST)
  private Map<String, Object> dzpShareResult(HttpServletRequest request) {
    Map<String,Object> modelMap = new HashMap<String,Object>();
    String drId = HttpServletRequestUtil.getString(request,"drId");
    //获取大转盘结果当条信息
    DzpResultExecution dzpResultExecution = dzpResultService.queryDzpResult(drId);
    if(dzpResultExecution.getState()== DzpResultEnum.SUCCESS.getState()){
      WechatAuthExecution wechatAuthExecution = wechatAuthService.getWechatAuthServiceByOpenId(dzpResultExecution.getDzpResult().getOpenId());
      modelMap.put("success",true);
      modelMap.put("dzpResult",dzpResultExecution.getDzpResult());
      modelMap.put("wechatAuth",wechatAuthExecution.getWechatAuth());
      return modelMap;
    }else{
      modelMap.put("success",false);
      modelMap.put("errMsg",dzpResultExecution.getStateInfo());
      return modelMap;
    }
  }
  /**
   * 获取膜面女神分享页面信息
   * @return
   */
  @RequestMapping(value = "mmnsshareresult",method = RequestMethod.POST)
  private Map<String, Object> mmnsShareResult(HttpServletRequest request) {
    Map<String,Object> modelMap = new HashMap<String,Object>();
    String dzpShareStr = HttpServletRequestUtil.getString(request,"dzpShareStr");
    //获取膜面女神投票信息

    modelMap.put("success", true);
    return modelMap;
  }

  /**
   * 抢面膜确认分享回调函数
   * @param request
   * @return
   */
  @RequestMapping(value = "sureqmmshare",method = RequestMethod.POST)
  private Map<String, Object> sureQmmShare(HttpServletRequest request) {
    Map<String,Object> modelMap = new HashMap<String,Object>();
    String openId = HttpServletRequestUtil.getString(request,"openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService.getWechatAuthServiceByOpenId(openId);
    PersonInfo personInfo = wechatAuthExecution.getWechatAuth().getPersonInfo();
    if(personInfo.getChanceQmm()==Integer.valueOf(maxChanceQmm)){
      modelMap.put("success", false);
      modelMap.put("errCode",-2100);
      modelMap.put("errMsg","机会已经达到最大值了噢");
      return modelMap;
    }else{
      if((personInfo.getChanceQmm()+Integer.valueOf(perAddChance))>Integer.valueOf(maxChanceQmm)){
        personInfo.setChanceQmm(12-personInfo.getChanceQmm());
      }else{
        personInfo.setChanceQmm(Integer.valueOf(perAddChance));
      }
        try {
          PersonInfoExecution personInfoExecution = personInfoService.modifyPersonInfo(personInfo);
          if(personInfoExecution.getState()== PersonInfoEnum.SUCCESS.getState()){
            modelMap.put("success", true);
            modelMap.put("personInfo",personInfoExecution.getPersonInfo());
            return modelMap;
          }else{
            modelMap.put("success", false);
            modelMap.put("errMsg",personInfoExecution.getStateInfo());
            return modelMap;
          }
        }catch (Exception e){
          modelMap.put("success", false);
          modelMap.put("errMsg",e.getMessage());
          return modelMap;
        }

      }
    }
}
