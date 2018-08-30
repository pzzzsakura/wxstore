package com.irecssa.mmns.controller.game;

import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.WorldImgExecution;
import com.irecssa.mmns.enums.WorldImgEnum;
import com.irecssa.mmns.service.WorldImgService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/03 00:30
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/game")
public class WorldImgController {

  @Autowired
  private WorldImgService worldImgService;
  @RequestMapping(value = "addworldimg",method = RequestMethod.POST)
  private Map<String,Object> addWorldImg(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    List<ImageHolder> worldImgList = new ArrayList<ImageHolder>();
    MultipartHttpServletRequest multipartHttpServletRequest = null;
    try {
      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
          request.getSession().getServletContext());
      if (commonsMultipartResolver.isMultipart(request)) {
        //取出图片
        handleImage(request,worldImgList);
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

    try {
      WorldImgExecution worldImgExecution = worldImgService.addWorldImg(worldImgList);
      if (worldImgExecution.getState() == WorldImgEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        return  modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", worldImgExecution.getStateInfo());
      }
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
      return modelMap;
    }
    return modelMap;
  }
  @RequestMapping(value = "modifyworldimg",method = RequestMethod.POST)
  private Map<String,Object> modifyWorldImg(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    List<ImageHolder> worldImgList = new ArrayList<ImageHolder>();
    MultipartHttpServletRequest multipartHttpServletRequest = null;
    try {
      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
          request.getSession().getServletContext());
      if (commonsMultipartResolver.isMultipart(request)) {
        //取出图片
        handleImage(request,worldImgList);
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

    try {
      WorldImgExecution worldImgExecution = worldImgService.modifyWorldImg(worldImgList);
      if (worldImgExecution.getState() == WorldImgEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        return  modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", worldImgExecution.getStateInfo());
      }
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
      return modelMap;
    }
    return modelMap;
  }
  /**
   * 取出图片文件
   */
  private void handleImage(HttpServletRequest request,List<ImageHolder> worldImgList) throws IOException {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    // 取出图列表并构建列表对象，最多支持六张图片上传
    for (int i = 0; i < 6; i++) {
      CommonsMultipartFile worldImgFile = (CommonsMultipartFile) multipartRequest
          .getFile("worldImg" + i);
      if (worldImgFile != null ) {
        ImageHolder worldImg = new ImageHolder(worldImgFile.getOriginalFilename(),
            worldImgFile.getInputStream());
        worldImgList.add(worldImg);
      } else {
        // 若取出的第i个图片文件流为空，则终止循环
        break;
      }
    }
  }
  private Map<String,Object> getWorldImgList(){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    WorldImgExecution worldImgExecution =worldImgService.getWorldImgList();
    modelMap.put("success",true);
    modelMap.put("worldImgList",worldImgExecution.getWorldImgList());
    return modelMap;
  }
}
