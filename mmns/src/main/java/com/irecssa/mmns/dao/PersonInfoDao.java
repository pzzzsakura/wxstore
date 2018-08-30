package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.PersonInfo;

/**
 * Ma.li.ran 2017/11/20 0020 13:37
 */
public interface PersonInfoDao {

  /**
   * 插入用户初始信息
   * @param personInfo
   * @return
   */
  int insertPersonInfo(PersonInfo personInfo);

  int updatePersonInfo(PersonInfo personInfo);

  PersonInfo queryPersonInfo(String personInfoId);
}
