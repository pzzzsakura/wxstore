package com.irecssa.mmns.controller.mall;

import com.irecssa.mmns.entity.Track;
import com.irecssa.mmns.service.TrackService;
import com.irecssa.mmns.util.KdniaoTrackQueryAPI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/29 00:08
 * @desc: 物流信息查询
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("/mall")
public class TrackQueryController {

  @Value("${trackkey}")
  private String key;
  @Autowired
  private TrackService trackService;

  @RequestMapping(value = "/querytrack", method = RequestMethod.GET)
  private Map<String, Object> queryTrack(@RequestParam("proorderId") String proorderId,
      HttpServletRequest request) throws Exception {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    if (proorderId != null) {
      Track track = trackService.queryTrack(proorderId);
      if (track != null) {
        List list = KdniaoTrackQueryAPI.getLogisticInfo(track.getCom(), track.getNum());
        modelMap.put("success", true);
        modelMap.put("list", list);
        modelMap.put("track", track);
        return modelMap;
      } else {
        modelMap.put("success", false);
        modelMap.put("errCode", "-1");
        modelMap.put("errMsg", "暂无此物流信息");
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "参数为空");
      return modelMap;
    }
  }
}
//    String url = "http://api.kuaidi100.com/api?id="+key+"&com="+trackDto.getCom()+"&nu="+trackDto.getNu()+"&show=1&muti=1&order=desc";
//    String result = WechatUtil.httpsRequest(url,"GET","");
//    modelMap = WechatUtil.xmlToMap(result);
  // modelMap.put("list",list);

//    {
//      "message":"ok","status":"1","state":"3","data":[
//      {"time":"2012-07-07 13:35:14","context":"客户已签收"},
//      {"time":"2012-07-07 09:10:10","context":"离开[北京石景山营业厅]派送中，递送员[温]，电话[]"},
//      {"time":"2012-07-06 19:46:38","context":"到达[北京石景山营业厅]"},
//      {"time":"2012-07-06 15:22:32","context":"离开[北京石景山营业厅]派送中，递送员[温]，电话[]"},
//      {"time":"2012-07-06 15:05:00","context":"到达[北京石景山营业厅]"},
//      {"time":"2012-07-06 13:37:52","context":"离开[北京_同城中转站]发往[北京石景山营业厅]"},
//      {"time":"2012-07-06 12:54:41","context":"到达[北京_同城中转站]"},
//      {"time":"2012-07-06 11:11:03","context":"离开[北京运转中心驻站班组] 发往[北京_同城中转站]"},
//      {"time":"2012-07-06 10:43:21","context":"到达[北京运转中心驻站班组]"},
//      {"time":"2012-07-05 21:18:53","context":"离开[福建_厦门支公司] 发往 [北京运转中心_航空]"},
//      {"time":"2012-07-05 20:07:27","context":"已取件，到达 [福建_厦门支公司]"}
//]}
