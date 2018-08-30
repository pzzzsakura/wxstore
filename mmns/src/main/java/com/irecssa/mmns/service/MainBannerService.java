package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.MainBannerExecution;
import com.irecssa.mmns.entity.MainBanner;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 1118
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface MainBannerService {

  MainBannerExecution insertMainBanner(MainBanner mainBanner,ImageHolder imageHolder);

  MainBannerExecution getMainBannerList();

}
