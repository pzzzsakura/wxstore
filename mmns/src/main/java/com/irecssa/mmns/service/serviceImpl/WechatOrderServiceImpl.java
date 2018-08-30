package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.WechatOrderDao;
import com.irecssa.mmns.dto.execution.WechatOrderExecution;
import com.irecssa.mmns.entity.WechatOrder;
import com.irecssa.mmns.enums.WechatOrderEnum;
import com.irecssa.mmns.exceptions.WechatOrderException;
import com.irecssa.mmns.service.WechatOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 11:26
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class WechatOrderServiceImpl implements WechatOrderService{

  @Autowired
  private WechatOrderDao wechatOrderDao;

  /**
   * 形成订单
   * @param wechatOrder
   * @return
   */
  @Transactional
  public WechatOrderExecution addWechatOrder(WechatOrder wechatOrder) {
    try {
      int result = wechatOrderDao.insertWechatOrder(wechatOrder);
      if(result>0){
        return new WechatOrderExecution(WechatOrderEnum.SUCCESS,wechatOrder);
      }else{
        throw new WechatOrderException("insertWechatOrder error");

      }
    }catch (Exception e){
      throw new WechatOrderException("insertWechatOrder"+e.getMessage());
    }
  }

  /**
   * 更新订单状态
   * @param wechatOrder
   * @return
   */
  @Transactional
  public WechatOrderExecution modifyWechatOrder(WechatOrder wechatOrder) {
    try {
      int result = wechatOrderDao.updateWechatOrder(wechatOrder);
      if(result>0){

        return new WechatOrderExecution(WechatOrderEnum.SUCCESS,wechatOrder);
      }else{
        throw new WechatOrderException("modifyWechatOrder error");

      }
    }catch (Exception e){
      throw new WechatOrderException("modifyWechatOrder"+e.getMessage());
    }
  }
}
