package com.irecssa.mmns.util;

/**
 * 得到存储图片路径
 * Ma.li.ran
 * 2017/11/1 0001 15:21
 */
public class PathUtil {

  private static String seperator = System.getProperty("file.separator");

  /**
   * 项目图片根路径
   * @return
   */
  public static String getImgBasePath() {

    //得到当前系统
    String os = System.getProperty("os.name");
    String basePath = "";
    //通常专用一台服务器存储图片等资源
    if (os.toLowerCase().startsWith("win")) {
      basePath = "D:/images/";
    } else {
      basePath = "/irecssa/mmns/images";
    }
    basePath = basePath.replace("/", seperator);
    return basePath;
  }

  /**
   * 主页轮播图片子路径
   * @param
   * @return
   */
  public static String getMainBannerImagePath() {
    String imagePath = "mall/mainbanner/";
    return imagePath.replace("/", seperator);
  }

  /**
   * 产品缩略图
   * @return
   */
  public static String getThumbnailPath(String productId) {
    String imagePath = "mall/product/"+productId+"/thumbnail/";
    return imagePath.replace("/", seperator);
  }
  /**
   * 产品详情图
   * @return
   */
  public static String getProductImgPath(String productId) {
    String imagePath = "mall/product/"+productId+"/productImg/";
    return imagePath.replace("/", seperator);
  }
  /**
   * 产品轮播图
   * @return
   */
  public static String getProductBannerPath(String productId) {
    String imagePath = "mall/product/"+productId+"/productBanner/";
    return imagePath.replace("/", seperator);
  }

  public static String getGameImgPath() {
    String imagePath = "game/gameImg/";
    return imagePath.replace("/", seperator);
  }
  public static String getWorldImgPath() {
    String imagePath = "game/worldImg/";
    return imagePath.replace("/", seperator);
  }
}
