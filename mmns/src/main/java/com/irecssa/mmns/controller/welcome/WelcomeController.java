package com.irecssa.mmns.controller.welcome;


import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.service.WechatAuthService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 新注册用户启动页欢迎页面
 * Ma.li.ran 2017/11/20 0020 17:36
 */
@RestController
@RequestMapping(value = "/welcome")
public class WelcomeController {
  @Value("${mallcode}")
  private String MALLCODE;
  @Value("${gamecode}")
  private String GAMECODE;
//  private static final String MALLCODE = "2";//商城界面
//  private static final String GAMECODE = "1";//游戏界面
  @Autowired
  private WechatAuthService wechatAuthService;

  @RequestMapping(value = "getuserinitdata", method = RequestMethod.POST)
  private Map<String, Object> getUserInitData(HttpServletRequest request) {
  System.out.println(MALLCODE);
    String state = request.getSession().getAttribute("state")+"";
    String openId = (String) request.getSession().getAttribute("openId");
    Map<String, Object> modelMap = new HashMap<String, Object>();
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      modelMap.put("success", true);
      modelMap.put("headImg", wechatAuthExecution.getWechatAuth().getWechatHeadimg());
      modelMap.put("sex", wechatAuthExecution.getWechatAuth().getWechatSex());//1,0
      modelMap.put("name",wechatAuthExecution.getWechatAuth().getWechatName());
      modelMap.put("rank", wechatAuthExecution.getWechatAuth().getNum());
      modelMap.put("state",state);//1游戏2商城
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
    }
    return modelMap;
  }
}
