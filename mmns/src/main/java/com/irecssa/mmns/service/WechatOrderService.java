package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.WechatOrderExecution;
import com.irecssa.mmns.entity.WechatOrder;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 11:25
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WechatOrderService {
  WechatOrderExecution addWechatOrder(WechatOrder wechatOrder);

  WechatOrderExecution modifyWechatOrder(WechatOrder wechatOrder);
}
