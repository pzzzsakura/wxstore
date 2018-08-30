package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.DzpResultDao;
import com.irecssa.mmns.dto.execution.DzpResultExecution;
import com.irecssa.mmns.entity.DzpResult;
import com.irecssa.mmns.enums.DzpResultEnum;
import com.irecssa.mmns.exceptions.DzpResultException;
import com.irecssa.mmns.service.DzpResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/06 00:22
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class DzpResultServiceImpl implements DzpResultService{

  @Autowired
  private DzpResultDao dzpResultDao;

  @Transactional
  public DzpResultExecution insertDzpResult(DzpResult dzpResult) {
    if (dzpResult != null) {
      try {
        int result = dzpResultDao.insertDzpResult(dzpResult);
        if (result > 0) {
          return new DzpResultExecution(DzpResultEnum.SUCCESS, dzpResult);
        } else {
          throw new DzpResultException("insertDzpResult error");
        }
      } catch (Exception e) {
        throw new DzpResultException(e.getMessage());
      }
    } else {
      return new DzpResultExecution(DzpResultEnum.NULL_AUTH_INFO);
    }
  }

  public DzpResultExecution queryDzpResult(String drId) {
    if(drId!=null){
      return new DzpResultExecution(DzpResultEnum.SUCCESS,dzpResultDao.queryDzpResult(drId));
    }else{
      return new DzpResultExecution(DzpResultEnum.NULL_AUTH_INFO);
    }
  }
}
