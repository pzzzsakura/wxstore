package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.FreeExecution;
import com.irecssa.mmns.entity.Free;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 17:23
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface FreeService {
  FreeExecution addFree(Free free);

  FreeExecution getFreeList();

  FreeExecution getFreeByFreeId(String freeId);
}
