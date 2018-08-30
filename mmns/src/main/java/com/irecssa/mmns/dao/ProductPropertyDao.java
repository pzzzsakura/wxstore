package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.ProductProperty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 14:26
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductPropertyDao {

  int insertProductProperty(List<ProductProperty> productPropertyList);

  List<ProductProperty> queryProductPropertyByProductId(String productId);

  List<ProductProperty> queryProductPropertyByProductIdAndPPManageId(@Param("productId") String productId,@Param("ppManageId") String ppManageId);


}
