package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.PPManageDao;
import com.irecssa.mmns.dao.ProductDao;
import com.irecssa.mmns.dao.ProductRepertoryDao;
import com.irecssa.mmns.dao.SPNumDao;
import com.irecssa.mmns.dto.ShoppingDto;
import com.irecssa.mmns.entity.PPManage;
import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.entity.ProductRepertory;
import com.irecssa.mmns.enums.PPManageEnum;
import com.irecssa.mmns.exceptions.PPManageOperationException;
import com.irecssa.mmns.service.PPManageService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/26 10:02
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class PPManageServiceImpl implements PPManageService {

  @Autowired
  private PPManageDao ppManageDao;
  @Autowired
  private ProductDao productDao;
  @Autowired
  private SPNumDao spNumDao;
  @Autowired
  private ProductRepertoryDao productRepertoryDao;

  /**
   * 检查商品库存
   */
  public Map<String, Object> checkSPNum(List<ShoppingDto> shoppingDtoList) {
    Map<String, Object> map = new HashMap<String, Object>();
    int totalMobi = 0;//总价
    int totalIntegral = 0;//总积分
    if (shoppingDtoList != null && shoppingDtoList.size() > 0) {
      //判断库存
      for (ShoppingDto shoppingDto : shoppingDtoList) {
        String ppManageId = shoppingDto.getPpManageId();
        Integer checkNum = shoppingDto.getCheckNum();
        String productId = shoppingDto.getProductId();
        if (ppManageId != null && ppManageId != "") {  //这里时unique
          PPManage ppManage = ppManageDao.queryPPManageByUnique(ppManageId, productId);
          if (ppManage != null && ppManage.getPpManageNum() < checkNum) {
            Product product = productDao.queryProductById(productId);
            map.put("errCode", PPManageEnum.STORENULL.getState());
            map.put("errMsg", product.getProductName() + PPManageEnum.STORENULL.getStateInfo());
            return map;
          } else {
            int mobi = ppManage.getPpmanageMobi();
            int integral = ppManage.getPpManageIntegral();
            totalMobi += mobi * checkNum;
            totalIntegral += integral * checkNum;
          }
        } else {
          Product product = productDao.queryProductById(productId);
          if (product != null) {
            String prorepId = product.getProductRepertory().getProrepId();
            ProductRepertory productRepertory = productRepertoryDao.queryProductRepertory(prorepId);
            if (productRepertory.getCurrentNum() < checkNum) {
              map.put("errCode", PPManageEnum.STORENULL.getState());
              map.put("errMsg", product.getProductName() + PPManageEnum.STORENULL.getStateInfo());
              return map;
            } else {
              if (product.getPromotionMobi() == null || product.getPromotionMobi() == 0) {
                int mobi = product.getNormalMobi();
                int integral = product.getProductIntegral();
                totalMobi += mobi * checkNum;
                totalIntegral += integral * checkNum;
              } else {
                int mobi = product.getPromotionMobi();
                int integral = product.getProductIntegral();
                totalIntegral += integral * checkNum;
                totalMobi += mobi * checkNum;
              }

            }
          } else {
            throw new PPManageOperationException("checkSPNum获取商品详情失败");
          }
        }
      }
      map.put("totalIntegral", totalIntegral);
      map.put("totalMobi", totalMobi);
      return map;
    } else {
      map.put("errCode", PPManageEnum.NULL_AUTH_INFO.getState());
      map.put("errMsg", PPManageEnum.NULL_AUTH_INFO.getStateInfo());
      return map;
    }
  }

  /**
   * 得到组合列表
   */
  public List<Map<String, Object>> getPPManageList(String productId) {
    List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
    List<PPManage> list = ppManageDao.queryPPManageList(productId);
    for (PPManage ppManage : list) {
      List proList = new ArrayList();
      proList.add(ppManage.getPpManageNum());
      proList.add(ppManage.getPpmanageMobi());
      proList.add(ppManage.getPpManageIntegral());
      Map<String, Object> map = new HashMap<String, Object>();
      map.put(ppManage.getPpUnique(), proList);
      returnlist.add(map);
    }
    return returnlist;
  }

  public PPManage queryPPManageByUnique(String ppUnique, String productId) {
    return ppManageDao.queryPPManageByUnique(ppUnique, productId);
  }
}
