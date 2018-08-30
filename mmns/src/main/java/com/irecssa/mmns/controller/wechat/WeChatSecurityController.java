package com.irecssa.mmns.controller.wechat;

import com.irecssa.mmns.util.wechat.SignUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Ma.li.ran
 * 2017/10/27  12:47
 */
@RestController
@RequestMapping("/wechat")
public class WeChatSecurityController{
  private static Logger logger = Logger.getLogger(WeChatSecurityController.class);

  @RequestMapping(value = "security",method = { RequestMethod.GET })
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String signature = req.getParameter("signature");
    String timestamp = req.getParameter("timestamp");
    String nonce = req.getParameter("nonce");
    String echostr = req.getParameter("echostr");
    if(SignUtil.checkSignature(signature, timestamp, nonce)){
      PrintWriter out = null;
      try {
        out = resp.getWriter();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      out.println(echostr);
      out.close();
    }

    else{
      logger.info("配置失败");
    }
  }

  @RequestMapping(method = { RequestMethod.POST })
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  }
}
