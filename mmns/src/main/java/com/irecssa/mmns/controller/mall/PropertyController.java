package com.irecssa.mmns.controller.mall;

import com.irecssa.mmns.dto.execution.PropertyExecution;
import com.irecssa.mmns.entity.Property;
import com.irecssa.mmns.enums.PropertyEnum;
import com.irecssa.mmns.service.PropertyService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 17:55
 * @desc: 参数种类
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class PropertyController {

  @Autowired
  private PropertyService propertyService;

  @RequestMapping(value = "addproperty",method = RequestMethod.POST)
  private Map<String,Object> addProperty(@RequestParam("propertyName") String propertyName){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    Property property = new Property();
    property.setPropertyName(propertyName);
    PropertyExecution propertyExecution = propertyService.addProperty(property);
    if(propertyExecution.getState()== PropertyEnum.SUCCESS.getState()){
      modelMap.put("success",true);
      modelMap.put("property",propertyExecution.getProperty());
      return modelMap;
    }else{
      modelMap.put("success",false);
      modelMap.put("errMsg",propertyExecution.getStateInfo());
      return modelMap;
    }
  }

  @RequestMapping(value = "getproperty",method = RequestMethod.POST)
  private Map<String,Object> getProperty(){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    PropertyExecution propertyExecution = propertyService.getPropertyList();
    if(propertyExecution.getState()==PropertyEnum.SUCCESS.getState()){
      modelMap.put("success",true);
      modelMap.put("propertyList",propertyExecution.getPropertyList());
      return modelMap;
    }else{
      modelMap.put("success",false);
      modelMap.put("errMsg",propertyExecution.getStateInfo());
      return modelMap;
    }

  }
}
