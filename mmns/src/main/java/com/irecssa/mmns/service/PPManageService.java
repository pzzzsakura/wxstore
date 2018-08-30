package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.ShoppingDto;
import com.irecssa.mmns.entity.PPManage;
import java.util.List;
import java.util.Map;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/26 10:01
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface PPManageService {

  //对比商品库存
  Map<String,Object> checkSPNum(List<ShoppingDto> shoppingDtoList);

  List<Map<String,Object>>  getPPManageList(String productId);

  PPManage queryPPManageByUnique( String ppUnique,String productId);
}
