package com.irecssa.mmns.util.wechat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irecssa.mmns.dto.UserAccessToken;
import com.irecssa.mmns.dto.WechatUser;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.entity.WechatAuth;
import com.irecssa.mmns.util.UUIDUtil;
import com.irecssa.mmns.util.wechat.ConfigUtil.SignType;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 微信工具类
 */
public class WechatUtil {

  private static Logger log = LoggerFactory.getLogger(WechatUtil.class);

  /**
   * 获取UserAccessToken实体类
   */
  public static UserAccessToken getUserAccessToken(String code) throws IOException {
    // 测试号信息里的appId
    String appId = ConfigUtil.APPID;
    log.debug("appId:" + appId);
    // 测试号信息里的appsecret
    String appsecret = ConfigUtil.APP_SECRECT;
    log.debug("secret:" + appsecret);
    // 根据传入的code,拼接出访问微信定义好的接口的URL
    String url =
        "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret
            + "&code=" + code + "&grant_type=authorization_code";
    // 向相应URL发送请求获取token json字符串
    String tokenStr = httpsRequest(url, "GET", null);
    log.debug("userAccessToken:" + tokenStr);
    UserAccessToken token = new UserAccessToken();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // 将json字符串转换成相应对象
      token = objectMapper.readValue(tokenStr, UserAccessToken.class);
    } catch (JsonParseException e) {
      log.error("获取用户accessToken失败: " + e.getMessage());
      e.printStackTrace();
    } catch (JsonMappingException e) {
      log.error("获取用户accessToken失败: " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      log.error("获取用户accessToken失败: " + e.getMessage());
      e.printStackTrace();
    }
    if (token == null) {
      log.error("获取用户accessToken失败。");
      return null;
    }
    return token;
  }

  /**
   * 获取WechatUser实体类
   */
  public static WechatUser getUserInfo(String accessToken, String openId) {
    // 根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
    String url =
        "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
            + "&lang=zh_CN";
    // 访问该URL获取用户信息json 字符串
    String userStr = httpsRequest(url, "GET", null);
    log.debug("user info :" + userStr);
    WechatUser user = new WechatUser();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // 将json字符串转换成相应对象
      user = objectMapper.readValue(userStr, WechatUser.class);
    } catch (JsonParseException e) {
      log.error("获取用户信息失败: " + e.getMessage());
      e.printStackTrace();
    } catch (JsonMappingException e) {
      log.error("获取用户信息失败: " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      log.error("获取用户信息失败: " + e.getMessage());
      e.printStackTrace();
    }
    if (user == null) {
      log.error("获取用户信息失败。");
      return null;
    }
    return user;
  }

  /**
   * 将WechatUser里的信息转换成PersonInfo的信息并返回PersonInfo实体类
   */
  public static WechatAuth wechatAuthFromRequest(PersonInfo personInfo,WechatUser user,int count) {

    WechatAuth wechatAuth = new WechatAuth();
    wechatAuth.setCreateTime(new Date());
    wechatAuth.setOpenId(user.getOpenId());
    wechatAuth.setWechatHeadimg(user.getHeadimgurl());
    wechatAuth.setWechatName(user.getNickName());
    wechatAuth.setAddress(user.getCountry()+"区"+user.getProvince()+"省"+user.getCity()+"市");
    wechatAuth.setWechatSex(user.getSex() + "");
    wechatAuth.setWechatId(UUIDUtil.createUUID());
    wechatAuth.setPersonInfo(personInfo);
    wechatAuth.setNum(count);

    return wechatAuth;
  }

  /**
   * 初始化personInfo
   * @return
   */
  public static PersonInfo personInfoFromRequest(){
    PersonInfo personInfo = new PersonInfo();
    personInfo.setMobi(0);
    personInfo.setIage(1);
    personInfo.setPersonInfoId(UUIDUtil.createUUID());
    personInfo.setGameIntegral(0);
    personInfo.setShopIntegral(0);
    //personInfo.setPower(3);//初始化体力
    return personInfo;
  }

  /**
   * 发起https请求并获取结果
   *
   * @param requestUrl 请求地址
   * @param requestMethod 请求方式（GET、POST）
   * @param outputStr 提交的数据
   * @return json字符串
   */
  public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
    StringBuffer buffer = new StringBuffer();
    try {
      // 创建SSLContext对象，并使用我们指定的信任管理器初始化
      TrustManager[] tm = {new MyX509TrustManager()};
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new java.security.SecureRandom());
      // 从上述SSLContext对象中得到SSLSocketFactory对象
      SSLSocketFactory ssf = sslContext.getSocketFactory();

      URL url = new URL(requestUrl);
      HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
      httpUrlConn.setSSLSocketFactory(ssf);

      httpUrlConn.setDoOutput(true);
      httpUrlConn.setDoInput(true);
      httpUrlConn.setUseCaches(false);
      // 设置请求方式（GET/POST）
      httpUrlConn.setRequestMethod(requestMethod);

      if ("GET".equalsIgnoreCase(requestMethod)) {
        httpUrlConn.connect();
      }

      // 当有数据需要提交时
      if (null != outputStr) {
        OutputStream outputStream = httpUrlConn.getOutputStream();
        // 注意编码格式，防止中文乱码
        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }

      // 将返回的输入流转换成字符串
      InputStream inputStream = httpUrlConn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      bufferedReader.close();
      inputStreamReader.close();
      // 释放资源
      inputStream.close();
      inputStream = null;
      httpUrlConn.disconnect();
      log.debug("https buffer:" + buffer.toString());
    } catch (ConnectException ce) {
      log.error("Weixin server connection timed out.");
    } catch (Exception e) {
      log.error("https request error:{}", e);
    }
    return buffer.toString();
  }

  /**
   * XML格式字符串转换为Map
   *
   * @param strXML XML字符串
   * @return XML数据转换后的Map
   * @throws Exception
   */
  public static Map<String, String> xmlToMap(String strXML) throws Exception {
    try {
      Map<String, String> data = new HashMap<String, String>();
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
      org.w3c.dom.Document doc = documentBuilder.parse(stream);
      doc.getDocumentElement().normalize();
      NodeList nodeList = doc.getDocumentElement().getChildNodes();
      for (int idx = 0; idx < nodeList.getLength(); ++idx) {
        Node node = nodeList.item(idx);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          org.w3c.dom.Element element = (org.w3c.dom.Element) node;
          data.put(element.getNodeName(), element.getTextContent());
        }
      }
      try {
        stream.close();
      } catch (Exception ex) {
        // do nothing
      }
      return data;
    } catch (Exception ex) {
      WechatUtil.getLogger().warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
      throw ex;
    }

  }

  /**
   * 将Map转换为XML格式的字符串
   *
   * @param data Map类型数据
   * @return XML格式的字符串
   * @throws Exception
   */
  public static String mapToXml(Map<String, String> data) throws Exception {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
    org.w3c.dom.Document document = documentBuilder.newDocument();
    org.w3c.dom.Element root = document.createElement("xml");
    document.appendChild(root);
    for (String key: data.keySet()) {
      String value = data.get(key);
      if (value == null) {
        value = "";
      }
      value = value.trim();
      org.w3c.dom.Element filed = document.createElement(key);
      filed.appendChild(document.createTextNode(value));
      root.appendChild(filed);
    }
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    DOMSource source = new DOMSource(document);
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    StringWriter writer = new StringWriter();
    StreamResult result = new StreamResult(writer);
    transformer.transform(source, result);
    String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
    try {
      writer.close();
    }
    catch (Exception ex) {
    }
    return output;
  }


  /**
   * 生成带有 sign 的 XML 格式字符串
   *
   * @param data Map类型数据
   * @param key API密钥
   * @return 含有sign字段的XML
   */
  public static String generateSignedXml(final Map<String, String> data, String key) throws Exception {
    return generateSignedXml(data, key, SignType.MD5);
  }

  /**
   * 生成带有 sign 的 XML 格式字符串
   *
   * @param data Map类型数据
   * @param key API密钥
   * @param signType 签名类型
   * @return 含有sign字段的XML
   */
  public static String generateSignedXml(final Map<String, String> data, String key, SignType signType) throws Exception {
    String sign = generateSignature(data, key, signType);
    data.put(ConfigUtil.FIELD_SIGN, sign);
    return mapToXml(data);
  }


  /**
   * 判断签名是否正确
   *
   * @param xmlStr XML格式数据
   * @param key API密钥
   * @return 签名是否正确
   * @throws Exception
   */
  public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
    Map<String, String> data = xmlToMap(xmlStr);
    if (!data.containsKey(ConfigUtil.FIELD_SIGN) ) {
      return false;
    }
    String sign = data.get(ConfigUtil.FIELD_SIGN);
    return generateSignature(data, key).equals(sign);
  }

  /**
   * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
   *
   * @param data Map类型数据
   * @param key API密钥
   * @return 签名是否正确
   * @throws Exception
   */
  public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
    return isSignatureValid(data, key, SignType.MD5);
  }

  /**
   * 判断签名是否正确，必须包含sign字段，否则返回false。
   *
   * @param data Map类型数据
   * @param key API密钥
   * @param signType 签名方式
   * @return 签名是否正确
   * @throws Exception
   */
  public static boolean isSignatureValid(Map<String, String> data, String key, SignType signType) throws Exception {
    if (!data.containsKey(ConfigUtil.FIELD_SIGN) ) {
      return false;
    }
    String sign = data.get(ConfigUtil.FIELD_SIGN);
    return generateSignature(data, key, signType).equals(sign);
  }

  /**
   * 生成签名
   *
   * @param data 待签名数据
   * @param key API密钥
   * @return 签名
   */
  public static String generateSignature(final Map<String, String> data, String key) throws Exception {
    return generateSignature(data, key, SignType.MD5);
  }

  /**
   * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
   *
   * @param data 待签名数据
   * @param key API密钥
   * @param signType 签名方式
   * @return 签名
   */
  public static String generateSignature(final Map<String, String> data, String key, SignType signType) throws Exception {
    Set<String> keySet = data.keySet();
    String[] keyArray = keySet.toArray(new String[keySet.size()]);
    Arrays.sort(keyArray);
    StringBuilder sb = new StringBuilder();
    for (String k : keyArray) {
      if (k.equals(ConfigUtil.FIELD_SIGN)) {
        continue;
      }
      if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
        sb.append(k).append("=").append(data.get(k).trim()).append("&");
    }
    sb.append("key=").append(key);
    if (SignType.MD5.equals(signType)) {
      return MD5(sb.toString()).toUpperCase();
    }
	       /* else if (SignType.HMACSHA256.equals(signType)) {
	            return HMACSHA256(sb.toString(), key);
	        }*/
    else {
      throw new Exception(String.format("Invalid sign_type: %s", signType));
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
   * 生成 MD5
   *
   * @param data 待处理数据
   * @return MD5结果
   */
  public static String MD5(String data) throws Exception {
    java.security.MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] array = md.digest(data.getBytes("UTF-8"));
    StringBuilder sb = new StringBuilder();
    for (byte item : array) {
      sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
    }
    return sb.toString().toUpperCase();
  }

  /**
   * 生成 HMACSHA256
   * @param data 待处理数据
   * @param key 密钥
   * @return 加密结果
   * @throws Exception
   */
  public static String HMACSHA256(String data, String key) throws Exception {
    Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
    sha256_HMAC.init(secret_key);
    byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
    StringBuilder sb = new StringBuilder();
    for (byte item : array) {
      sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
    }
    return sb.toString().toUpperCase();
  }

  /**
   * 日志
   * @return
   */
  public static Logger getLogger() {
    Logger logger = LoggerFactory.getLogger("wxpay java sdk");
    return logger;
  }

  /**
   * 获取当前时间戳，单位秒
   * @return
   */
  public static long getCurrentTimestamp() {
    return System.currentTimeMillis()/1000;
  }

  /**
   * 获取当前时间戳，单位毫秒
   * @return
   */
  public static long getCurrentTimestampMs() {
    return System.currentTimeMillis();
  }

  /**
   * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
   * @return
   */
  public static String generateUUID() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
  }

}