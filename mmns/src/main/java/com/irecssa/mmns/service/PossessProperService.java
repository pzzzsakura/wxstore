package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.PossessProperExecution;
import com.irecssa.mmns.entity.PossessProper;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 23:56
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface PossessProperService {

  PossessProperExecution insertPossessProper(PossessProper possessProper);

  PossessProperExecution queryPossessProperList(PossessProper possessProper);

  PossessProperExecution updatePossessProper(PossessProper possessProper);
}
