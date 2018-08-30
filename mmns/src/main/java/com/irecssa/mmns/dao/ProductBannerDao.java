package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.ProductBanner;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 00:13
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductBannerDao {

  int batchInsertProductBanner(@Param("productBannerList") List<ProductBanner> productBannerList);

  List<ProductBanner> queryProductBannerList(@Param("productId") String productId);

  int deleteProductBannerByProductId(@Param("productId") String productId);
}
