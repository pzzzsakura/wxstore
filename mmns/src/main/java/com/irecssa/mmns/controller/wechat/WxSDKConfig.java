package com.irecssa.mmns.controller.wechat;

import com.irecssa.mmns.dto.Result;
import com.irecssa.mmns.dto.execution.TicketExecution;
import com.irecssa.mmns.entity.Ticket;
import com.irecssa.mmns.enums.ResultEnum;
import com.irecssa.mmns.enums.TicketEnum;
import com.irecssa.mmns.exceptions.TicketException;
import com.irecssa.mmns.service.TicketService;
import com.irecssa.mmns.util.UUIDUtil;
import com.irecssa.mmns.util.wechat.ConfigUtil;
import com.irecssa.mmns.util.wechat.GetAT;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ma.li.ran 2017/10/28 0028 11:52
 */

@RestController
@RequestMapping("/wechat")
public class WxSDKConfig {
  @Autowired
  private TicketService ticketService;

  @PostMapping("config")
  public Result getSignature(@RequestParam(name = "url") String url) throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    String jsapi_ticket = checkTicket();
    String noncestr = generateNonceStr();
    String timestamp = String.valueOf(getCurrentTimestamp());
    String signature = getSign(timestamp, noncestr, jsapi_ticket, url);
    map.put("nonceStr", noncestr);
    map.put("timestamp", timestamp);
    map.put("signature", signature);
    map.put("appId", ConfigUtil.APPID);
    map.put("url", url);
    if (signature != null && jsapi_ticket != null) {
      return new Result(true, map);
    } else {
      return new Result(false, ResultEnum.UNKNOWN_ERROR.getMsg(),
          ResultEnum.UNKNOWN_ERROR.getCode());
    }

  }

  /**
   * 获取随机字符串 Nonce Str
   *
   * @return String 随机字符串
   */
  public static String generateNonceStr() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
  }

  /**
   * 获取当前时间戳，单位秒
   */
  public static long getCurrentTimestamp() {
    return System.currentTimeMillis() / 1000;
  }

  /**
   * 得到签名
   */
  public static String getSign(String timestamp, String noncestr, String jsapi_ticket, String url) {

    String arr[] = new String[]{"jsapi_ticket=" + jsapi_ticket, "noncestr=" + noncestr,
        "timestamp=" + timestamp, "url=" + url};
    Arrays.sort(arr);//字典序排序
    String str = "";
    str = arr[0] + "&" + arr[1] + "&" + arr[2] + "&" + arr[3];
    System.out.println(str);
    String mParms = null;//sha1加密
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("SHA");
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    digest.update(str.getBytes());
    byte messageDigest[] = digest.digest();
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < messageDigest.length; i++) {
      String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
      if (shaHex.length() < 2) {
        hexString.append(0);
      }
      hexString.append(shaHex);
    }
    return hexString.toString();
  }

  /***
   * 判断ticket是否超时
   * @return
   */

  private String checkTicket() throws IOException {
    TicketExecution ticketExecution = ticketService.queryTicket();
    String jsapi_ticket = null;
    if(ticketExecution.getState() == TicketEnum.SUCCESS.getState()) {
      if (ticketExecution.getTicketList() != null && ticketExecution.getTicketList().size() > 0) {
        Ticket ticket = ticketExecution.getTicketList().get(0);
        Date currentTime = new Date();
        Date lastTime = ticket.getTime();
        Integer expireIn = ticket.getExpireIn();
        if((currentTime.getTime()-lastTime.getTime())>=expireIn){
          jsapi_ticket = GetAT.getTicket().get("ticket");
          ticket.setTicket(jsapi_ticket);
          ticket.setTime(new Date());
          ticketService.updateTicket(ticket);
        }else{
          jsapi_ticket = ticket.getTicket();
        }
      } else {
        jsapi_ticket = GetAT.getTicket().get("ticket");
        Ticket ticket = new Ticket();
        ticket.setExpireIn(6000);
        ticket.setTime(new Date());
        ticket.setTicket(jsapi_ticket);
        ticket.setTicketId(UUIDUtil.createUUID());
        ticketService.insertTicket(ticket);
      }
      return jsapi_ticket;
    }else{
      throw new TicketException(ticketExecution.getStateInfo());
    }


  }

}
