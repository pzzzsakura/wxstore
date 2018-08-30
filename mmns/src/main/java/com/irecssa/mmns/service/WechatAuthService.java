package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.WechatUser;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;

/**
 * Ma.li.ran 2017/11/20 0020 14:43
 */
public interface WechatAuthService {

   WechatAuthExecution insertWechatAuth(WechatUser user,int count);

   WechatAuthExecution getWechatAuthServiceByOpenId(String openId);

   int getWechatAuthCount();

   WechatAuthExecution getWechatAuthListOrderByGameIntegral();
}
