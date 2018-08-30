package com.irecssa.mmns.controller.wechat;


import com.alibaba.fastjson.JSONObject;
import com.irecssa.mmns.dto.Button;
import com.irecssa.mmns.dto.Menu;
import com.irecssa.mmns.dto.ViewButton;
import com.irecssa.mmns.util.wechat.SignUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
public class WeiXinCreateMenuTest {

  @Value("${url1}")
  private String url1;
  @Value("${url2}")
  private String url2;
  @Value("${url3}")
  private String url3;
  @Value("${vbname1}")
  private String vbName1;
  @Value("${vbname2}")
  private String vbName2;
  @Value("${vbname3}")
  private String vbName3;

  @RequestMapping(value = "/createmenu", method = RequestMethod.GET)
  public Map<String, Object> wechatMenu() throws ServletException, IOException {

    Map<String, Object> map = new HashMap<String, Object>();
    //创建自定义菜单
    String menu = JSONObject.toJSONString(initMenu());
    int result = SignUtil.createMenu(menu);

    if (result == 0) {
      map.put("success",true);
      map.put("result","创建菜单成功");
      System.out.println("菜单创建成功！");
      return map;
    } else {
      map.put("success",true);
      map.put("result","创建菜单失败");
      System.out.println("菜单创建失败！");
      return map;
    }
  }

  /*
  * 自定义菜单
  */
  public Menu initMenu() throws UnsupportedEncodingException {

    Menu menu = new Menu();

    ViewButton vb1 = new ViewButton();
    vb1.setName(vbName1);
    vb1.setType("view");
    vb1.setUrl(url1);

    ViewButton vb2 = new ViewButton();
    vb2.setName(vbName2);
    vb2.setType("view");
    vb2.setUrl(url2);

    ViewButton vb3 = new ViewButton();
    vb3.setName(vbName3);
    vb3.setType("view");
    vb3.setUrl(url3);

    menu.setButton(new Button[]{vb1, vb2, vb3});

    return menu;
  }
}
