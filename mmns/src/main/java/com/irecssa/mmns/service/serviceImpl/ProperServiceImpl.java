package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.ProperDao;
import com.irecssa.mmns.dto.execution.ProperExecution;
import com.irecssa.mmns.entity.Proper;
import com.irecssa.mmns.enums.ProperEnum;
import com.irecssa.mmns.exceptions.ProperException;
import com.irecssa.mmns.service.ProperService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 22:57
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class ProperServiceImpl implements ProperService {

  @Autowired
  private ProperDao properDao;

  @Transactional
  public ProperExecution insertProper(Proper proper) {
    if(proper!=null){
      proper.setCreateTime(new Date());
      proper.setLastEditTime(new Date());
      proper.setProperId(UUIDUtil.createUUID());
      try {
        int result = properDao.insertProper(proper);
        if(result>0){
          return new ProperExecution(ProperEnum.SUCCESS,proper);
        }else{
          throw new ProperException("insertProper error");
        }
      }catch (Exception e){
        throw new ProperException(e.getMessage());
      }
    }else{
      return new ProperExecution(ProperEnum.NULL_AUTH_INFO);
    }
  }

  public ProperExecution queryProperList() {
    return new ProperExecution(ProperEnum.SUCCESS,properDao.queryProperList());
  }

  public ProperExecution queryProperById(String properId) {
    if(properId!=null&&properId!=""){
      Proper properExecution = properDao.queryProperById(properId);
      return new ProperExecution(ProperEnum.SUCCESS,properExecution);

    }else{
      return new ProperExecution(ProperEnum.NULL_AUTH_INFO);
    }
  }
}
