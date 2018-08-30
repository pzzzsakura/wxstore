package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.ProductCategoryDao;
import com.irecssa.mmns.dto.execution.ProductCategoryExecution;
import com.irecssa.mmns.entity.ProductCategory;
import com.irecssa.mmns.enums.ProductCategoryEnum;
import com.irecssa.mmns.exceptions.ProductCategoryOperationException;
import com.irecssa.mmns.service.ProductCategoryService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 17:49
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

  @Autowired
  private ProductCategoryDao productCategoryDao;

  @Transactional
  public ProductCategoryExecution addProductCategory(ProductCategory productCategory) {
    if (productCategory != null && productCategory.getProcateName() != null) {
      ProductCategory productCategoryCondition = new ProductCategory();
      productCategoryCondition.setProcateName(productCategory.getProcateName());
      List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(productCategoryCondition);
      if (productCategoryList != null && productCategoryList.size() > 0) {
        return new ProductCategoryExecution(ProductCategoryEnum.NAMEISEXIST);
      }
      productCategory.setCreateTime(new Date());
      productCategory.setEnableStatus(1);
      productCategory.setLastEditTime(new Date());
      productCategory.setProcateId(UUIDUtil.createUUID());
      productCategory.setProcateNum(UUIDUtil.createNum());
      int result;
      try {
        result = productCategoryDao.insertProductCategory(productCategory);
        if (result > 0) {
          return new ProductCategoryExecution(ProductCategoryEnum.SUCCESS, productCategory);
        } else {
          throw new ProductCategoryOperationException("添加商品种类出错");
        }
      } catch (Exception e) {
        throw new ProductCategoryOperationException("添加商品种类出错" + e.getMessage());
      }
    } else {
      return new ProductCategoryExecution(ProductCategoryEnum.NULL_AUTH_INFO);
    }
  }

  /**
   * 查询所有类别
   */
  public List<Map<String, Object>> getAllProductCategoryList(ProductCategory productCategoryCondition) {
    List<ProductCategory> productParentCategoryList = productCategoryDao.queryProductCategoryList(productCategoryCondition);
    if(productParentCategoryList==null){
      return null;
    }else{
      return getList(productParentCategoryList);
    }
  }

  /**
   * 按条件查询类别
   */
  public ProductCategoryExecution getProductCategoryList(ProductCategory productCategoryCondition) {
    List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(null);
    if (productCategoryList != null && productCategoryList.size() > 0) {
      return new ProductCategoryExecution(ProductCategoryEnum.SUCCESS, productCategoryList);
    } else {
      return new ProductCategoryExecution(ProductCategoryEnum.NULLOFRESULT);
    }
  }

  /**
   * 递归取出所有类别
   */
  public List<Map<String, Object>> getList(List<ProductCategory> productParentCategoryList) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      for (ProductCategory productCategory : productParentCategoryList) {
        ProductCategory productChildCategoryCondition = new ProductCategory();
        productChildCategoryCondition.setParent(productCategory);
        List<ProductCategory> productChildCategoryList = productCategoryDao.queryProductCategoryList(productChildCategoryCondition);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productParentCategory", productCategory);
        map.put("productChildCategory", productChildCategoryList);
        if(productChildCategoryList==null)continue;
        getList(productChildCategoryList);
        list.add(map);
      }

    return list;
  }
}
