package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.FreeDao;
import com.irecssa.mmns.dto.execution.FreeExecution;
import com.irecssa.mmns.entity.Free;
import com.irecssa.mmns.enums.FreeEnum;
import com.irecssa.mmns.exceptions.FreeOperationException;
import com.irecssa.mmns.service.FreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 17:24
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class FreeServiceImpl implements FreeService {

  @Autowired
  private FreeDao freeDao;

  //输入赠品信息
  @Transactional
  public FreeExecution addFree(Free free) {
    if (free != null) {
      int result;
      try {
        result = freeDao.insertFree(free);
      } catch (Exception e) {
        throw new FreeOperationException("addFree" + e.getMessage());
      }
      if (result > 0) {
        return new FreeExecution(FreeEnum.SUCCESS);
      } else {
        throw new FreeOperationException("addFree error");
      }
      }else{
      return new FreeExecution(FreeEnum.NULL_AUTH_INFO);
    }
  }

  //返回赠品列表信息
  public FreeExecution getFreeList() {
    return new FreeExecution(FreeEnum.SUCCESS, freeDao.queryFreeList());
  }


  public FreeExecution getFreeByFreeId(String freeId) {
    if (freeId != null) {
      Free free = freeDao.queryFreeByFreeId(freeId);
      return new FreeExecution(FreeEnum.SUCCESS, free);
    } else {
      return new FreeExecution(FreeEnum.NULL_AUTH_INFO);
    }
  }
}
