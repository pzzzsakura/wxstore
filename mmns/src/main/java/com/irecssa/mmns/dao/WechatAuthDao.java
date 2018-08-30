package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.WechatAuth;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Ma.li.ran 2017/11/20 0020 13:34
 */

public interface WechatAuthDao {

  /**
   * 插入微信信息
   * @param wechatAuth
   * @return
   */
  int insertWechatAuth(WechatAuth wechatAuth);

  /**
   * 通过openid查询用户信息
   * @param openId
   * @return
   */
  WechatAuth queryWechatAuthByOpenId(@Param("openId")String openId);

  /**
   * 查询当前用户数量
   * @return
   */
  int queryWechatAuthCount();

  List<WechatAuth> queryWechatAuthListOrderByGameIntegral();
}
