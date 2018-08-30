package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.WorldImgDao;
import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.WorldImgExecution;
import com.irecssa.mmns.entity.WorldImg;
import com.irecssa.mmns.enums.WorldImgEnum;
import com.irecssa.mmns.exceptions.WorldImgException;
import com.irecssa.mmns.service.WorldImgService;
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
 * @datetime: 2017/12/03 00:07
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class WorldImgServiceImpl implements WorldImgService {

  @Autowired
  private WorldImgDao worldImgDao;
  public WorldImgExecution addWorldImg(List<ImageHolder> worldImgList) {
    addWorldImgList(worldImgList);
    return new WorldImgExecution(WorldImgEnum.SUCCESS);
  }

  public WorldImgExecution getWorldImgList() {
    return new WorldImgExecution(WorldImgEnum.SUCCESS,worldImgDao.queryWorldImgList());
  }

  public WorldImgExecution modifyWorldImg(List<ImageHolder> worldImgList) {
    deleteWorldImgList();
    addWorldImgList(worldImgList);
    return new WorldImgExecution(WorldImgEnum.SUCCESS);
  }

  /**
   * 批量添加图片
   */
  @Transactional
  public void addWorldImgList(List<ImageHolder> worldImgList) {
    // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
    String worldImgDest = PathUtil.getWorldImgPath();
    List<WorldImg> worldImgs= new ArrayList<WorldImg>();

    // 遍历图片一次去处理，并添加进gameImg实体类里
    for (int i=0;i<worldImgList.size();i++) {
      ImageHolder worldImgHolder = new ImageHolder(worldImgList.get(i).getImageName(),worldImgList.get(i).getImage());
      String imgAddr = ImageUtil.generateNormalImg(worldImgHolder, worldImgDest);
      WorldImg worldImg = new WorldImg();
      worldImg.setCreateTime(new Date());
      worldImg.setEnableStatus(1);
      worldImg.setPriority(i);
      worldImg.setWorldImgId(UUIDUtil.createUUID());
      worldImg.setWorldImgUrl(imgAddr);
      worldImgs.add(worldImg);
    }
    // 如果确实是有图片需要添加的，就执行批量添加操作
    if (worldImgs.size()>0) {
      try {
        int effectedNum = worldImgDao.batchInsertWorldImg(worldImgs);//插入
        if (effectedNum <= 0) {
          throw new WorldImgException("世界图失败");
        }
      } catch (Exception e) {
      throw new WorldImgException("世界图失败"+e.getMessage());
      }
    }
  }
  /** 删除所有图
   *
   */
  private void deleteWorldImgList() {
    // 根据productId获取原来的图片
    List<WorldImg> worldImgList = worldImgDao.queryWorldImgList();
    // 干掉原来的图片
    for (WorldImg worldImg:worldImgList) {
      ImageUtil.deleteFileOrPath(worldImg.getWorldImgUrl());
    }
    // 删除数据库里原有图片的信息
    worldImgDao.deleteWorldImg();
  }
}
