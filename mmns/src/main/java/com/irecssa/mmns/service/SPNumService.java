package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.SPNumExecution;
import com.irecssa.mmns.entity.SPNum;
import java.util.Map;
import java.util.List;
/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 10:28
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface SPNumService {

  SPNumExecution addSPNum(SPNum spNum);

  List<Map<String, Object>> getSPNumList(SPNum spNum);

  SPNumExecution getSPNum(String spNumId);

  List<Map<String,Object>> getOrderProductList(SPNum spNum);

  SPNumExecution modifySPNum(SPNum spNum);

  SPNumExecution querySPNumIsExist(SPNum spNum);

  SPNumExecution removeSPNum(List<String> spNumIdList);

  SPNumExecution getOrderSPNumList(SPNum spNum);
}
