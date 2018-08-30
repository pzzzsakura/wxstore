package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.SPNum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 09:36
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface SPNumDao {

  int insertSPNum(SPNum spNum);

  List<SPNum> querySPNumList(SPNum spNum);

  SPNum querySPNum(String spNumId);

  int updateSPNum(SPNum spNum);

  SPNum querySPNumIsExist(SPNum spNum);
  int batchDeleteSPNum(List<String> spNumId);
}
