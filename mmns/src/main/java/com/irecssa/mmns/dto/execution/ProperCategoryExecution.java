package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.ProperCategory;
import com.irecssa.mmns.enums.ProperCategoryEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/04 15:14
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProperCategoryExecution {

  private int state;
  private String stateInfo;
  private ProperCategory properCategory;
  private List<ProperCategory> properCategorieList;

  public ProperCategoryExecution(ProperCategoryEnum properCategoryEnum,List<ProperCategory> properCategorieList) {
    this.properCategorieList = properCategorieList;
    this.state=properCategoryEnum.getState();
    this.stateInfo=properCategoryEnum.getStateInfo();
  }

  public ProperCategoryExecution(ProperCategoryEnum properCategoryEnum,ProperCategory properCategory) {
    this.properCategory = properCategory;
    this.state=properCategoryEnum.getState();
    this.stateInfo=properCategoryEnum.getStateInfo();
  }
  public ProperCategoryExecution(ProperCategoryEnum properCategoryEnum) {

    this.state=properCategoryEnum.getState();
    this.stateInfo=properCategoryEnum.getStateInfo();
  }



  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getStateInfo() {
    return stateInfo;
  }

  public void setStateInfo(String stateInfo) {
    this.stateInfo = stateInfo;
  }

  public ProperCategory getProperCategory() {
    return properCategory;
  }

  public void setProperCategory(ProperCategory properCategory) {
    this.properCategory = properCategory;
  }

  public List<ProperCategory> getProperCategorieList() {
    return properCategorieList;
  }

  public void setProperCategorieList(List<ProperCategory> properCategorieList) {
    this.properCategorieList = properCategorieList;
  }
}
