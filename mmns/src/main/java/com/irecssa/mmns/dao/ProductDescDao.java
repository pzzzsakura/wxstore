package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.ProductDesc;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/22 16:58
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductDescDao {

  int updateProductDesc(ProductDesc productDesc);
  int insertProductDesc(ProductDesc productDesc);

  ProductDesc queryProductDescById(String prodescId);
}
