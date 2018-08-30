package com.irecssa.mmns.controller.mall;

import com.irecssa.mmns.dto.execution.MainBannerCategoryExecution;
import com.irecssa.mmns.entity.MainBannerCategory;
import com.irecssa.mmns.enums.MainBannerCategoryEnum;
import com.irecssa.mmns.service.MainBannerCategoryService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 15:24
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("mall")
public class MainBannerCategoryController {

  @Autowired
  private MainBannerCategoryService mainBannerCategoryService;

  /**
   * 获得轮播图类别列表
   */
  @RequestMapping(value = "getmainbannercategory", method = RequestMethod.GET)
  private Map<String, Object> getMainBannerCategory() {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    MainBannerCategoryExecution mainBannerCategoryExecution = mainBannerCategoryService
        .getMainBannerCategoryList(null);
    if (mainBannerCategoryExecution.getState() == MainBannerCategoryEnum.SUCCESS.getState()) {
      modelMap.put("success", true);
      modelMap
          .put("mainBannerCategoryList", mainBannerCategoryExecution.getMainBannerCategoryList());
    } else {
      modelMap.put("success", true);
      modelMap.put("errMsg", mainBannerCategoryExecution.getStateInfo());
    }
    return modelMap;
  }


  /**
   * 添加轮播图分类
   */
  @RequestMapping(value = "addmainbannercategory", method = RequestMethod.POST)
  private Map<String, Object> addMainBannerCategory(
      @RequestParam("mainBannerCategoryName") String mainBannerCategoryName) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    MainBannerCategory mainBannerCategory = new MainBannerCategory();
    if (mainBannerCategoryName != null) {
      mainBannerCategory.setBannercateId(UUIDUtil.createUUID());
      mainBannerCategory.setBannercateName(mainBannerCategoryName);
      try {
        MainBannerCategoryExecution mainBannerCategoryExecution = mainBannerCategoryService
            .insertMainBannerCategory(mainBannerCategory);
        if (mainBannerCategoryExecution.getState() == MainBannerCategoryEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
          modelMap.put("mainBannerCategory", mainBannerCategoryExecution.getMainBannerCategory());
          return modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", mainBannerCategoryExecution.getStateInfo());
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
}
