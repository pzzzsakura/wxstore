package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.QmmResultExecution;
import com.irecssa.mmns.entity.QmmResult;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/05 23:24
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface QmmResultService {

  QmmResultExecution insertQmmResult(QmmResult qmmResult);

  QmmResultExecution queryQmmResult(String qrId);

}
