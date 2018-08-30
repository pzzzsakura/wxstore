package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.PossessProperDao;
import com.irecssa.mmns.dto.execution.PossessProperExecution;
import com.irecssa.mmns.entity.PossessProper;
import com.irecssa.mmns.enums.PossessProperEnum;
import com.irecssa.mmns.exceptions.PossessProperException;
import com.irecssa.mmns.service.PossessProperService;
import com.irecssa.mmns.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 23:58
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class PossessProperServiceImpl implements PossessProperService {

  @Autowired
  private PossessProperDao possessProperDao;

  @Transactional
  public PossessProperExecution insertPossessProper(PossessProper possessProper) {
    if(possessProper!=null){
      possessProper.setEnableStatus(1);
      possessProper.setPossessProperId(UUIDUtil.createUUID());
      try {
        int result = possessProperDao.insertPossessProper(possessProper);
        if(result>0){
          return new PossessProperExecution(PossessProperEnum.SUCCESS,possessProper);
        }else{
          throw new PossessProperException("insertPossessProper error");
        }
      }catch (Exception e){
        throw new PossessProperException(e.getMessage());
      }

    }else{
      return new PossessProperExecution(PossessProperEnum.NULL_AUTH_INFO);
    }
  }

  public PossessProperExecution queryPossessProperList(PossessProper possessProper) {
    return new PossessProperExecution(PossessProperEnum.SUCCESS,possessProperDao.queryPossessProperList(possessProper));
  }


  @Transactional
  public PossessProperExecution updatePossessProper(PossessProper possessProper) {
    try {
      int result = possessProperDao.updatePossessProper(possessProper);
      if(result>0){
        return new PossessProperExecution(PossessProperEnum.SUCCESS,possessProper);
      }else{
        throw new PossessProperException("updatePossessProper error");
      }
    }catch (Exception e){
      throw new PossessProperException(e.getMessage());
    }

  }
}
