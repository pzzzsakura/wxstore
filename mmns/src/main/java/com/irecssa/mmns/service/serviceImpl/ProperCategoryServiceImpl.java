package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.ProperCategoryDao;
import com.irecssa.mmns.dto.execution.ProperCategoryExecution;
import com.irecssa.mmns.entity.ProperCategory;
import com.irecssa.mmns.enums.ProperCategoryEnum;
import com.irecssa.mmns.exceptions.ProperCategoryException;
import com.irecssa.mmns.service.ProperCategoryService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 15:27
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class ProperCategoryServiceImpl implements ProperCategoryService {

  @Autowired
  private ProperCategoryDao properCategoryDao;

  /**
   * 添加道具类别
   * @param properCategory
   * @return
   */
  public ProperCategoryExecution insertProperCategory(ProperCategory properCategory) {
    if(properCategory!=null){
      properCategory.setCreateTime(new Date());
      properCategory.setLastEditTime(new Date());
      properCategory.setPropercateId(UUIDUtil.createUUID());
      properCategory.setPropercateOrder(UUIDUtil.createNum());
      try {
        int result = properCategoryDao.insertProperCategory(properCategory);
        if(result>0){
          return new ProperCategoryExecution(ProperCategoryEnum.SUCCESS,properCategory);
        }else{
          throw new ProperCategoryException("insertProperCategory error");
        }
      }catch (Exception e){
        throw new ProperCategoryException(e.getMessage());
      }

    }else{
      return new ProperCategoryExecution(ProperCategoryEnum.NULL_AUTH_INFO);

    }
  }

  /**
   *
   * 查询道具种类列表
   * @return
   */
  public ProperCategoryExecution queryProperCategoryList() {
    return new ProperCategoryExecution(ProperCategoryEnum.SUCCESS,properCategoryDao.queryProperCategoryList());
  }
}
