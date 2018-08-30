package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.ProductPropertyExecution;
import com.irecssa.mmns.entity.PPManage;
import com.irecssa.mmns.entity.ProductProperty;
import com.irecssa.mmns.entity.PropertyValue;
import java.util.List;
import java.util.Map;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 15:10
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProductPropertyService {

  List<Map<String,Object>> getProductPropertyByProductId(String productId);

  List<Map<String,Object>> getComboProductList(String productId);
 // PPManageExecution queryProductPropertyByProductIdAndPropertyValueAndPropertyName(String productId,List<String> propertyValueList,List<String> propertyNameList);
  ProductPropertyExecution addProductPropertyList(List<PropertyValue> propertyValueList,PPManage ppManage);

  ProductPropertyExecution modifyProductProperty(ProductProperty productProperty);
}
