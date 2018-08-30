package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.ProductExecution;
import com.irecssa.mmns.entity.Combo;
import com.irecssa.mmns.entity.Product;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/22 15:18
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductService {

  /**
   * 查询商品列表并分页
   */
  ProductExecution queryProductList(Product productCondition, int pageIndex, int pageSize,
      String priceNew);

  /**
   * 查询对应的商品总数
   */
  ProductExecution getProductCount(Product productCondition);

  /**
   * 插入商品
   */
  ProductExecution addProduct(Product product,ImageHolder imageHolder,List<ImageHolder> productImgList,List<ImageHolder> productBannerList,Combo Combo);

  /**
   * 查询当前商品信息和轮播详情图
   */
  ProductExecution getProductById(String productId);

  /**
   * 更新商品信息
   */
  ProductExecution modifyProduct(Product product,ImageHolder imageHolder,List<ImageHolder> productImgList,List<ImageHolder> productBannerList);


}
