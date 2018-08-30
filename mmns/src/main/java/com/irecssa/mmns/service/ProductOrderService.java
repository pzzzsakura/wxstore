package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.ShoppingDto;
import com.irecssa.mmns.dto.execution.ProductOrderExecution;
import com.irecssa.mmns.entity.Address;
import com.irecssa.mmns.entity.ProductOrder;
import com.irecssa.mmns.entity.Track;
import java.util.List;
import java.util.Map;
/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 18:33
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductOrderService {

  Map<String, Object> addProductOrder(List<ShoppingDto> shoppingDtoList, int totalMobi,int totalIntegral,
      Address address, String personInfoId);
  ProductOrderExecution sureSend(Track track,String personInfoId,String proorderId);
  ProductOrderExecution getProductOrderList(ProductOrder productOrderCondition);
  ProductOrderExecution goPay(String personInfoId,String proorderId);
  ProductOrderExecution modifyProductOrder(ProductOrder productOrder);
  ProductOrderExecution salesReturn(String personInfoId,String proorderId);
  ProductOrderExecution salesReturning(String personInfoId,String proorderId);
  ProductOrderExecution sure(String personInfoId,String proorderId);
  ProductOrderExecution  getProductOrder(ProductOrder productOrder);
}
