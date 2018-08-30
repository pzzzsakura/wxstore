package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.ProductRepertory;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/22 17:08
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductRepertoryDao {

  //初始化库存
  int insertProductRepertory(ProductRepertory productRepertory);

  //更新库存
  int updateProductRepertory(ProductRepertory productRepertory);

  //查询库存
  ProductRepertory queryProductRepertory(String prorepId);

}
