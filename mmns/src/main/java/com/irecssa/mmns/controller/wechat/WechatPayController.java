package com.irecssa.mmns.controller.wechat;

import com.irecssa.mmns.dto.execution.PersonInfoExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.dto.execution.WechatOrderExecution;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.entity.WechatAuth;
import com.irecssa.mmns.entity.WechatOrder;
import com.irecssa.mmns.enums.PersonInfoEnum;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.enums.WechatOrderEnum;
import com.irecssa.mmns.exceptions.PersonInfoOperationException;
import com.irecssa.mmns.exceptions.WechatAuthOperationException;
import com.irecssa.mmns.exceptions.WechatOrderException;
import com.irecssa.mmns.service.PersonInfoService;
import com.irecssa.mmns.service.WechatAuthService;
import com.irecssa.mmns.service.WechatOrderService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import com.irecssa.mmns.util.UUIDUtil;
import com.irecssa.mmns.util.wechat.ConfigUtil;
import com.irecssa.mmns.util.wechat.ConfigUtil.SignType;
import com.irecssa.mmns.util.wechat.WechatPayUtil;
import com.irecssa.mmns.util.wechat.WechatUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 09:42
 * @desc: 充值，调用微信支付接口
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/wechat")
public class WechatPayController {

  @Value("${acceptername}")
  private String ACCEPTERNAME;
  @Value("${desc2}")
  private String DESC2;
  @Value("${ratio}")
  private String RATIO;
  @Value("${apikey}")
  private String API_KEY;
  @Autowired
  private WechatOrderService wechatOrderService;
  @Autowired
  private WechatAuthService wechatAuthService;
  @Autowired
  private PersonInfoService personInfoService;

  @RequestMapping(value = "/couponsConfirm", method = RequestMethod.POST)
  public Map<String, Object> couponsConfirm(HttpServletRequest request, HttpServletResponse resp)
      throws Exception {
    //获得客户端ip
    String ip = request.getHeader("X-Forwarded-For");
    if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
      //多次反向代理后会有多个ip值，第一个ip才是真实ip
      int index = ip.indexOf(",");
      if (index != -1) {
        ip = ip.substring(0, index);
      }
    }
    ip = request.getHeader("X-Real-IP");
    if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    ip = request.getRemoteAddr();

    //获取openId
    String openId = (String) request.getSession().getAttribute("openId");
    //orderNo是你的商品订单号，自行生成的随机订单号，但是要保证随机性，不能有重复订单号。
    String orderNum = UUIDUtil.createUUID();
    System.out.println(orderNum);
    //支付姓名
    WechatAuth wechatAuth = getUserName(request,openId);
    String personInfoId = wechatAuth.getPersonInfo().getPersonInfoId();
    String payName = wechatAuth.getWechatName();
    //支付金额 按分计
    String payMoney = HttpServletRequestUtil.getString(request,"payMoney");//获取金额
    //支付描述
    String payBody = DESC2;
    //形成未完成支付订单
    Map<String, Object> map = new HashMap<String, Object>();
    if(createWechatOrder(ip,payName,openId,payBody,payMoney,personInfoId)){
      //获取预支付id
      long timeStamp = WechatUtil.getCurrentTimestamp();
      String nonceStr = WechatUtil.generateUUID();
      map.put("appId", ConfigUtil.APPID);
      map.put("timestamp", timeStamp);
      map.put("nonceStr", nonceStr);
      //map.put("openid",openId);
      map.put("signType", "MD5");
      double money = Double.valueOf(payMoney) * 100;
      //System.out.println(Long.toString(Math.round(a)));
      String prepayId = WechatPayUtil
          .unifiedorder(ip, Long.toString(Math.round(money)), payBody, orderNum, openId);
      // String userAgent = request.getHeader(“user-agent”);
      // char agent = userAgent.charAt(userAgent.indexOf(“MicroMessenger”)+15);
      // m.addAttribute(“agent”, new String(new char[]{agent}));//微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。

      // 生成支付签名，要采用URLENCODER的原始值进行SHA1算法！
      SortedMap<String, String> signParams = new TreeMap<String, String>();
      signParams.put("appId", ConfigUtil.APPID);
      signParams.put("nonceStr", nonceStr);
      signParams.put("package", "prepay_id=" + prepayId);
      signParams.put("timeStamp", String.valueOf(timeStamp));
      signParams.put("signType", "MD5");
      String sign = WechatUtil.generateSignature(signParams, API_KEY, SignType.MD5);

      map.put("paySign", sign);
      map.put("prepayId", prepayId);
      map.put("success",true);
      return map;
    }else{
      map.put("success",false);
      map.put("errMsg","形成订单失败");
      return map;
    }
  }
  /**
   * 处理 HTTPS API返回数据，转换成Map对象。return_code为SUCCESS时，验证签名。
   * @param  request API返回的XML格式数据
   * @return Map类型数据
   * @throws Exception
   */
  @RequestMapping("/paycallback")
  public String processResponseXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
    String RETURN_CODE = "return_code";
    String return_code;
    Map<String, String> respData = WechatUtil.xmlToMap(request.toString());
    Map<String,String> result = new HashMap<String,String>();
    result.put("return_code", ConfigUtil.FAIL);
    if (respData.containsKey(RETURN_CODE)) {
      return_code = respData.get(RETURN_CODE);
    }
    else {
      throw new Exception(String.format("No `return_code` in XML"));
    }

    if (return_code.equals(ConfigUtil.FAIL)) {
      result.remove("return_code");
      result.put("return_code", ConfigUtil.FAIL);
    }
    else if (return_code.equals(ConfigUtil.SUCCESS)) {
      if (this.isResponseSignatureValid(respData)) {
              //结果校验成功后，更新订单状态为已完成，并转换膜币
              WechatOrder wechatOrder = new WechatOrder();
              wechatOrder.setOrderNum(respData.get("out_trade_no"));
              wechatOrder.setEnableStatus(2);
              wechatOrder.setFinishTime(new Date());
              try {
                WechatOrderExecution wechatOrderExecution = wechatOrderService.modifyWechatOrder(wechatOrder);
                if(wechatOrderExecution.getState()==WechatOrderEnum.SUCCESS.getState()){
                  //更新用户账户膜币
                   wechatOrder = wechatOrderExecution.getWechatOrder();
                  if(updateUserMobi(wechatOrder.getPersonInfoId(),wechatOrder.getPayMoney())){
                    result.remove("return_code");
                    result.put("return_code", ConfigUtil.SUCCESS);
                  }else{
                    throw new WechatOrderException("更新用户账户膜币失败");
                  }
                }else{
                  throw new WechatOrderException("更新微信订单状态失败"+wechatOrderExecution.getStateInfo());
                }
              }catch (Exception e){
                throw new WechatOrderException("更新微信订单状态失败"+e.getMessage());
              }
            }
      else {
        throw new Exception(String.format("Invalid sign value in XML"));
      }
    }
    else {
      throw new Exception(String.format("return_code value %s is invalid in XML", return_code));
    }
    String resultMSG = WechatUtil.mapToXml(result);
    return resultMSG;
  }

  /**
   * 判断xml数据的sign是否有效，必须包含sign字段，否则返回false。
   *
   * @param reqData 向wxpay post的请求数据
   * @return 签名是否有效
   * @throws Exception
   */
  public boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
    // 返回数据的签名方式和请求中给定的签名方式是一致的
    return WechatUtil.isSignatureValid(reqData,API_KEY, SignType.MD5);
  }

  /**
   * 获取当前微信用户信息
   * @param request
   * @return
   */
  public WechatAuth getUserName(HttpServletRequest request,String openId) {
    WechatAuthExecution wechatAuthExecution = wechatAuthService.getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      WechatAuth wechatAuth = wechatAuthExecution.getWechatAuth();
      return wechatAuth;
     } else {
      throw new WechatAuthOperationException(wechatAuthExecution.getStateInfo());
    }
  }

  /**
   * 形成初始订单
   */
  private boolean createWechatOrder(String ip,String payName,String openId,String payBody,String payMoney,String personInfoId){
    WechatOrder wechatOrder = new WechatOrder();
    wechatOrder.setAccepter(ACCEPTERNAME);
    wechatOrder.setCreateTime(new Date());
    wechatOrder.setFinishTime(new Date());
    wechatOrder.setEnableStatus(1);
    wechatOrder.setPersonInfoId(personInfoId);
    wechatOrder.setIp(ip);
    wechatOrder.setName(payName);
    wechatOrder.setOpenId(openId);
    wechatOrder.setOrderId(UUIDUtil.createUUID());
    wechatOrder.setOrderNum(UUIDUtil.createOrderNum());
    wechatOrder.setPayBody(payBody);
    wechatOrder.setPayMoney(Integer.valueOf(payMoney));
    try {
      WechatOrderExecution wechatOrderExecution = wechatOrderService.addWechatOrder(wechatOrder);
      if(wechatOrderExecution.getState()== WechatOrderEnum.SUCCESS.getState()) {
        return true;
      }else{
        throw new WechatOrderException(wechatOrderExecution.getStateInfo());
      }
    }catch (Exception e){
      throw new WechatOrderException(e.getMessage());
    }
  }

  /**
   * 更新用户账户膜币
   */
  private boolean updateUserMobi(String personInfoId,Integer mobi){
    Integer userMobi = mobi*Integer.valueOf(RATIO);
    PersonInfo personInfo = new PersonInfo();
    personInfo.setPersonInfoId(personInfoId);
    personInfo.setMobi(userMobi);
    try {
      PersonInfoExecution personInfoExecution = personInfoService.modifyPersonInfo(personInfo);
      if(personInfoExecution.getState()== PersonInfoEnum.SUCCESS.getState()){
        return true;
      }else{
        throw new PersonInfoOperationException("更新账户膜币失败"+personInfoExecution.getStateInfo());
      }
    }catch (Exception e){
      throw new PersonInfoOperationException("更新账户膜币失败"+e.getMessage());
    }


  }}
