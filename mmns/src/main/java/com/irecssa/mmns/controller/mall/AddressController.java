package com.irecssa.mmns.controller.mall;

import com.irecssa.mmns.dto.execution.AddressExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.Address;
import com.irecssa.mmns.enums.AddressEnum;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.service.AddressService;
import com.irecssa.mmns.service.WechatAuthService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 14:47
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class AddressController {

  @Autowired
  private AddressService addressService;
  @Autowired
  private WechatAuthService wechatAuthService;

  /**
   * 添加收货地址
   */
  @RequestMapping(value = "addaddress",method = RequestMethod.POST)
  private Map<String, Object> addAddress(HttpServletRequest request, @RequestBody Address address) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    if (openId != null && openId != "") {
      WechatAuthExecution wechatAuthExecution = wechatAuthService
          .getWechatAuthServiceByOpenId(openId);
      if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
        AddressExecution addressExecution;
        try {
          addressExecution = addressService
              .addAddress(address, wechatAuthExecution.getWechatAuth().getPersonInfo());
          if (addressExecution.getState() == AddressEnum.SUCCESS.getState()) {

            modelMap.put("success", true);
            modelMap.put("address", addressExecution.getAddress());
            return modelMap;
          } else {
            modelMap.put("success", false);
            modelMap.put("address", addressExecution.getStateInfo());
            return modelMap;
          }
        } catch (Exception e) {
          modelMap.put("success", false);
          modelMap.put("errMsg", e.getMessage());
          return modelMap;
        }
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请先关注公众号");
      return modelMap;
    }
  }

  /**
   * 得到所有收货地址
   * @param request
   * @return
   */
  @RequestMapping(value = "getaddresslist", method = RequestMethod.POST)
  private Map<String, Object> getAddressList(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    if (openId != null && openId != "") {
      WechatAuthExecution wechatAuthExecution = wechatAuthService
          .getWechatAuthServiceByOpenId(openId);
      if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
        AddressExecution addressExecution = addressService
            .getAddressList(wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId());
        modelMap.put("success", true);
        modelMap.put("addressList", addressExecution.getAddressList());
        return modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请先关注公众号");
      return modelMap;
    }
  }

  /**
   * 修改默认收获地址
   * @param request
   * @return
   */
  @RequestMapping(value = "modifyisdefaultaddress", method = RequestMethod.POST)
  private Map<String, Object> modifyIsDefaultAddress(@RequestParam("addressId")String addressId, HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    if(openId!=null&&openId!=""){
      WechatAuthExecution wechatAuthExecution = wechatAuthService
          .getWechatAuthServiceByOpenId(openId);
      if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
        try {
          AddressExecution addressExecution = addressService.modifyIsDefault(addressId,wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId());
       if(addressExecution.getState()==AddressEnum.SUCCESS.getState()){
         modelMap.put("success", true);
         //modelMap.put("addressList", addressExecution.getAddressList());
         return modelMap;
       }else{
         modelMap.put("success", false);
         modelMap.put("errMsg", addressExecution.getStateInfo());
         return modelMap;
       }
        }catch(Exception e){
          modelMap.put("success", false);
          modelMap.put("errMsg", e.getMessage());
          return modelMap;
        }

      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
        return modelMap;
      }

    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", "请先关注公众号");
      return modelMap;
    }
  }

  /**
   * 编辑当前地址信息
   */
  @RequestMapping(value = "modifyaddress", method = RequestMethod.POST)
  private Map<String, Object> modifyAddress(HttpServletRequest request,@RequestBody Address address) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    if (openId != null && openId != "") {
      WechatAuthExecution wechatAuthExecution = wechatAuthService
          .getWechatAuthServiceByOpenId(openId);
      if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
        try {
          AddressExecution addressExecution = addressService.modifyAddress(address);
          if (addressExecution.getState() == AddressEnum.SUCCESS.getState()) {
            if (address.getIsDefault() == 1) {
              addressService.modifyIsDefault(address.getAddressId(),
                  wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId());
            }
            modelMap.put("success", true);
            modelMap.put("address", address);
            return modelMap;
          } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", addressExecution.getStateInfo());
            return modelMap;
          }
        } catch (Exception e) {
          modelMap.put("success", false);
          modelMap.put("errMsg", e.getMessage());
          return modelMap;
        }

      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
        return modelMap;
      }
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", "请先关注公众号");
      return modelMap;
    }

  }
  /**
   * 删除当前收货地址
   */
  @RequestMapping(value = "removeaddress", method = RequestMethod.POST)
  private Map<String, Object> removeAddress(HttpServletRequest request,@RequestParam("addressId") String addressId){
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    if(openId!=null&&openId!=""){
      WechatAuthExecution wechatAuthExecution = wechatAuthService
          .getWechatAuthServiceByOpenId(openId);
      if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
        try {
          AddressExecution addressExecution = addressService.removeAddress(addressId,wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId());
          if(addressExecution.getState()==AddressEnum.SUCCESS.getState()){
            modelMap.put("success", true);
            return modelMap;
          }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", addressExecution.getStateInfo());
            return modelMap;
          }
        }catch(Exception e){
          modelMap.put("success", false);
          modelMap.put("errMsg", e.getMessage());
          return modelMap;
        }

      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
        return modelMap;
      }

    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", "请先关注公众号");
      return modelMap;
    }
  }

  /**
   *  查找当前默认收获地址
   */
  @RequestMapping(value = "getaddress", method = RequestMethod.POST)
  private Map<String, Object> getAddress(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    if (openId != null && openId != "") {
      WechatAuthExecution wechatAuthExecution = wechatAuthService
          .getWechatAuthServiceByOpenId(openId);
      if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
        AddressExecution addressExecution = addressService
            .getIsDefaultAddress(wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId());
        modelMap.put("success", true);
        modelMap.put("address", addressExecution.getAddress());
        return modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请先关注公众号");
      return modelMap;
    }
  }
  /**
   *  查找当前收获地址
   */
  @RequestMapping(value = "getcurrentaddress", method = RequestMethod.POST)
  private Map<String, Object> getCurrentAddress(HttpServletRequest request,@RequestParam("addressId") String addressId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    if (openId != null && openId != "") {
      WechatAuthExecution wechatAuthExecution = wechatAuthService
          .getWechatAuthServiceByOpenId(openId);
      if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
        Address address = new Address();
        address.setPersonInfo(wechatAuthExecution.getWechatAuth().getPersonInfo());
        address.setAddressId(addressId);
        AddressExecution addressExecution = addressService
            .getAddress(address);
        modelMap.put("success", true);
        modelMap.put("address", addressExecution.getAddress());
        return modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请先关注公众号");
      return modelMap;
    }
  }
}