package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.WorldImg;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/02 23:52
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WorldImgDao {

  int batchInsertWorldImg(List<WorldImg> worldImgList);

  List<WorldImg> queryWorldImgList();

  void deleteWorldImg();
}
