package com.irecssa.mmns.controller.mall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irecssa.mmns.dto.ShoppingDto;
import com.irecssa.mmns.dto.execution.SPNumExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.entity.SPNum;
import com.irecssa.mmns.enums.SPNumEnum;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.service.SPNumService;
import com.irecssa.mmns.service.WechatAuthService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import com.irecssa.mmns.util.JacksonUtil;
import com.irecssa.mmns.util.UUIDUtil;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 11:02
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class SPNumController {

  @Autowired
  private SPNumService spNumService;
  @Autowired
  private WechatAuthService wechatAuthService;

  /**
   * 得到购物车列表
   */
  @RequestMapping(value = "/getshoppingcart", method = RequestMethod.POST)
  private Map<String, Object> getShoppingCart(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      SPNum spNum = new SPNum();
      spNum.setPersonInfoId(wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId());
      spNum.setShoppingCart(1);
      List<Map<String, Object>> lists = spNumService.getSPNumList(spNum);
      modelMap.put("success", true);
      modelMap.put("spNumList", lists);
      return modelMap;
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 添加购物车
   */
  @RequestMapping(value = "/addshoppingcart", method = RequestMethod.POST)
  private Map<String, Object> addShoppingCart(@RequestBody ShoppingDto shoppingDto,HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String personInfoId=null;
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
    if (shoppingDto!=null&&shoppingDto.getProductId()!= null&&shoppingDto.getPpManageId()!=null) {
      SPNum spNum = new SPNum();
      Product product = new Product();
      product.setProductId(shoppingDto.getProductId());
      spNum.setProduct(product);
      spNum.setShoppingCart(1);
      spNum.setPpManageId(shoppingDto.getPpManageId());
      spNum.setSpNumId(UUIDUtil.createUUID());
      spNum.setSpNumNum(shoppingDto.getCheckNum());
      spNum.setCreateTime(new Date());
      spNum.setEnableStatus(1);
      spNum.setPersonInfoId(personInfoId);
      SPNumExecution se;
      SPNumExecution spNumExecution = spNumService.querySPNumIsExist(spNum);
      if(spNumExecution.getState()==SPNumEnum.SUCCESS.getState()){
          if(spNumExecution.getSpNum()!=null){
            try {
              spNumExecution.getSpNum().setSpNumNum(shoppingDto.getCheckNum());
               se =  spNumService.modifySPNum(spNumExecution.getSpNum());
            }catch (Exception e){
              modelMap.put("success", false);
              modelMap.put("errMsg", e.getMessage());
              return modelMap;
            }
          }else{
            try {
               se = spNumService.addSPNum(spNum);
            } catch (Exception e) {
              modelMap.put("success", false);
              modelMap.put("errMsg", e.getMessage());
              return modelMap;
            }
          }
        if (se.getState() == SPNumEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
          modelMap.put("spNum", se.getSpNum());
          return modelMap;
        }else{
          modelMap.put("success", false);
          modelMap.put("errMsg", se.getStateInfo());
          return modelMap;
        }
      }else{
        modelMap.put("success", false);
        modelMap.put("errMsg", spNumExecution.getStateInfo());
        return modelMap;
      }

    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "参数为空");
      return modelMap;
    }
  }


/**
 * 删除购物车内商品
 */
@RequestMapping(value = "/removespnums", method = RequestMethod.POST)
private Map<String, Object> removeSPNums(HttpServletRequest request) throws IOException {
  Map<String, Object> modelMap = new HashMap<String, Object>();
  ObjectMapper objectMapper = new ObjectMapper();
  String spNumIdListStr = HttpServletRequestUtil.getString(request,"spNumIdList");
 List<String> spNumIdList = objectMapper.readValue(spNumIdListStr, JacksonUtil
      .getCollectionType(objectMapper,List.class,String.class));
  if(spNumIdList!=null&&spNumIdList.size()>0){
    try{
      SPNumExecution spNumExecution = spNumService.removeSPNum(spNumIdList);
      if(spNumExecution.getState()==SPNumEnum.SUCCESS.getState()){
        modelMap.put("success", true);
        return modelMap;
      }else{
        modelMap.put("success", false);
        modelMap.put("errMsg", spNumExecution.getStateInfo());
        return modelMap;
      }
    }catch (Exception e){
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
      return modelMap;
    }
  }else{
    modelMap.put("success", false);
    modelMap.put("errMsg", "参数为空");
    return modelMap;
  }
}

}
