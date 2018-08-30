package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.Proper;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 16:04
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProperDao {

  int insertProper(Proper proper);

  List<Proper> queryProperList();

  List<Proper> queryProperListByProperCategoryId(String propercateId);

  Proper queryProperById(String properId);
}
