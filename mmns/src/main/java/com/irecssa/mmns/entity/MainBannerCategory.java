package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 1051
 * @desc: 主页轮播图种类
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class MainBannerCategory {

  private String  bannercateId;
  private String bannercateName;


  public String getBannercateId() {
    return bannercateId;
  }

  public void setBannercateId(String bannercateId) {
    this.bannercateId = bannercateId;
  }

  public String getBannercateName() {
    return bannercateName;
  }

  public void setBannercateName(String bannercateName) {
    this.bannercateName = bannercateName;
  }


}
