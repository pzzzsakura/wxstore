package com.irecssa.mmns.controller.mall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irecssa.mmns.dto.ShoppingDto;
import com.irecssa.mmns.dto.execution.AddressExecution;
import com.irecssa.mmns.dto.execution.ProductOrderExecution;
import com.irecssa.mmns.dto.execution.SPNumExecution;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.Address;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.entity.ProductOrder;
import com.irecssa.mmns.entity.SPNum;
import com.irecssa.mmns.entity.Track;
import com.irecssa.mmns.enums.AddressEnum;
import com.irecssa.mmns.enums.PPManageEnum;
import com.irecssa.mmns.enums.ProductOrderEnum;
import com.irecssa.mmns.enums.SPNumEnum;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.service.AddressService;
import com.irecssa.mmns.service.PPManageService;
import com.irecssa.mmns.service.ProductOrderService;
import com.irecssa.mmns.service.ProductPropertyService;
import com.irecssa.mmns.service.ProductService;
import com.irecssa.mmns.service.SPNumService;
import com.irecssa.mmns.service.WechatAuthService;
import com.irecssa.mmns.util.HttpServletRequestUtil;
import com.irecssa.mmns.util.JacksonUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 23:27
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class ProductOrderController {

  @Autowired
  private ProductPropertyService productPropertyService;
  @Autowired
  private AddressService addressService;
  @Autowired
  private ProductService productService;
  @Autowired
  private PPManageService ppManageService;
  @Autowired
  private WechatAuthService wechatAuthService;
  @Autowired
  private ProductOrderService productOrderService;
  @Autowired
  private SPNumService spNumService;

  /**
   * 结算商品时调用，生成订单
   */
  @RequestMapping(value = "clearing", method = RequestMethod.POST)
  private Map<String, Object> clearing(HttpServletRequest request) throws IOException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    //1、根据传过来的商品参数和数量判断库存，如果库存不够则返回错误信息
    String shoppingListStr = HttpServletRequestUtil.getString(request, "shoppingListStr");
    if (shoppingListStr != null && shoppingListStr != "") {
      ObjectMapper objectMapper = new ObjectMapper();
      List<ShoppingDto> shoppingDtoList = objectMapper.readValue(shoppingListStr,
          JacksonUtil.getCollectionType(objectMapper, List.class, ShoppingDto.class));
      Map<String, Object> map = ppManageService.checkSPNum(shoppingDtoList);
      if (map.get("errCode")!=null&&map.get("errCode").equals(PPManageEnum.STORENULL.getState())) {
        modelMap.put("success", false);
        modelMap.put("errCode", map.get("errCode"));//-1009
        modelMap.put("errMsg", map.get("errMsg"));//库存不足
        return modelMap;
      } else if (map.get("errCode")!=null&&map.get("errCode").equals(PPManageEnum.NULL_AUTH_INFO.getState())) {
        modelMap.put("success", false);
        modelMap.put("errMsg", map.get("errMsg"));
        return modelMap;
      } else {
        String personInfoId = null;
        modelMap.put("totalMobi", map.get("totalMobi"));//商品总价
        modelMap.put("totalIntegral", map.get("totalIntegral"));//商品总积分
        //2、判断是否有默认收货地址
        String openId = (String) request.getSession().getAttribute("openId");
        WechatAuthExecution wechatAuthExecution = wechatAuthService
            .getWechatAuthServiceByOpenId(openId);
        if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
          personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
          AddressExecution addressExecution = addressService.getIsDefaultAddress(personInfoId);
          if (addressExecution.getState() == AddressEnum.SUCCESS.getState()) {
            Address address = addressExecution.getAddress();
            if (address != null) {
              modelMap.put("isDefaultAddress", address);//返回默认地址
            } else {
              modelMap.put("errCode", -1008);//-1008
              modelMap.put("errMsg", "没有默认地址");
            }
          } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", addressExecution.getStateInfo());
            return modelMap;
          }
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
          return modelMap;
        }
        //3、生成初始未支付订单信息，更新相应库存
        Address defaultAddress = (Address) modelMap.get("isDefaultAddress");
        int totalMobi = (Integer) modelMap.get("totalMobi");
        int totalIntegral = (Integer) modelMap.get("totalIntegral");
        Map<String, Object> orderMap = productOrderService
            .addProductOrder(shoppingDtoList, totalMobi, totalIntegral, defaultAddress,
                personInfoId);
        modelMap.put("success", true);
        ProductOrder productOrder = (ProductOrder) orderMap.get("productOrder");

        modelMap.put("productOrder", productOrder);//订单详情
        List<Map<String, Object>> spNumList = (List<Map<String, Object>>) modelMap.get("spNumList");
        modelMap.put("spNumList", spNumList);//里面spNum+propertyList[{name,value}]
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "参数为空");
      return modelMap;
    }
  }

  /**
   * 根据条件查询订单列表
   */
  @RequestMapping(value = "getproductorderlist", method = RequestMethod.POST)
  private Map<String, Object> getProductOrderList(HttpServletRequest request,
      @RequestParam("enableStatus") Integer enableStatus) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      ProductOrder productOrderCondition = new ProductOrder();
      productOrderCondition.setEnableStatus(enableStatus);
      productOrderCondition.setPersonInfoId(personInfoId);
      ProductOrderExecution poe = productOrderService.getProductOrderList(productOrderCondition);

      if (poe.getState() == ProductOrderEnum.SUCCESS.getState()) {
        List orderList = new ArrayList();
        List<ProductOrder> productOrderList = poe.getProductOrderList();
        for (ProductOrder productOrder : productOrderList) {
          List list = new ArrayList();
          String productOrderId = productOrder.getProorderId();
          SPNum spNum = new SPNum();
          spNum.setShoppingCart(0);
          spNum.setProOrderId(productOrderId);
          spNum.setPersonInfoId(personInfoId);
          SPNumExecution spNumExecution = spNumService.getOrderSPNumList(spNum);
          if (spNumExecution.getState() == SPNumEnum.SUCCESS.getState()) {
            String productImg = spNumExecution.getSpNumList().get(0).getProduct().getProductImg();
            list.add(productOrder);
            list.add(productImg);
            orderList.add(list);
            modelMap.put("success", true);
            modelMap.put("orderList", orderList);
            return modelMap;
          } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询订单失败");
            return modelMap;
          }
        }
        modelMap.put("success", true);
        modelMap.put("orderList", orderList);//[[{xx:xx},productImg],...]
        return modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", poe.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 取消订单
   */
  @RequestMapping(value = "cancelproductorder", method = RequestMethod.POST)
  private Map<String, Object> cancelProductOrder(HttpServletRequest request,
      @RequestParam("proorderId") String proorderId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      ProductOrder productOrder = new ProductOrder();
      productOrder.setPersonInfoId(personInfoId);
      productOrder.setEnableStatus(6);//已取消
      productOrder.setProorderId(proorderId);
      try {
        ProductOrderExecution productOrderExecution = productOrderService
            .modifyProductOrder(productOrder);
        if (productOrderExecution.getState() == ProductOrderEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
          modelMap.put("productOrder", productOrderExecution.getProductOrder());
          return modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", productOrderExecution.getStateInfo());
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
  }

  /**
   * 查看订单
   */
  @RequestMapping(value = "getproductorder", method = RequestMethod.POST)
  private Map<String, Object> getProductOrder(HttpServletRequest request,
      @RequestParam("proorderId") String proorderId, @RequestParam("addressId") String addressId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");

   //openId="o22h00XyvxWc96iPA23YOnr9mKgc";
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      //获取订单信息
      ProductOrder productOrder = new ProductOrder();
      productOrder.setPersonInfoId(personInfoId);
      productOrder.setProorderId(proorderId);
      productOrder.setLastEditTime(new Date());
      if(addressId!=null&&addressId!=""&&!"null".equals(addressId)){
        productOrder.setAddressId(addressId);
      }else{
        AddressExecution addressExecution = addressService.getIsDefaultAddress(personInfoId);
        if(addressExecution.getAddress()==null){
          productOrder.setAddressId(null);
        }else{
          productOrder.setAddressId(addressExecution.getAddress().getAddressId());
        }
      }
      ProductOrderExecution productOrderExecution = productOrderService.modifyProductOrder(productOrder);
      if (productOrderExecution.getState() == ProductOrderEnum.SUCCESS.getState()) {
        productOrder = productOrderExecution.getProductOrder();
        modelMap.put("productOrder", productOrder);
        //获取地址信息
        Address address = new Address();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setPersonInfoId(personInfoId);
        address.setAddressId(productOrder.getAddressId());
        address.setPersonInfo(personInfo);
        AddressExecution addressExecution = addressService.getAddress(address);
        if (addressExecution.getState() == AddressEnum.SUCCESS.getState()) {
          modelMap.put("address", addressExecution.getAddress());
          //获取购物商品信息
          SPNum spNum = new SPNum();
          spNum.setShoppingCart(0);
          spNum.setPersonInfoId(personInfoId);
          spNum.setProOrderId(proorderId);
          List<Map<String, Object>> list = spNumService.getOrderProductList(spNum);
          modelMap.put("spNumList", list);
          modelMap.put("success", true);
          return modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", addressExecution.getStateInfo());
          return modelMap;
        }


      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", productOrderExecution.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 去支付
   */
  @RequestMapping(value = "gopay", method = RequestMethod.POST)
  private Map<String, Object> goPay(HttpServletRequest request,
      @RequestParam("proorderId") String proorderId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      //去支付
     ProductOrderExecution productOrderExecution =  productOrderService.goPay(personInfoId,proorderId);
    if(productOrderExecution.getState()==ProductOrderEnum.SUCCESS.getState()){
      modelMap.put("success", true);
      modelMap.put("payedProductOrder", productOrderExecution.getProductOrder());
      return modelMap;
    }else if(productOrderExecution.getState()==ProductOrderEnum.MOBIDECLINE.getState()){
      modelMap.put("success", false);
      modelMap.put("errCode",productOrderExecution.getState());//-2 膜币不足
      modelMap.put("errMsg", productOrderExecution.getStateInfo());
      return modelMap;
    }else{
      modelMap.put("success", false);
      modelMap.put("errCode",productOrderExecution.getState());
      modelMap.put("errMsg", productOrderExecution.getStateInfo());
      return modelMap;
    }
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 商家确认退货
   */
  @RequestMapping(value = "salesreturn", method = RequestMethod.POST)
  private Map<String, Object> salesReturn(HttpServletRequest request,
      @RequestParam("proorderId") String proorderId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      //去退货
      ProductOrderExecution productOrderExecution =  productOrderService.salesReturn(personInfoId,proorderId);
      if(productOrderExecution.getState()==ProductOrderEnum.SUCCESS.getState()){
        modelMap.put("success", true);
        modelMap.put("returnProductOrder", productOrderExecution.getProductOrder());
        return modelMap;
      }else{
        modelMap.put("success", false);
        modelMap.put("errMsg", productOrderExecution.getStateInfo());
        return modelMap;
      }
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 用户退货，审核中状态
   */
  @RequestMapping(value = "salesreturning", method = RequestMethod.POST)
  private Map<String, Object> salesReturning(HttpServletRequest request,
      @RequestParam("proorderId") String proorderId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      //去退货
      ProductOrderExecution productOrderExecution = productOrderService
          .salesReturning(personInfoId, proorderId);
      if (productOrderExecution.getState() == ProductOrderEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        modelMap.put("returningProductOrder", productOrderExecution.getProductOrder());
        return modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", productOrderExecution.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }
  /**
   * 确认收货
   */
  @RequestMapping(value = "sure", method = RequestMethod.POST)
  private Map<String, Object> sure(HttpServletRequest request,
      @RequestParam("proorderId") String proorderId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String openId = (String) request.getSession().getAttribute("openId");
    WechatAuthExecution wechatAuthExecution = wechatAuthService
        .getWechatAuthServiceByOpenId(openId);
    if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
      String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
      //去退货
      ProductOrderExecution productOrderExecution = productOrderService
          .sure(personInfoId, proorderId);
      if (productOrderExecution.getState() == ProductOrderEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        modelMap.put("sureProductOrder", productOrderExecution.getProductOrder());
        return modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", productOrderExecution.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
      return modelMap;
    }
  }
/**
 *
 * 商家确认发货，增加订单物流信息
 */
@RequestMapping(value = "suresend", method = RequestMethod.POST)
private Map<String, Object> sureSend(HttpServletRequest request,
    @RequestParam("proorderId") String proorderId,@RequestParam("com") String com,@RequestParam("trackName") String trackName,
    @RequestParam("num") String num) {
  Map<String, Object> modelMap = new HashMap<String, Object>();
  String openId = (String) request.getSession().getAttribute("openId");
  WechatAuthExecution wechatAuthExecution = wechatAuthService
      .getWechatAuthServiceByOpenId(openId);
  if (wechatAuthExecution.getState() == WechatAuthStateEnum.SUCCESS.getState()) {
    String personInfoId = wechatAuthExecution.getWechatAuth().getPersonInfo().getPersonInfoId();
    //去确认发货
    Track track = new Track();
    track.setProorderId(proorderId);
    track.setCom(com);
    track.setNum(num);
    ProductOrderExecution productOrderExecution =  productOrderService.sureSend(track,personInfoId,proorderId);
    if(productOrderExecution.getState()==ProductOrderEnum.SUCCESS.getState()){
      modelMap.put("success", true);
      modelMap.put("sendProductOrder", productOrderExecution.getProductOrder());
      modelMap.put("track", productOrderExecution.getTrack());
      return modelMap;
    }else{
      modelMap.put("success", false);
      modelMap.put("errMsg", productOrderExecution.getStateInfo());
      return modelMap;
    }
  }else{
    modelMap.put("success", false);
    modelMap.put("errMsg", wechatAuthExecution.getStateInfo());
    return modelMap;
  }
}
}
