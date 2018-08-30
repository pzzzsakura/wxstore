package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.DzpResult;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/06 00:12
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface DzpResultDao {

  int insertDzpResult(DzpResult dzpResult);

  DzpResult queryDzpResult(String drId);
}
