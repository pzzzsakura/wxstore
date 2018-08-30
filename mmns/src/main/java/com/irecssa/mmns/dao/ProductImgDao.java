package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.ProductImg;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/22 23:14
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductImgDao {

  int batchInsertProductImg(@Param("productImgList") List<ProductImg> productImgList);

  List<ProductImg> queryProductImgList(@Param("productId") String productId);

  int deleteProductImgByProductId(@Param("productId") String productId);
}
