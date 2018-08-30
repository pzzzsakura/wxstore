package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.ProperCategory;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 15:19
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProperCategoryDao {

  int insertProperCategory(ProperCategory properCategory);

  List<ProperCategory> queryProperCategoryList();
}
