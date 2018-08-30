
package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.GameImg;
import com.irecssa.mmns.enums.GameImgEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/02 13:54
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class GameImgExecution {

  private String stateInfo;
  private int state;
  private List<GameImg> gameImgList;

  public GameImgExecution(GameImgEnum gameimgEnum,List<GameImg> gameimgList) {
    this.gameImgList = gameimgList;
    this.state=gameimgEnum.getState();
    this.stateInfo = gameimgEnum.getStateInfo();
  }
  public GameImgExecution(GameImgEnum gameimgEnum) {
    this.state=gameimgEnum.getState();
    this.stateInfo = gameimgEnum.getStateInfo();
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

  public List<GameImg> getGameimgList() {
    return gameImgList;
  }

  public void setGameimgList(List<GameImg> gameimgList) {
    this.gameImgList = gameimgList;
  }
}
