package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.MainBannerCategoryExecution;
import com.irecssa.mmns.entity.MainBannerCategory;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 1302
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface MainBannerCategoryService {

 MainBannerCategoryExecution insertMainBannerCategory(MainBannerCategory mainBannerCategory);

 MainBannerCategoryExecution getMainBannerCategoryList(MainBannerCategory mainBannerCategory);
}
