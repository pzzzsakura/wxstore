package com.irecssa.mmns.controller.mall;

import com.irecssa.mmns.service.ComboService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 23:35
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping(value = "/mall")
public class ComboController {


  @Autowired
  private ComboService comboService;

  @RequestMapping(value = "getcombobycomboid", method = RequestMethod.GET)
  private Map<String, Object> getComboById(@RequestParam("comboId") String comboId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    modelMap = comboService.getComboById(comboId);//[{}{}{}]
    modelMap.put("success", true);
    return modelMap;

  }

}
