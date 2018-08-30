package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.QmmResult;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/05 23:17
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface QmmResultDao {

  int insertQmmResult(QmmResult qmmResult);

  QmmResult queryQmmResult(String qrId);
}
