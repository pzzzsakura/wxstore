package com.irecssa.mmns.controller.mall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.MainBannerExecution;
import com.irecssa.mmns.entity.MainBanner;
import com.irecssa.mmns.enums.MainBannerEnum;
import com.irecssa.mmns.service.MainBannerService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 002110:12
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class MainBannerController {

  private static Logger log = LoggerFactory.getLogger(ProductController.class);
  @Autowired
  private MainBannerService mainBannerService;

  /**
   * 获取主页轮播图列表
   */
  @RequestMapping(value = "getmainbannerlist", method = RequestMethod.POST)
  private Map<String, Object> getMainBannerList() {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    List<MainBanner> mainBannerList;
    MainBannerExecution mainBannerExecution = mainBannerService.getMainBannerList();
    if (mainBannerExecution.getState() == MainBannerEnum.SUCCESS.getState()
        && mainBannerExecution.getMainBannerList() != null
        && mainBannerExecution.getMainBannerList().size() > 0) {
      mainBannerList = mainBannerExecution.getMainBannerList()                                                                                                           ;
      modelMap.put("success", true);
      modelMap.put("mainBannerList", mainBannerList);
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", mainBannerExecution.getStateInfo());
    }
    return modelMap;
  }

  /**
   * 添加轮播图信息及上传图片
   * @param request
   * @return
   */

  @RequestMapping(value = "addmainbannercontroller",method = RequestMethod.POST)
  private Map<String, Object> addMainBannerController(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    ObjectMapper objectMapper = new ObjectMapper();
    MainBanner mainBanner ;
    String mainBannerStr = "";
    ImageHolder imageHolder = null;
    MultipartHttpServletRequest multipartHttpServletRequest = null;
    mainBannerStr = HttpServletRequestUtil.getString(request, "mainBannerStr");
    try {
      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
          request.getSession().getServletContext());
      if (commonsMultipartResolver.isMultipart(request)) {
        //取出缩略图
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
          imageHolder = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
      }else{
        modelMap.put("success",false);
        modelMap.put("errMsg","上传图片不能为空");
        return modelMap;
      }
    } catch (Exception e) {
      modelMap.put("success",false);
      modelMap.put("errMsg",e.getMessage());
      return modelMap;

    }
    try{
      //str转换product实体
      mainBanner = objectMapper.readValue(mainBannerStr,MainBanner.class);
    }catch(Exception e){
      modelMap.put("success",false);
      modelMap.put("errMsg",e.getMessage());
      return modelMap;
    }
    if(mainBanner!=null&&imageHolder!=null){
      try {
       MainBannerExecution mainBannerExecution = mainBannerService.insertMainBanner(mainBanner,imageHolder);
        if(mainBannerExecution.getState()== MainBannerEnum.SUCCESS.getState()){
          modelMap.put("success",true);
          modelMap.put("mainBanner",mainBannerExecution.getMainBanner());
        }else{
          modelMap.put("success",false);
          modelMap.put("errMsg",mainBannerExecution.getStateInfo());
        }
      }catch(Exception e){
        modelMap.put("success",false);
        modelMap.put("errMsg",e.getMessage());
        return modelMap;
      }
    }


    return  modelMap;




}

}
