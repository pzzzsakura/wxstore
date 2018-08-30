package com.irecssa.mmns.controller.mall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.ProductExecution;
import com.irecssa.mmns.entity.Combo;
import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.entity.ProductCategory;
import com.irecssa.mmns.enums.ProductEnum;
import com.irecssa.mmns.service.ProductService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/22 09:50
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */

@RestController
@RequestMapping("/mall")
public class ProductController {

  private final int IMAGEMAXCOUNT = 6;
  private static Logger log = LoggerFactory.getLogger(ProductController.class);
  @Autowired
  private ProductService productService;

  /**
   *  按条件查找商品信息
   * @param request
   * @return
   */
  @RequestMapping(value = "getproductlistbycondition", method = RequestMethod.POST)
  private Map<String, Object> getProductList(HttpServletRequest request)
      throws UnsupportedEncodingException {
    Map<String, Object> modelMap = new HashMap<String,Object>();
    int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
    int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
    String priceNew = HttpServletRequestUtil.getString(request,"priceNew");
    String productName = HttpServletRequestUtil.getString(request,"productName");
    if(productName!=null){
      productName = URLDecoder.decode(productName, "UTF-8");
    }
    String procateId = HttpServletRequestUtil.getString(request,"procateId");
    int isBoom = HttpServletRequestUtil.getInt(request,"isBoom");
    int isCombo = HttpServletRequestUtil.getInt(request,"isCombo");
    System.out.println(isBoom);
    System.out.println(isCombo);

    String  isFree = HttpServletRequestUtil.getString(request,"isFree");

    Product productCondition = compactProductCondition(procateId,productName,isBoom,isCombo,isFree);
    ProductExecution productExecution = productService.queryProductList(productCondition,pageIndex,pageSize,priceNew);
    if(productExecution.getState()==ProductEnum.SUCCESS.getState()){
      modelMap.put("success",true);
      modelMap.put("productList",productExecution.getProductList());
      modelMap.put("count",productExecution.getCount());
      return modelMap;
    }else{
      modelMap.put("success",false);
      modelMap.put("errMsg",productExecution.getStateInfo());
      return modelMap;
    }
  }
  /**
   * 添加商品信息
   */
  @RequestMapping(value = "addproduct", method = RequestMethod.POST)
  private Map<String, Object> addProduct(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    ObjectMapper objectMapper = new ObjectMapper();
    Product product;
    String productStr = "";
    ImageHolder thumbnail = null;
    List<ImageHolder> productBannerList = new ArrayList<ImageHolder>();//轮播图
    List<ImageHolder> productImgList = new ArrayList<ImageHolder>();//详情图
    MultipartHttpServletRequest multipartHttpServletRequest = null;
    productStr = HttpServletRequestUtil.getString(request, "productStr");
    try {
      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
          request.getSession().getServletContext());
      if (commonsMultipartResolver.isMultipart(request)) {
        //取出图片
        thumbnail = handleImage(request, thumbnail, productBannerList, productImgList);
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", "上传图片不能为空");
        return modelMap;
      }
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
      return modelMap;
    }
    Combo combo = null;
    try {
      //str转换product实体
      product = objectMapper.readValue(productStr, Product.class);
      if(product.getIsCombo()==1){
        String comboStr = HttpServletRequestUtil.getString(request,"comboStr");
         combo= objectMapper.readValue(comboStr, Combo.class);
      }
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
      return modelMap;
    }
    if (product != null && thumbnail != null) {
      try {
        ProductExecution productExecution = productService
            .addProduct(product, thumbnail, productImgList, productBannerList,combo);
        if (productExecution.getState() == ProductEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
          modelMap.put("productId", productExecution.getProduct().getProductId());
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", productExecution.getStateInfo());
        }
      } catch (Exception e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        return modelMap;
      }
    }
    return modelMap;
  }

  /**
   * 取出图片文件，包括缩略图，详情图，轮播图
   */
  private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail,
      List<ImageHolder> productBannerList, List<ImageHolder> productImgList) throws IOException {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    // 取出缩略图并构建ImageHolder对象
    CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest
        .getFile("thumbnail");
    if (thumbnailFile != null) {
      thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),
          thumbnailFile.getInputStream());
    }
    // 取出详情图和轮播图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
    for (int i = 0; i < IMAGEMAXCOUNT; i++) {
      CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
          .getFile("productImg" + i);
      CommonsMultipartFile productBannerFile = (CommonsMultipartFile) multipartRequest
          .getFile("productBanner" + i);
      if (productImgFile != null && productBannerFile != null) {
        // 若取出的第i个详情图片和轮播图文件流不为空，则将其加入对应列表
        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
            productImgFile.getInputStream());
        productImgList.add(productImg);
        ImageHolder productBanner = new ImageHolder(productBannerFile.getOriginalFilename(),
            productBannerFile.getInputStream());
        productBannerList.add(productBanner);
      } else {
        // 若取出的第i个图片文件流为空，则终止循环
        break;
      }
    }
    return thumbnail;
  }

  /**
   * 封装商品查询条件到Product实例中
   */
  private Product compactProductCondition(String productCategoryId, String productName,
     Integer isBoom,Integer isCombo,String isFree) {
    Product productCondition = new Product();
    productCondition.setEnableStatus(1);
    productCondition.setFreeId(isFree);
    productCondition.setIsBoom(isBoom);
    productCondition.setIsCombo(isCombo);
    // 若有指定类别的要求则添加进去
    if(productCategoryId!=null){
      ProductCategory productCategory = new ProductCategory();
      productCategory.setProcateId(productCategoryId);
      productCondition.setProductCategory(productCategory);
    }
    // 若有商品名模糊查询的要求则添加进去
    if (productName != null) {
      productCondition.setProductName(productName);
    }
    return productCondition;
  }
  /**
   * 查找单个商品详情信息
   */
  @RequestMapping(value = "getproductdetailinfo",method = RequestMethod.POST)
  private Map<String,Object> getProductDetailInfo(@RequestParam("productId")String productId){
    Map<String, Object> modelMap = new HashMap<String, Object>();
    ProductExecution productExecution = productService.getProductById(productId);
    if(productExecution.getState()==ProductEnum.SUCCESS.getState()){
      Product product = productExecution.getProduct();
      modelMap.put("success",true);
      modelMap.put("product",product);
      return modelMap;
    }else{
      modelMap.put("success",false);
      modelMap.put("product",productExecution.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 更新商品信息
   * @param request
   * @return
   */
  @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
  private Map<String, Object> modifyProduct(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
    ObjectMapper mapper = new ObjectMapper();
    Product product = null;
    ImageHolder thumbnail = null;
    List<ImageHolder> productBannerList = new ArrayList<ImageHolder>();//轮播图
    List<ImageHolder> productImgList = new ArrayList<ImageHolder>();//详情图
    MultipartHttpServletRequest multipartHttpServletRequest = null;
    // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
    try {
      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
          request.getSession().getServletContext());
      if (commonsMultipartResolver.isMultipart(request)) {
        //取出图片
        thumbnail = handleImage(request, thumbnail, productBannerList, productImgList);
      }
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    }
    try {
      String productStr = HttpServletRequestUtil.getString(request, "productStr");
      // 尝试获取前端传过来的表单string流并将其转换成Product实体类
      product = mapper.readValue(productStr, Product.class);
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    }
    // 非空判断
    if (product != null) {
      try {
        // 开始进行商品信息变更操作
        ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList,productBannerList);
        if (pe.getState() == ProductEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", pe.getStateInfo());
        }
      } catch (RuntimeException e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.toString());
        return modelMap;
      }

    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请输入商品信息");
    }
    return modelMap;
  }
}

