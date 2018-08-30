package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.WorldImgExecution;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 00:05
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WorldImgService {
  WorldImgExecution addWorldImg(List<ImageHolder> worldImgList);

  WorldImgExecution getWorldImgList();

  WorldImgExecution modifyWorldImg(List<ImageHolder> worldImgList);
}
