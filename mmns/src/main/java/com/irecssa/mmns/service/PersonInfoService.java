package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.PersonInfoExecution;
import com.irecssa.mmns.entity.PersonInfo;

/**
 * Ma.li.ran 2017/11/20 0020 14:56
 */
public interface PersonInfoService {

  PersonInfoExecution insertPersonInfo(PersonInfo personInfo);

  PersonInfoExecution modifyPersonInfo(PersonInfo personInfo);

}
