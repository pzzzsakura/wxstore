package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.MainBanner;
import com.irecssa.mmns.enums.MainBannerEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 1023
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class MainBannerExecution {

  private int state;
  private String stateInfo;
  private List<MainBanner> mainBannerList;
  private MainBanner mainBanner;

  public MainBannerExecution(MainBannerEnum mainBannerEnum){
    this.state = mainBannerEnum.getState();
    this.stateInfo = mainBannerEnum.getStateInfo();
  }

  public MainBannerExecution(MainBannerEnum mainBannerEnum,List<MainBanner> mainBannerList) {
    this.state = mainBannerEnum.getState();
    this.stateInfo = mainBannerEnum.getStateInfo();
    this.mainBannerList = mainBannerList;
  }

  public MainBannerExecution(MainBannerEnum mainBannerEnum,MainBanner mainBanner){
    this.state = mainBannerEnum.getState();
    this.stateInfo = mainBannerEnum.getStateInfo();
    this.mainBanner = mainBanner;
  }

  public int getState() {
    return state;
  }

  public String getStateInfo() {
    return stateInfo;
  }

  public List<MainBanner> getMainBannerList() {
    return mainBannerList;
  }

  public MainBanner getMainBanner() {
    return mainBanner;
  }
}
