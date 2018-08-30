package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.ProductOrder;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 16:50
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductOrderDao {

  int insertProductOrder(ProductOrder productOrder);

  List<ProductOrder> queryProductOrderList(@RequestParam("productOrderCondition")ProductOrder productOrderCondition);

  int updateProductOrder(ProductOrder productOrder);

  ProductOrder queryProductOrder(ProductOrder productOrder);


}
