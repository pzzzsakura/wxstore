package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.DzpResultExecution;
import com.irecssa.mmns.entity.DzpResult;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/06 00:22
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface DzpResultService {

  DzpResultExecution insertDzpResult(DzpResult dzpResult);

  DzpResultExecution queryDzpResult(String drId);

}
