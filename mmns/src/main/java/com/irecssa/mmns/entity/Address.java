package com.irecssa.mmns.entity;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 11:54
 * @desc: 收货地址
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Address {

  private String addressId;
  private String addressName;//收货人
  private String addressPhone;//电话
  private String addressProvince;//省
  private String addressCity;//市
  private String addressArea;//区
  private String addressRow;//街道
  private String postCode;//邮编
  private String addressDetail;//详细地址
  private PersonInfo personInfo;//个人信息
  private Integer isDefault;//是否为默认地址1是0否

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getAddressName() {
    return addressName;
  }

  public void setAddressName(String addressName) {
    this.addressName = addressName;
  }

  public String getAddressPhone() {
    return addressPhone;
  }

  public void setAddressPhone(String addressPhone) {
    this.addressPhone = addressPhone;
  }

  public String getAddressProvince() {
    return addressProvince;
  }

  public void setAddressProvince(String addressProvince) {
    this.addressProvince = addressProvince;
  }

  public String getAddressCity() {
    return addressCity;
  }

  public void setAddressCity(String addressCity) {
    this.addressCity = addressCity;
  }

  public String getAddressArea() {
    return addressArea;
  }

  public void setAddressArea(String addressArea) {
    this.addressArea = addressArea;
  }

  public String getAddressRow() {
    return addressRow;
  }

  public void setAddressRow(String addressRow) {
    this.addressRow = addressRow;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getAddressDetail() {
    return addressDetail;
  }

  public void setAddressDetail(String addressDetail) {
    this.addressDetail = addressDetail;
  }

  public PersonInfo getPersonInfo() {
    return personInfo;
  }

  public void setPersonInfo(PersonInfo personInfo) {
    this.personInfo = personInfo;
  }

  public Integer getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Integer isDefault) {
    this.isDefault = isDefault;
  }
}
