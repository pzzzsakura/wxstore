package com.irecssa.mmns.entity;

/**
 * Ma.li.ran 2017/11/20 0020 13:20
 */
public class PersonInfo {

  private String personInfoId;
  private Integer mobi;//初始膜币 默认0
  private Integer iage;//艾瑞莎龄 默认1
  private Integer power;//体力
  private Integer gameIntegral;//游戏积分
  private Integer shopIntegral;//商城积分
  private Integer chanceQmm;
  private Integer chanceDzp;

  public Integer getChanceQmm() {
    return chanceQmm;
  }

  public void setChanceQmm(Integer chanceQmm) {
    this.chanceQmm = chanceQmm;
  }

  public Integer getChanceDzp() {
    return chanceDzp;
  }

  public void setChanceDzp(Integer chanceDzp) {
    this.chanceDzp = chanceDzp;
  }

  public String getPersonInfoId() {
    return personInfoId;
  }

  public void setPersonInfoId(String personInfoId) {
    this.personInfoId = personInfoId;
  }

  public Integer getMobi() {
    return mobi;
  }

  public void setMobi(Integer mobi) {
    this.mobi = mobi;
  }

  public Integer getIage() {
    return iage;
  }

  public void setIage(Integer iage) {
    this.iage = iage;
  }

  public Integer getPower() {
    return power;
  }

  public void setPower(Integer power) {
    this.power = power;
  }

  public Integer getGameIntegral() {
    return gameIntegral;
  }

  public void setGameIntegral(Integer gameIntegral) {
    this.gameIntegral = gameIntegral;
  }

  public Integer getShopIntegral() {
    return shopIntegral;
  }

  public void setShopIntegral(Integer shopIntegral) {
    this.shopIntegral = shopIntegral;
  }

}
