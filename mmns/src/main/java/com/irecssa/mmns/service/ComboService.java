package com.irecssa.mmns.service;

import java.util.Map;
/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 23:45
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ComboService {

  Map<String,Object> getComboById(String comboId);

}
