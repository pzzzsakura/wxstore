package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.ProperCategoryExecution;
import com.irecssa.mmns.entity.ProperCategory;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 15:26
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface ProperCategoryService {

  ProperCategoryExecution insertProperCategory(ProperCategory properCategory);

  ProperCategoryExecution queryProperCategoryList();
}
