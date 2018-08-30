package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.WechatOrder;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 11:15
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WechatOrderDao {

  int insertWechatOrder(WechatOrder wechatOrder);

  int updateWechatOrder(WechatOrder wechatOrder);

}
