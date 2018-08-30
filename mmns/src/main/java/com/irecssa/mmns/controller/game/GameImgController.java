package com.irecssa.mmns.controller.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irecssa.mmns.dto.ImageHolder;
import com.irecssa.mmns.dto.execution.GameImgExecution;
import com.irecssa.mmns.enums.GameImgEnum;
import com.irecssa.mmns.service.GameImgService;
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
 * @datetime: 2017/12/03 00:11
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/game")
public class GameImgController {

  @Autowired
  private GameImgService gameImgService;
  @RequestMapping(value = "addgameimg",method = RequestMethod.POST)
  private Map<String,Object> addGameImg(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    ObjectMapper objectMapper = new ObjectMapper();
    List<ImageHolder> gameImgList = new ArrayList<ImageHolder>();
    MultipartHttpServletRequest multipartHttpServletRequest = null;
    try {
      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
          request.getSession().getServletContext());
      if (commonsMultipartResolver.isMultipart(request)) {
        //取出图片
        handleImage(request,gameImgList);
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
        GameImgExecution gameImgExecution = gameImgService.addGameImg(gameImgList);
        if (gameImgExecution.getState() == GameImgEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
          modelMap.put("gameImgList", gameImgExecution.getGameimgList());
         return  modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", gameImgExecution.getStateInfo());
        }
      } catch (Exception e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        return modelMap;
      }
    return modelMap;
  }

  @RequestMapping(value = "modifygameimg",method = RequestMethod.POST)
  private Map<String,Object> modifyGameImg(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    List<ImageHolder> gameImgList = new ArrayList<ImageHolder>();
    MultipartHttpServletRequest multipartHttpServletRequest = null;
    try {
      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
          request.getSession().getServletContext());
      if (commonsMultipartResolver.isMultipart(request)) {
        //取出图片
        handleImage(request,gameImgList);
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
      GameImgExecution gameImgExecution = gameImgService.modifyGameImg(gameImgList);
      if (gameImgExecution.getState() == GameImgEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        return  modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", gameImgExecution.getStateInfo());
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
  private void handleImage(HttpServletRequest request,List<ImageHolder> gameImgList) throws IOException {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    // 取出图列表并构建列表对象，最多支持六张图片上传
    for (int i = 0; i < 6; i++) {
      CommonsMultipartFile gameImgFile = (CommonsMultipartFile) multipartRequest
          .getFile("gameImg" + i);
      if (gameImgFile != null ) {
        ImageHolder gameImg = new ImageHolder(gameImgFile.getOriginalFilename(),
            gameImgFile.getInputStream());
        gameImgList.add(gameImg);
      } else {
        // 若取出的第i个图片文件流为空，则终止循环
        break;
      }
    }
  }

  private Map<String,Object> getGameImgList(){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    GameImgExecution gameImgExecution = gameImgService.getGameImgList();
    modelMap.put("success",true);
    modelMap.put("gameImgList",gameImgExecution.getGameimgList());
    return modelMap;
  }


}
