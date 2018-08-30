package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.PossessProper;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 23:40
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface PossessProperDao {

  int insertPossessProper(PossessProper possessProper);

  List<PossessProper> queryPossessProperList(PossessProper possessProper);

  int updatePossessProper(PossessProper possessProper);
}
