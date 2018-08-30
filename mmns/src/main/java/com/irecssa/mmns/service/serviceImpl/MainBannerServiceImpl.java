package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.MainBannerDao;
import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.MainBannerExecution;
import com.irecssa.mmns.entity.MainBanner;
import com.irecssa.mmns.enums.MainBannerEnum;
import com.irecssa.mmns.exceptions.MainBannerOperationException;
import com.irecssa.mmns.service.MainBannerService;
import com.irecssa.mmns.util.ImageUtil;
import com.irecssa.mmns.util.PathUtil;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 1119
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class MainBannerServiceImpl implements MainBannerService {

  @Autowired
  private MainBannerDao mainBannerDao;

  //上传轮播图信息
  @Transactional
  public MainBannerExecution insertMainBanner(MainBanner mainBanner, ImageHolder imageHolder) {
    if (mainBanner == null) {
      return new MainBannerExecution(MainBannerEnum.LOGINFAIL);
    }
    if (imageHolder != null) {
      try {
        addMainBannerImg(mainBanner, imageHolder);
      } catch (Exception e) {
        throw new MainBannerOperationException("insertMainBanner error:上传图片异常");
      }
    }
    mainBanner.setBannerId(UUIDUtil.createUUID());
    mainBanner.setCreateTime(new Date());
    mainBanner.setLastEditTime(new Date());
    mainBanner.setEnableStatus(1);

    int result;
    try {
      result = mainBannerDao.insertMainBanner(mainBanner);
    } catch (Exception e) {
      throw new MainBannerOperationException("insertMainBannerList error:插入轮播图失败");
    }
    if (result > 0) {
      return new MainBannerExecution(MainBannerEnum.SUCCESS,mainBanner);
    } else {
      throw new MainBannerOperationException("insertMainBannerList error:查询轮播图列表失败");
    }
  }

  //查询轮播图
  public MainBannerExecution getMainBannerList() {
    List<MainBanner> bannerList;
    bannerList = mainBannerDao.queryMainBannerList();
    if (bannerList != null) {
      return new MainBannerExecution(MainBannerEnum.SUCCESS, bannerList);
    } else {
      return new MainBannerExecution(MainBannerEnum.RESULTNULL);
    }
  }

  //上传主页轮播图单张图片
  private void addMainBannerImg(MainBanner mainBanner, ImageHolder imageHolder) {
    String dest = PathUtil.getMainBannerImagePath();
    String thumbnailAddr = ImageUtil.generateThumbnail(imageHolder, dest);
    mainBanner.setBannerUrl(thumbnailAddr);
  }
}
