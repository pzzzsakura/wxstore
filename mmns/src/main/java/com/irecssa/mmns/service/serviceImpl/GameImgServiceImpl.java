package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.GameImgDao;
import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.GameImgExecution;
import com.irecssa.mmns.entity.GameImg;
import com.irecssa.mmns.enums.GameImgEnum;
import com.irecssa.mmns.exceptions.ProductOperationException;
import com.irecssa.mmns.service.GameImgService;
import com.irecssa.mmns.util.ImageUtil;
import com.irecssa.mmns.util.PathUtil;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/02 23:46
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class GameImgServiceImpl implements GameImgService {

  @Autowired
  private GameImgDao gameImgDao;
  public GameImgExecution addGameImg(List<ImageHolder> gameImgList) {
    addGameImgList(gameImgList);
    return new GameImgExecution(GameImgEnum.SUCCESS);
  }

  public GameImgExecution getGameImgList() {
    return new GameImgExecution(GameImgEnum.SUCCESS,gameImgDao.queryGameImgList()) ;
  }

  public GameImgExecution modifyGameImg(List<ImageHolder> gameImgList) {
    deleteGameImgList();
    addGameImgList(gameImgList);
    return new GameImgExecution(GameImgEnum.SUCCESS);
  }

  /**
   * 批量添加图片
   */
  @Transactional
  public void addGameImgList(List<ImageHolder> gameImgList) {
    // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
    String gameImgDest = PathUtil.getGameImgPath();
    List<GameImg> gameImgs= new ArrayList<GameImg>();

    // 遍历图片一次去处理，并添加进gameImg实体类里
    for (int i=0;i<gameImgList.size();i++) {
      ImageHolder productImgHolder = new ImageHolder(gameImgList.get(i).getImageName(),gameImgList.get(i).getImage());
      String imgAddr = ImageUtil.generateNormalImg(productImgHolder, gameImgDest);
      GameImg gameImg = new GameImg();
      gameImg.setGameImgUrl(imgAddr);
      gameImg.setCreateTime(new Date());
      gameImg.setEnableStatus(1);
      gameImg.setGameImgId(UUIDUtil.createUUID());
      gameImg.setPriority(i);
      gameImgs.add(gameImg);
    }
    // 如果确实是有图片需要添加的，就执行批量添加操作
    if (gameImgs.size()>0) {
      try {
        int effectedNum = gameImgDao.batchInsertGameImg(gameImgs);//插入
        if (effectedNum <= 0) {
          throw new ProductOperationException("创建游戏图片失败");
        }
      } catch (Exception e) {
        throw new ProductOperationException("创建游戏图片失败:" + e.toString());
      }
    }
  }
  /** 删除所有图
   *
   */
  private void deleteGameImgList() {
    // 根据productId获取原来的图片
    List<GameImg> gameImgList = gameImgDao.queryGameImgList();
    // 干掉原来的图片
    for (GameImg gameImg:gameImgList) {
      ImageUtil.deleteFileOrPath(gameImg.getGameImgUrl());
    }
    // 删除数据库里原有图片的信息
    gameImgDao.deleteGameImg();
  }
}
