package com.irecssa.mmns.controller.mall;

import com.irecssa.mmns.dto.execution.ProductCategoryExecution;
import com.irecssa.mmns.entity.ProductCategory;
import com.irecssa.mmns.enums.ProductCategoryEnum;
import com.irecssa.mmns.service.ProductCategoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 18:01
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class ProductCategoryController {

  @Autowired
  private ProductCategoryService productCategoryService;

  /**
   * 按条件查询商品种类
   */
  @RequestMapping(value = "getproductcategory", method = RequestMethod.POST)
  private Map<String, Object> getProductCategory(@RequestBody ProductCategory productCategoryCondition) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    ProductCategoryExecution productCategoryExecution = productCategoryService.getProductCategoryList(productCategoryCondition);
    if (productCategoryExecution.getState() == ProductCategoryEnum.SUCCESS.getState()
        && productCategoryExecution.getProductCategoryList() != null
        && productCategoryExecution.getProductCategoryList().size() > 0) {
      modelMap.put("success", true);
      modelMap.put("productCategoryList", productCategoryExecution.getProductCategoryList());
      return modelMap;
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", productCategoryExecution.getStateInfo());
      return modelMap;
    }
  }
/**
 * 查询商品所有种类，包括一级和二级类别
 */
@RequestMapping(value = "getallproductcategorylist",method = RequestMethod.POST)
private Map<String,Object> getAllProductCategoryList(){
  Map<String, Object> modelMap = new HashMap<String, Object>();
  List<Map<String,Object>> productCategoryList = productCategoryService.getAllProductCategoryList(null);
  modelMap.put("success",true);
  modelMap.put("productCategoryList",productCategoryList);
  return modelMap;
}

  /**
   * 添加商品种类
   */
  @RequestMapping(value = "addproductcategory", method = RequestMethod.POST)
  private Map<String, Object> addProductCategory(@RequestBody ProductCategory productCategory) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    ProductCategoryExecution productCategoryExecution;
    if (productCategory != null) {
      try {
        productCategoryExecution = productCategoryService.addProductCategory(productCategory);
        if (productCategoryExecution.getState() == ProductCategoryEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
          modelMap.put("productCategory", productCategoryExecution.getProductCategory());
          return modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", productCategoryExecution.getStateInfo());
          return modelMap;
        }
      } catch (Exception e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", ProductCategoryEnum.NULL_AUTH_INFO.getStateInfo());
      return modelMap;
    }
  }
}
