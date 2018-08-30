package com.irecssa.mmns.controller.welcome;

import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.WechatAuth;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.service.WechatAuthService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/26 17:29
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class MyMallController {

  @Autowired
  private WechatAuthService wechatAuthService;

  @RequestMapping(value = "getmyinfo",method = RequestMethod.POST)
  private Map<String,Object> getMyInfo(HttpServletRequest request){
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      WechatAuth wechatAuth = wechatAuthExecution.getWechatAuth();

      modelMap.put("success", true);
      modelMap.put("wechatAuth", wechatAuthExecution.getWechatAuth());
      return modelMap;
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }
}
