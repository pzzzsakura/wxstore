package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.Combo;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 17:51
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ComboDao {

  int insertCombo(Combo combo);

  List<Combo> queryComboList();

  Combo queryComboById(String comboId);

  List<Combo> queryComboByProductId(Combo combo);
}
