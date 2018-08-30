package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.MainBannerCategory;
import com.irecssa.mmns.enums.MainBannerCategoryEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 13:03
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class MainBannerCategoryExecution {

  private int state;
  private String stateInfo;
  private MainBannerCategory mainBannerCategory;
  private List<MainBannerCategory> mainBannerCategoryList;

  public MainBannerCategoryExecution(MainBannerCategoryEnum mainBannerCategoryEnum) {
    this.state = state;
    this.stateInfo = mainBannerCategoryEnum.getStateInfo();
  }

  public MainBannerCategoryExecution(MainBannerCategoryEnum mainBannerCategoryEnum,
      MainBannerCategory mainBannerCategory) {
    this.state = mainBannerCategoryEnum.getState();
    this.stateInfo = mainBannerCategoryEnum.getStateInfo();
    this.mainBannerCategory = mainBannerCategory;
  }
  public MainBannerCategoryExecution(MainBannerCategoryEnum mainBannerCategoryEnum,
     List<MainBannerCategory> mainBannerCategoryList) {
    this.state = mainBannerCategoryEnum.getState();
    this.stateInfo = mainBannerCategoryEnum.getStateInfo();
    this.mainBannerCategoryList = mainBannerCategoryList;
  }

  public MainBannerCategory getMainBannerCategory() {
    return mainBannerCategory;
  }

  public void setMainBannerCategory(MainBannerCategory mainBannerCategory) {
    this.mainBannerCategory = mainBannerCategory;
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


  public List<MainBannerCategory> getMainBannerCategoryList() {
    return mainBannerCategoryList;
  }

  public void setMainBannerCategoryList(List<MainBannerCategory> mainBannerCategoryList) {
    this.mainBannerCategoryList = mainBannerCategoryList;
  }
}
