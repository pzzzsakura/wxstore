package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.PersonInfoDao;
import com.irecssa.mmns.dto.execution.PersonInfoExecution;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.enums.PersonInfoEnum;
import com.irecssa.mmns.exceptions.PersonInfoOperationException;
import com.irecssa.mmns.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ma.li.ran 2017/11/20 0020 15:04
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

  @Autowired
  private PersonInfoDao personInfoDao;
  @Transactional
  public PersonInfoExecution insertPersonInfo(PersonInfo personInfo) {
    int result ;
    if(personInfo!=null){
      try{
        result = personInfoDao.insertPersonInfo(personInfo);
      }catch(Exception e){
        throw new PersonInfoOperationException(e.getMessage());
      }
    }else{
      return new PersonInfoExecution(PersonInfoEnum.NULL_AUTH_INFO);
    }
    return new PersonInfoExecution(PersonInfoEnum.SUCCESS);
  }

  @Transactional
  public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) {
    int result ;
    if(personInfo!=null){
      try{
        result = personInfoDao.updatePersonInfo(personInfo);
      }catch(Exception e){
        throw new PersonInfoOperationException(e.getMessage());
      }
    }else{
      return new PersonInfoExecution(PersonInfoEnum.NULL_AUTH_INFO);
    }
    return new PersonInfoExecution(PersonInfoEnum.SUCCESS,personInfo);
  }
}
