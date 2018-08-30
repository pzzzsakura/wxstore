package com.irecssa.mmns.util.wechat;

public class ConfigUtil {

	public enum SignType {
		MD5,HMACSHA256
	}
	public final static String APPID = "";//服务号的appid
	public final static String APP_SECRECT = "";//服务号的appSecrect
	// 菜单创建接口（POST）
	 public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
//  public final static String ACCEPTERNAME = "";
//	public final static String DESC1 = "";
//	public final static String DESC2 = "";
//	public final static Integer RATIO = 10;//
//	public final static Integer EXPRESSFEE = 10;//
//	public final static Integer FREEEXPRESS = 99;//
	public final static String MCH_ID = "";//开通微信支付分配的商户号
	public final static String API_KEY = "";//商户API密钥 自行去商户平台设置
	public final static String SIGN_TYPE = "MD5";//签名加密方式
	public final static String FAIL = "FAIL";
	public static final String SUCCESS  = "SUCCESS";
	public static final String FIELD_SIGN = "sign";

	//微信支付统一接口的回调action

	public final static String NOTIFY_URL = "http://www.xx.com/mmns/wechat/paycallback"; //用于告知微信服务器 调用成功
	/**
	 * 微信支付接口地址
	 */
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//微信支付统一接口(POST)
	//微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	//关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	//退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	//短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	//接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";

}
