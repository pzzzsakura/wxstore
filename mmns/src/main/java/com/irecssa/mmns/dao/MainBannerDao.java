package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.MainBanner;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 1029
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface MainBannerDao {

  /**
   * 插入轮播图
   * @param mainBanner
   * @return
   */
  int insertMainBanner(MainBanner mainBanner);

  /**
   * 查询轮播图列表
   * @return
   */
  List<MainBanner> queryMainBannerList();
}
