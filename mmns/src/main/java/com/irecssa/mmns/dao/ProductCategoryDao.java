package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.ProductCategory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 17:37
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductCategoryDao {

  /**
   * 插入商品种类
   * @param productCategory
   * @return
   */
  int insertProductCategory(ProductCategory productCategory);

  /**
   * 查询商品种类列表
   * @return
   */
  List<ProductCategory> queryProductCategoryList(@Param("productCategoryCondition") ProductCategory productCategoryCondition);
}
