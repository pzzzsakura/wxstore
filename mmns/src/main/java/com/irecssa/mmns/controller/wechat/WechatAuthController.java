package com.irecssa.mmns.controller.wechat;

import com.irecssa.mmns.util.wechat.ConfigUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Ma.li.ran 2017/11/20 0020 12:03
 */
@Controller
@RequestMapping("/wechat")
public class WechatAuthController {

  @Value("${domainurl}")
  private String DOMAINURL;
  @RequestMapping(value = "auth",method = { RequestMethod.GET })
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String backUrl = DOMAINURL+"/mmns/wechat/login";
    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ ConfigUtil.APPID
    +"&redirect_uri="+backUrl
        +"&response_type=code"
        +"&scope=snsapi_userinfo"
        +"&state="+ req.getParameter("state")+"#wechat_redirect";
    resp.sendRedirect(url);
  }
}
