package com.irecssa.mmns.util.wechat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 10:11
 * @desc: 微信支付, 得到预支付ID
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WechatPayUtil {


  public static String unifiedorder(String spbill_create_ip, String total_fee, String body,
      String out_trade_no, String openid) throws Exception {
    SortedMap<String, String> parameters = new TreeMap<String, String>();
    parameters.put("appid", ConfigUtil.APPID);
    parameters.put("mch_id", ConfigUtil.MCH_ID);
    parameters.put("nonce_str", WechatUtil.generateNonceStr());
    parameters.put("body", body);
    parameters.put("out_trade_no", out_trade_no);
    parameters.put("total_fee", total_fee);
    parameters.put("spbill_create_ip", spbill_create_ip);
    parameters.put("notify_url", ConfigUtil.NOTIFY_URL);
    parameters.put("trade_type", "JSAPI");
    parameters.put("openid", openid);
    String sign = WechatUtil.generateSignature(parameters, ConfigUtil.API_KEY);
    parameters.put("sign", sign);
    String requestXML = WechatUtil.mapToXml(parameters);
    System.out.println(requestXML.toString());
    String result = WechatUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
    System.out.println(result.toString());
    Map<String, String> map = new HashMap<String, String>();
    try {
      map = WechatUtil.xmlToMap(result);
    } catch (JDOMException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }//解析微信返回的信息，以Map形式存储便于取值
    return map.get("prepay_id").toString();
  }
}
