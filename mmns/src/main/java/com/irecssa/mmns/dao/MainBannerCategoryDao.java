package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.MainBannerCategory;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 1218
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface MainBannerCategoryDao {

  /**
   * 插入轮播图种类
   * @param mainBannerCategory
   * @return
   */
  int insertMainBannerCategory(MainBannerCategory mainBannerCategory);

  /**
   * 查询轮播图种类列表
   * @param mainBannerCategory
   * @return
   */
  List<MainBannerCategory> queryMainBannerCategoryList(MainBannerCategory mainBannerCategory);

}
