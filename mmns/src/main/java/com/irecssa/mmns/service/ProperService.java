package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.ProperExecution;
import com.irecssa.mmns.entity.Proper;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 22:56
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProperService {

  ProperExecution insertProper(Proper proper);

  ProperExecution queryProperList();

  ProperExecution queryProperById(String properId);
}
