package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.ComboDao;
import com.irecssa.mmns.dao.ProductBannerDao;
import com.irecssa.mmns.dao.ProductDao;
import com.irecssa.mmns.dao.ProductDescDao;
import com.irecssa.mmns.dao.ProductImgDao;
import com.irecssa.mmns.dao.ProductRepertoryDao;
import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.ProductExecution;
import com.irecssa.mmns.entity.Combo;
import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.entity.ProductBanner;
import com.irecssa.mmns.entity.ProductDesc;
import com.irecssa.mmns.entity.ProductImg;
import com.irecssa.mmns.entity.ProductRepertory;
import com.irecssa.mmns.enums.ProductEnum;
import com.irecssa.mmns.exceptions.ComboOperationException;
import com.irecssa.mmns.exceptions.ProductOperationException;
import com.irecssa.mmns.service.ProductService;
import com.irecssa.mmns.util.ImageUtil;
import com.irecssa.mmns.util.PageCalculator;
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
 * @datetime: 2017/11/22 15:38
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductDescDao productDescDao;
  @Autowired
  private ProductDao productDao;
  @Autowired
  private ProductRepertoryDao productRepertoryDao;
  @Autowired
  private ProductImgDao productImgDao;
  @Autowired
  private ProductBannerDao productBannerDao;
  @Autowired
  private ComboDao comboDao;

  /**
   * 按条件查询商品列表
   */
  public ProductExecution queryProductList(Product productCondition, int pageIndex, int pageSize,
      String priceNew) {
    int rowIndex = PageCalculator.caculateRowIndex(pageIndex, pageSize);
    List<Product> list = productDao
        .queryProductList(productCondition, rowIndex, pageSize, priceNew);
    int count = productDao.queryProductCount(productCondition);
    return new ProductExecution(ProductEnum.SUCCESS, list, count);
  }

  /**
   * 按条件查询商品数量
   */
  public ProductExecution getProductCount(Product productCondition) {
    int count = productDao.queryProductCount(productCondition);
    return new ProductExecution(ProductEnum.SUCCESS, count);
  }

  /**
   * 添加商品详情以及上传相关图片
   */
  @Transactional
  public ProductExecution addProduct(Product product, ImageHolder thumbnail,
      List<ImageHolder> productImgList,List<ImageHolder> productBannerList,Combo combo) {


    if (product != null && product.getProductDesc() != null
        && product.getProductDesc().getProdescText() != null
        && product.getProductRepertory() != null
        && product.getProductRepertory().getCurrentNum() >= 0) {
      //设置默认属性
      ProductDesc productDesc = new ProductDesc();
      try {
        productDesc.setProdescText(product.getProductDesc().getProdescText());
        productDesc.setCreateTime(new Date());
        productDescDao.insertProductDesc(productDesc);//增加介绍
      } catch (Exception e) {
        throw new ProductOperationException("addProduct:insertProductDesc error" + e.getMessage());
      }
      ProductRepertory productRepertory = new ProductRepertory();
      try {
        productRepertory.setProrepId(UUIDUtil.createUUID());
        productRepertory.setSaleNum(0);
        productRepertory.setCurrentNum(product.getProductRepertory().getCurrentNum());
        productRepertory.setCreateTime(new Date());
        productRepertory.setLastEditTime(new Date());
        productRepertoryDao.insertProductRepertory(productRepertory);//初始化当前商品库存
      } catch (Exception e) {
        throw new ProductOperationException(
            "addProduct:insertProductRepertory error" + e.getMessage());
      }
      product.setProductId(UUIDUtil.createUUID());
      product.setCreateTime(new Date());
      product.setLastEditTime(new Date());
      product.setEnableStatus(1);
      product.setProductDesc(productDesc);
      product.setProductRepertory(productRepertory);
      if(product!=null&&product.getIsCombo()==1){
        combo.setComboId(product.getProductId());
        try{
          comboDao.insertCombo(combo);
        }catch(Exception e){
          throw new ComboOperationException("insertCombo error "+e.getMessage());
        }
      }
      //若商品缩略图不为空则添加
      if (thumbnail != null) {
        addThumbnail(product, thumbnail);
      }
      if (productImgList != null && productImgList.size() > 0) {
        addProductImgList(product, productImgList,productBannerList);
      }

    } else {
      return new ProductExecution(ProductEnum.NULL_AUTH_INFO);
    }
      try {
         int result = productDao.insertProduct(product);
        if (result <= 0) {
          throw new ProductOperationException("创建商品失败");
        }
      } catch (ProductOperationException e) {
        throw new ProductOperationException("addProduct error " + e.getMessage());
      }
    return new ProductExecution(ProductEnum.SUCCESS, product);
  }

  /**
   * 得到商品详情
   * @param productId
   * @return
   */
  public ProductExecution getProductById(String productId) {
    Product product ;
    if(productId!=null){
       product = productDao.queryProductById(productId);
       List<ProductImg> productImgList = new ArrayList<ProductImg>();
       productImgList = productImgDao.queryProductImgList(productId);
       ProductRepertory productRepertory = productRepertoryDao.queryProductRepertory(product.getProductRepertory().getProrepId());
       if(product!=null&&product.getProductBannerList()!=null
           &&product.getProductBannerList().size()>0&&productImgList!=null
           &&productImgList.size()>0){
         product.setProductImgList(productImgList);
         product.setProductRepertory(productRepertory);
         return new ProductExecution(ProductEnum.SUCCESS,product);
       }else{
         return new ProductExecution(ProductEnum.NULLOFRESULT);
       }
    }else{
      return new ProductExecution(ProductEnum.NULL_AUTH_INFO);
    }
  }

  /**
   * 修改商品信息
   * @param product
   * @param thumbnail
   * @param productImgList
   * @param productBannerList
   * @return
   */
  @Transactional
  public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
      List<ImageHolder> productImgList,List<ImageHolder> productBannerList) {
    // 空值判断
    if (product != null ) {
      // 给商品设置上默认属性
      try {
        productDescDao.updateProductDesc(product.getProductDesc());
      }catch(Exception e){
        throw new ProductOperationException("updateProductDesc error"+e.getMessage());
      }
      product.setLastEditTime(new Date());
      // 若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
      if (thumbnail != null) {
        // 先获取一遍原有信息，因为原来的信息里有原图片地址
        Product tempProduct = productDao.queryProductById(product.getProductId());
        if (tempProduct.getProductImg() != null) {
          ImageUtil.deleteFileOrPath(tempProduct.getProductImg());
        }
        addThumbnail(product, thumbnail);
      }
      // 如果有新存入的商品详情图，则将原先的删除，并添加新的图片
      if (productImgList != null && productImgList.size() > 0
          &&productBannerList!=null&&productBannerList.size()>0) {
        deleteProductImgList(product.getProductId());
        deleteProductBannerList(product.getProductId());
        addProductImgList(product, productImgList,productBannerList);
      }
      try {
        // 更新商品信息
        int effectedNum = productDao.updateProduct(product);
        if (effectedNum <= 0) {
          throw new ProductOperationException("更新商品信息失败");
        }
        return new ProductExecution(ProductEnum.SUCCESS, product);
      } catch (Exception e) {
        throw new ProductOperationException("更新商品信息失败:" + e.toString());
      }
    } else {
      return new ProductExecution(ProductEnum.NULL_AUTH_INFO);
    }
  }


  //上传缩略图片
  private void addThumbnail(Product product, ImageHolder thumbnail) {
    String dest = PathUtil.getThumbnailPath(product.getProductId());
    String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
    product.setProductImg(thumbnailAddr);
  }
  /**
   * 批量添加图片
   */
  @Transactional
  public void addProductImgList(Product product, List<ImageHolder> productImgList,List<ImageHolder> productBannerList) {
    // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
    String productBannerDest = PathUtil.getProductBannerPath(product.getProductId());
    String productImgDest = PathUtil.getProductImgPath(product.getProductId());
    List<ProductImg> productImgs= new ArrayList<ProductImg>();
    List<ProductBanner> productBanners= new ArrayList<ProductBanner>();
    // 遍历图片一次去处理，并添加进productImg实体类里
    for (int i=0;i<productImgList.size();i++) {
      ImageHolder productImgHolder = new ImageHolder(productImgList.get(i).getImageName(),productImgList.get(i).getImage());
      String imgAddr = ImageUtil.generateNormalImg(productImgHolder, productImgDest);
      ProductImg productImg = new ProductImg();
      productImg.setProimgId(UUIDUtil.createUUID());
      productImg.setCreateTime(new Date());
      productImg.setEnableStatus(1);
      productImg.setProimgUrl(imgAddr);
      productImg.setPriority(i);
      productImg.setProductId(product.getProductId());
      productImgs.add(productImg);
    }
    // 遍历图片一次去处理，并添加进productBanner实体类里
    for (int i=0;i<productBannerList.size();i++) {
      ImageHolder productImgHolder = new ImageHolder(productBannerList.get(i).getImageName(),productBannerList.get(i).getImage());
      String bannerAddr = ImageUtil.generateNormalImg(productImgHolder, productImgDest);
      ProductBanner productBanner = new ProductBanner();
      productBanner.setProbannerUrl(bannerAddr);
      productBanner.setProbannerId(UUIDUtil.createUUID());
      productBanner.setCreateTime(new Date());
      productBanner.setEnableStatus(1);
      productBanner.setPriority(i);
      productBanner.setCreateTime(new Date());
      productBanner.setProductId(product.getProductId());
      productBanners.add(productBanner);
    }
    // 如果确实是有图片需要添加的，就执行批量添加操作
    if (productBanners.size() > 0&&productImgs.size()>0) {
      try {
        int effectedNum = productImgDao.batchInsertProductImg(productImgs);//插入
        if (effectedNum <= 0) {
          throw new ProductOperationException("创建商品详情图片失败");
        }
      } catch (Exception e) {
        throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
      }

      try{
        int effectedNum2 = productBannerDao.batchInsertProductBanner(productBanners);//插入
        if (effectedNum2 <= 0) {
          throw new ProductOperationException("创建商品轮播图片失败");
        }
      }catch(Exception e){
        throw new ProductOperationException("创建商品轮播图片失败:" + e.toString());
      }

    }
  }
   /** 删除某个商品下的所有详情图
     *
     * @param productId
     */
  private void deleteProductImgList(String productId) {
    // 根据productId获取原来的图片
    List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
    // 干掉原来的图片
    for (ProductImg productImg : productImgList) {
      ImageUtil.deleteFileOrPath(productImg.getProimgUrl());
    }
    // 删除数据库里原有图片的信息
    productImgDao.deleteProductImgByProductId(productId);
  }
  private void deleteProductBannerList(String productId) {
    // 根据productId获取原来的图片
    List<ProductBanner> productBannerList = productBannerDao.queryProductBannerList(productId);
    // 干掉原来的图片
    for (ProductBanner productBanner : productBannerList) {
      ImageUtil.deleteFileOrPath(productBanner.getProbannerUrl());
    }
    // 删除数据库里原有图片的信息
   productBannerDao.deleteProductBannerByProductId(productId);
  }
}
