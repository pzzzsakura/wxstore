package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.ProductCategoryExecution;
import com.irecssa.mmns.entity.ProductCategory;
import java.util.List;
import java.util.Map;
/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 17:48
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductCategoryService {

  ProductCategoryExecution addProductCategory(ProductCategory productCategory);

  ProductCategoryExecution getProductCategoryList(ProductCategory productCategoryCondition);

  public List<Map<String,Object>> getAllProductCategoryList(ProductCategory productCategoryCondition);
}
