package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.QmmResultDao;
import com.irecssa.mmns.dto.execution.QmmResultExecution;
import com.irecssa.mmns.entity.QmmResult;
import com.irecssa.mmns.enums.QmmResultEnum;
import com.irecssa.mmns.exceptions.QmmResultException;
import com.irecssa.mmns.service.QmmResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/05 23:25
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class QmmResultServiceImpl implements QmmResultService {

  @Autowired
  private QmmResultDao qmmResultDao;

  public QmmResultExecution insertQmmResult(QmmResult qmmResult) {
    if (qmmResult != null) {
      try {
        int result = qmmResultDao.insertQmmResult(qmmResult);
        if (result > 0) {
          return new QmmResultExecution(QmmResultEnum.SUCCESS, qmmResult);
        } else {
          throw new QmmResultException("insertQmmResult error");
        }
      } catch (Exception e) {
        throw new QmmResultException(e.getMessage());
      }
    } else {
      return new QmmResultExecution(QmmResultEnum.NULL_AUTH_INFO);
    }
  }

  public QmmResultExecution queryQmmResult(String qrId) {
    if(qrId!=null){
      return new QmmResultExecution(QmmResultEnum.SUCCESS,qmmResultDao.queryQmmResult(qrId));
    }else{
      return new QmmResultExecution(QmmResultEnum.NULL_AUTH_INFO);
    }
  }
}
