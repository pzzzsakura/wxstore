package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.GameImg;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/02 13:59
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface GameImgDao {

  List<GameImg>  queryGameImgList();

  int batchInsertGameImg(List<GameImg> gameImgList);

  int deleteGameImg();
}
