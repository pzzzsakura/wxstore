package com.irecssa.mmns.controller.mall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/01 12:10
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Controller
@RequestMapping("/mall")
public class MallController {

  @RequestMapping("/myshoppingcart")
  private String myshoppingcart(){
    return "mall/myshoppingcart";
  }
  @RequestMapping("/addshippingaddress")
  private String addshippingaddress(){
    return "mall/addshippingaddress";
  }
  @RequestMapping("/girlhouse")
  private String girlhouse(){
    return "mall/girlhouse";
  }
  @RequestMapping("/girlmain")
  private String girlmain(){
    return "mall/girlmain";
  }
  @RequestMapping("/girltreasury")
  private String girltreasury(){
    return "mall/girltreasury";
  }
  @RequestMapping("/guidance")
  private String guidance(){
    return "mall/guidance";
  }
  @RequestMapping("/loopactivitydetail")
  private String loopactivitydetail(){
    return "mall/loopactivitydetail";
  }
  @RequestMapping("/myorders")
  private String myorders(){
    return "mall/myorders";
  }
  @RequestMapping("/mypay")
  private String mypay(){
    return "mall/mypay";
  }
  @RequestMapping("/myshippingaddress")
  private String myshippingaddress(){
    return "mall/myshippingaddress";
  }
  @RequestMapping("/nopayorderdetail")
  private String nopayorderdetail(){
    return "mall/nopayorderdetail";
  }
  @RequestMapping("/orderdetail")
  private String orderdetail(){
    return "mall/orderdetail";
  }
  @RequestMapping("/productdetail")
  private String productdetail(){
    return "mall/productdetail";
  }
  @RequestMapping("/tripaddress")
  private String tripaddress(){
    return "mall/tripaddress";
  }
  @RequestMapping("/search")
  private String search(){
    return "mall/search";
  }
  @RequestMapping("/girlword")
  private String girlword(){
    return "mall/girlword";
  }
}
