package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.MainBannerCategoryDao;
import com.irecssa.mmns.dto.execution.MainBannerCategoryExecution;
import com.irecssa.mmns.entity.MainBannerCategory;
import com.irecssa.mmns.enums.MainBannerCategoryEnum;
import com.irecssa.mmns.exceptions.MainBannerCategoryOperationException;
import com.irecssa.mmns.service.MainBannerCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 13:20
 * @desc: 上传轮播图
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class MainBannerCategoryServiceImpl implements MainBannerCategoryService {

  @Autowired
  private MainBannerCategoryDao mainBannerCategoryDao;

  /**
   * 增加轮播图类名
   * @param mainBannerCategory
   * @return
   */
  @Transactional
  public MainBannerCategoryExecution insertMainBannerCategory(
      MainBannerCategory mainBannerCategory) {
    if (mainBannerCategory != null && mainBannerCategory.getBannercateName() != null) {
      List<MainBannerCategory> mainBannerCategoryList = mainBannerCategoryDao
          .queryMainBannerCategoryList(mainBannerCategory);
      if (mainBannerCategoryList != null && mainBannerCategoryList.size() > 0) {
        return new MainBannerCategoryExecution(MainBannerCategoryEnum.ISEXIST);
      } else {
        int result;
        try {
          result = mainBannerCategoryDao.insertMainBannerCategory(mainBannerCategory);
          if (result > 0) {
            return new MainBannerCategoryExecution(MainBannerCategoryEnum.SUCCESS, mainBannerCategory);
          } else {
            throw new MainBannerCategoryOperationException("insertMainBannerCategory error");
          }
        } catch (Exception e) {
          throw new MainBannerCategoryOperationException(e.getMessage());
        }

      }
    } else {
      return new MainBannerCategoryExecution(MainBannerCategoryEnum.LOGINFAIL);
    }

  }

  /**
   * 轮播图类别列表
   * @param mainBannerCategory
   * @return
   */
  public MainBannerCategoryExecution getMainBannerCategoryList(MainBannerCategory mainBannerCategory) {
    List<MainBannerCategory> mainBannerCategoryList = mainBannerCategoryDao.queryMainBannerCategoryList(mainBannerCategory);
    if(mainBannerCategoryList!=null&&mainBannerCategoryList.size()>0){
      return new MainBannerCategoryExecution(MainBannerCategoryEnum.SUCCESS);
    }else{
      return new MainBannerCategoryExecution(MainBannerCategoryEnum.RESULTNULL);
    }
  }
}
