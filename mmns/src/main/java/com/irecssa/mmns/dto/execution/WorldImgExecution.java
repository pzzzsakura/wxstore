package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.WorldImg;
import com.irecssa.mmns.enums.WorldImgEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/02 23:56
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WorldImgExecution {

  private String stateInfo;
  private int state;
  private List<WorldImg> worldImgList;

  public WorldImgExecution(WorldImgEnum worldImgEnum,List<WorldImg> worldImgList) {
    this.worldImgList = worldImgList;
    this.state = worldImgEnum.getState();
    this.stateInfo = worldImgEnum.getStateInfo();
  }
  public WorldImgExecution(WorldImgEnum worldImgEnum) {
    this.state = worldImgEnum.getState();
    this.stateInfo = worldImgEnum.getStateInfo();
  }

  public String getStateInfo() {
    return stateInfo;
  }

  public void setStateInfo(String stateInfo) {
    this.stateInfo = stateInfo;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public List<WorldImg> getWorldImgList() {
    return worldImgList;
  }

  public void setWorldImgList(List<WorldImg> worldImgList) {
    this.worldImgList = worldImgList;
  }
}
