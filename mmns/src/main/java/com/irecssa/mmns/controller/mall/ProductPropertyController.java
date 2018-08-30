package com.irecssa.mmns.controller.mall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irecssa.mmns.dto.execution.ProductPropertyExecution;
import com.irecssa.mmns.entity.PPManage;
import com.irecssa.mmns.entity.PropertyValue;
import com.irecssa.mmns.enums.ProductPropertyEnum;
import com.irecssa.mmns.service.PPManageService;
import com.irecssa.mmns.service.ProductPropertyService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import com.irecssa.mmns.util.JacksonUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 00:29
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class ProductPropertyController {

  @Autowired
  private ProductPropertyService productPropertyService;

  @Autowired
  private PPManageService ppManageService;
  /**
   * 产品参数
   * @param productId
   * @return
   */
  @RequestMapping(value = "getproductproperty", method = RequestMethod.POST)
  private Map<String, Object> getProductProperty(@RequestParam("productId") String productId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    try {
      List<Map<String, Object>> comboList = productPropertyService.getComboProductList(productId);
      List<Map<String, Object>> propertyList = productPropertyService
          .getProductPropertyByProductId(productId);
      modelMap.put("comboList", comboList);
      modelMap.put("propertyList", propertyList);//[[name1,id1,[[value1,id1][value2,id2]]]]
      modelMap.put("ppManageList",ppManageService.getPPManageList(productId));//[{"xx":[num,mobi,inte]}{}]
      modelMap.put("success", true);
      return modelMap;
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
      return modelMap;
    }
  }

  /**
   * 给商品增加参数
   * @param request
   * @return
   */
  @RequestMapping(value = "addproductpropertylist",method = RequestMethod.POST)
  private Map<String,Object> addProductPropertyList(HttpServletRequest request) throws IOException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String propertyValueStr = HttpServletRequestUtil.getString(request,"propertyValueStr");//propertyValueId,property的id,id
    String ppManageStr = HttpServletRequestUtil.getString(request,"ppManageStr");//这里是价格和数量
    ObjectMapper objectMapper = new ObjectMapper();
    List<PropertyValue> propertyValueList = objectMapper.readValue(propertyValueStr,
        JacksonUtil.getCollectionType(objectMapper,List.class,PropertyValue.class));
    PPManage ppManage = objectMapper.readValue(ppManageStr,PPManage.class);
    if(propertyValueList!=null&&propertyValueList.size()>0&&ppManage!=null){
      try{
        ProductPropertyExecution productPropertyExecution = productPropertyService.addProductPropertyList(propertyValueList,ppManage);
        if(productPropertyExecution.getState()== ProductPropertyEnum.SUCCESS.getState()){
          modelMap.put("success", true);
          //modelMap.put("productPropertyList",productPropertyList);
          return modelMap;
        }else{
          modelMap.put("success", false);
          modelMap.put("errMsg", productPropertyExecution.getStateInfo());
          return modelMap;
        }
      }catch(Exception e){
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        return modelMap;
      }
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg","参数为空");
      return modelMap;
    }
  }




}

