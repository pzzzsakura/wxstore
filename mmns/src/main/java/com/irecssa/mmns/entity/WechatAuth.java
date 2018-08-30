package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * Ma.li.ran 2017/11/20 0020 13:29
 */
public class WechatAuth {

  private String wechatId;
  private String wechatName;//微信名
  private String openId;//微信openid
  private String wechatHeadimg;//头像
  private String wechatSex;//性别
  private String address;//地址
  private Date createTime;//创建时间
  private PersonInfo personInfo;//信息
  private Integer num;//名次

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getWechatId() {
    return wechatId;
  }

  public void setWechatId(String wechatId) {
    this.wechatId = wechatId;
  }

  public String getWechatName() {
    return wechatName;
  }

  public void setWechatName(String wechatName) {
    this.wechatName = wechatName;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getWechatHeadimg() {
    return wechatHeadimg;
  }

  public void setWechatHeadimg(String wechatHeadimg) {
    this.wechatHeadimg = wechatHeadimg;
  }

  public String getWechatSex() {
    return wechatSex;
  }

  public void setWechatSex(String wechatSex) {
    this.wechatSex = wechatSex;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public PersonInfo getPersonInfo() {
    return personInfo;
  }

  public void setPersonInfo(PersonInfo personInfo) {
    this.personInfo = personInfo;
  }
}
