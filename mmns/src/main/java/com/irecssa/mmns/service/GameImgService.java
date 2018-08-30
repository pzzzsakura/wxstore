package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.GameImgExecution;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/02 23:43
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface GameImgService {

  GameImgExecution addGameImg(List<ImageHolder> gameImgList);

  GameImgExecution getGameImgList();

  GameImgExecution modifyGameImg(List<ImageHolder> gameImgList);
}
