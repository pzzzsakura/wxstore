package com.irecssa.mmns.controller.wechat;

import com.irecssa.mmns.dto.UserAccessToken;
import com.irecssa.mmns.dto.WechatUser;
import com.irecssa.mmns.dto.execution.PersonInfoExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.exceptions.WechatAuthOperationException;
import com.irecssa.mmns.service.PersonInfoService;
import com.irecssa.mmns.service.WechatAuthService;
import com.irecssa.mmns.util.wechat.WechatUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 授权后登陆验证
 */
@Controller
@RequestMapping("wechat")
public class WechatLoginController {

  @Autowired
  private WechatAuthService wechatAuthService;

  @Autowired
  private PersonInfoService personInfoService;

  private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);

  @Value("${gamecode}")
  private String GAMECODE;//游戏界面
  @Value("${mallcode}")
  private String MALLCODE;//商城界面
  @Value("${qmmcode}")
  private String QMMCODE;//抢面膜分享页面
  @Value("${dzpcode}")
  private String DZPCODE;//大转盘分享页面
  @Value("${mmnscode}")
  private String MMNSCODE;//膜面女神分享页面

  @RequestMapping(value = "/login", method = {RequestMethod.GET})
  public String doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException, InterruptedException {
    log.debug("weixin login get...");
    // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
    String code = request.getParameter("code");
    // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
    String state = request.getParameter("state");
    log.debug("weixin login code:" + code);
    WechatUser user = null;
    String openId = null;
    if (null != code) {
      UserAccessToken token;
      try {
        // 通过code获取access_token
        token = WechatUtil.getUserAccessToken(code);
        log.debug("weixin login token:" + token.toString());
        // 通过token获取accessToken
        String accessToken = token.getAccessToken();
        Thread.sleep(10);
        // 通过token获取openId
        openId = token.getOpenId();
        if (openId == null) {
          return checkState(state);
        } else {
          // 通过access_token和openId获取用户昵称等信息
          user = WechatUtil.getUserInfo(accessToken, openId);
          log.debug("weixin login user:" + user.toString());
          request.getSession().setAttribute("openId", openId);
          PersonInfoExecution personInfoExecution = null;
          WechatAuthExecution wechatAuthExecution = wechatAuthService
              .getWechatAuthServiceByOpenId(openId);
          if (wechatAuthExecution.getWechatAuth() == null) {

            //注册
            int count = wechatAuthService.getWechatAuthCount();
            wechatAuthExecution = wechatAuthService.insertWechatAuth(user, count);
            if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
              if (state.equals(MALLCODE)) {
                request.getSession().setAttribute("state", 2);
                return "mall/guidance";
              }
              else if(state.equals(GAMECODE)){
                request.getSession().setAttribute("state", 1);
                return "mall/guidance";
              }else{
                return checkState(state);
              }
            } else {
              throw new WechatAuthOperationException(wechatAuthExecution.getStateInfo());
            }
          } else {
            return checkState(state);
          }
        }
      } catch (IOException e) {
        log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
        e.printStackTrace();
        return "IO Error";
      }
    } else {
      return checkState(state);
    }
  }

  private String checkState(String state) {
    if (state.equals(MALLCODE)) {
      return "mall/girlword";
    }
    if (state.equals(GAMECODE)) {
      return "game/gameindex";
    }
    if (state.equals(QMMCODE)) {
      return "game/qmmshare";
    }
    if (state.equals(DZPCODE)) {
      return "game/dzpshare";
    }
    if (state.equals(MMNSCODE)) {
      return "game/mmnsshare";
    }
    return null;
  }
}
