package com.irecssa.mmns.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Title LogisticInfoVO
 * @Date Created in 2017/7/26
 * @Author:Back.T
 * @Description:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticInfoVO {

  @JsonProperty("AcceptTime")
  private String AcceptTime;  //时间

  @JsonProperty("AcceptStation")
  private String AcceptStation;  //地点

  public String getAcceptTime() {
    return AcceptTime;
  }

  public void setAcceptTime(String acceptTime) {
    AcceptTime = acceptTime;
  }

  public String getAcceptStation() {
    return AcceptStation;
  }

  public void setAcceptStation(String acceptStation) {
    AcceptStation = acceptStation;
  }
}