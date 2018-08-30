package com.irecssa.mmns.controller.mall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irecssa.mmns.dto.execution.PropertyValueExecution;
import com.irecssa.mmns.entity.PropertyValue;
import com.irecssa.mmns.enums.PropertyValueEnum;
import com.irecssa.mmns.service.PropertyValueService;
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
 * @datetime: 2017/11/30 13:19
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class PropertyValueController {

  @Autowired
  private PropertyValueService propertyValueService;

  /**
   * 批量添加属性值
   * @param request
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "addpropertyvalue",method = RequestMethod.POST)
  private Map<String,Object> addPropertyValue(HttpServletRequest request) throws IOException {
    Map<String,Object> modelMap = new HashMap<String,Object>();
    String propertyValueStr = HttpServletRequestUtil.getString(request,"propertyValueStr");
    if(propertyValueStr!=null){
      ObjectMapper objectMapper = new ObjectMapper();
      //得到参数种类和值的组合   【{property的id，value值}。。。】//按查询时的id顺序来组合
      List<PropertyValue> propertyValueList = objectMapper.readValue(propertyValueStr, JacksonUtil.getCollectionType(objectMapper,List.class,PropertyValue.class));
      PropertyValueExecution propertyValueExecution = propertyValueService.addPropertyValueList(propertyValueList);
      if(propertyValueExecution.getState()== PropertyValueEnum.SUCCESS.getState()){
        modelMap.put("success",true);
        modelMap.put("propertyValueList",propertyValueExecution.getPropertyValueList());
        return modelMap;

      }else{
        modelMap.put("success",false);
        modelMap.put("errMsg",propertyValueExecution.getStateInfo());
        return modelMap;

      }
    }else{
      modelMap.put("success",false);
      modelMap.put("errMsg","参数为空");
      return modelMap;
    }

  }

  /**
   * 得到属性值列表
   * @param request
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "getpropertyvalueList",method = RequestMethod.POST)
  private Map<String,Object> getPropertyValueList(@RequestParam("propertyId")String propertyId, HttpServletRequest request) throws IOException {
    Map<String,Object> modelMap = new HashMap<String,Object>();
    modelMap.put("success",true);
    modelMap.put("propertyValueList",propertyValueService.getPropertyValueList(propertyId));
    return modelMap;
  }
}
