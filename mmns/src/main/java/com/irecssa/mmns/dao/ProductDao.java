package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.Product;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/22 09:57
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductDao{

  /**
   *查询商品列表并分页
   */
  List<Product> queryProductList(@Param("productCondition") Product productCondition,
      @Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize,
      @Param("priceNew") String price);
  /**
   * 查询对应的商品总数
   */
  int queryProductCount(@Param("productCondition") Product productCondition);
  /**
   *
   * 插入商品
   * @param product
   * @return
   */
  int insertProduct(Product product);
  /**
   * 查询当前商品信息和轮播图
   */
  Product queryProductById(String productId);
  /**
   * 更新商品信息
   *
   */
  int updateProduct(Product product);
  /**
   * 查询详情图
   */
  Product queryProductImgById(String productId);


}
