package com.irecssa.mmns.controller.game;

import com.irecssa.mmns.dto.execution.ProperCategoryExecution;
import com.irecssa.mmns.entity.ProperCategory;
import com.irecssa.mmns.enums.ProperCategoryEnum;
import com.irecssa.mmns.service.ProperCategoryService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 15:36
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/game")
public class ProperCategoryController {

  @Autowired
  private ProperCategoryService properCategoryService;

  /**
   * 添加道具种类
   * @param properCategoryName
   * @return
   */
  @RequestMapping(value = "addpropercategory",method = RequestMethod.POST)
  private Map<String, Object> addProperCategory(
      @RequestParam("properCategoryName") String properCategoryName) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    ProperCategory properCategory = new ProperCategory();
    if (properCategoryName != null && properCategoryName != "") {
      properCategory.setPropercateName(properCategoryName);
      try {
        ProperCategoryExecution properCategoryExecution = properCategoryService
            .insertProperCategory(properCategory);
        if (properCategoryExecution.getState() == ProperCategoryEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
          modelMap.put("errMsg", properCategoryExecution.getProperCategory());
          return modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", properCategoryExecution.getStateInfo());
          return modelMap;
        }
      } catch (Exception e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "参数为空");
      return modelMap;
    }
  }

  /**
   * 得到道具种类列表
   * @return
   */
  @RequestMapping(value = "getpropercategorylist",method = RequestMethod.POST)
  private Map<String, Object> getProperCategoryList() {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    ProperCategoryExecution properCategoryExecution = properCategoryService.queryProperCategoryList();
    modelMap.put("success", true);
    modelMap.put("properCategoryList",properCategoryExecution.getProperCategorieList());
    return modelMap;

  }
}
