package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.PPManageDao;
import com.irecssa.mmns.dao.ProductDao;
import com.irecssa.mmns.dao.ProductPropertyDao;
import com.irecssa.mmns.dao.ProductRepertoryDao;
import com.irecssa.mmns.dao.PropertyValueDao;
import com.irecssa.mmns.dao.SPNumDao;
import com.irecssa.mmns.dto.execution.SPNumExecution;
import com.irecssa.mmns.entity.PPManage;
import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.entity.ProductProperty;
import com.irecssa.mmns.entity.ProductRepertory;
import com.irecssa.mmns.entity.PropertyValue;
import com.irecssa.mmns.entity.SPNum;
import com.irecssa.mmns.enums.SPNumEnum;
import com.irecssa.mmns.exceptions.SPNumOperationException;
import com.irecssa.mmns.service.SPNumService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 10:29
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class SPNumServiceImpl implements SPNumService {

  public  static final Logger log = LoggerFactory.getLogger(SPNumServiceImpl.class);
  @Autowired
  private SPNumDao spNumDao;

  @Autowired
  private ProductPropertyDao productPropertyDao;

  @Autowired
  private PPManageDao ppManageDao;
  @Autowired
  private PropertyValueDao propertyValueDao;

  @Autowired
  private ProductRepertoryDao productRepertoryDao;
  @Autowired
  private ProductDao productDao;
  /**
   * 批量删除购物车内商品
   */
  @Transactional
  public SPNumExecution removeSPNum(List<String> spNumIdList) {
    if (spNumIdList != null && spNumIdList.size() > 0) {
      try {
        int result = spNumDao.batchDeleteSPNum(spNumIdList);
        if (result > 0) {
          return new SPNumExecution(SPNumEnum.SUCCESS);
        } else {
          throw new SPNumOperationException("removeSPNum error");
        }
      } catch (Exception e) {
        throw new SPNumOperationException(e.getMessage());
      }
    } else {
      return new SPNumExecution(SPNumEnum.NULL_AUTH_INFO);
    }
  }

  public SPNumExecution getOrderSPNumList(SPNum spNum) {
    List<SPNum> list = spNumDao.querySPNumList(spNum);
  return new SPNumExecution(SPNumEnum.SUCCESS,list);
  }

  @Transactional
  public SPNumExecution addSPNum(SPNum spNum) {
    if(spNum!=null){
      try {
        int result = spNumDao.insertSPNum(spNum);
        if(result>0){
          return new SPNumExecution(SPNumEnum.SUCCESS,spNum);
        }else{
          throw new SPNumOperationException("addSPNum error");
        }
      }catch (Exception e){
        log.debug(e.getMessage());
        throw new SPNumOperationException(e.getMessage());
      }
    }else{
      throw new SPNumOperationException(SPNumEnum.NULL_AUTH_INFO.getStateInfo());
    }

  }

  /**
   * 得到当前购物车列表
   */
  public  List<Map<String, Object>> getSPNumList(SPNum spNum) {
    if (spNum != null) {
      Map<String,Object> map = new HashMap<String,Object>();
      List<SPNum> spNumList = spNumDao.querySPNumList(spNum);
      List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
      for(int i=0;i< spNumList.size();i++){
        list = getOrderProductList(spNumList.get(i));
      }
      return list;
    } else {
      throw new SPNumOperationException("参数为空");
    }
  }

  public SPNumExecution getSPNum(String spNumId) {
    return null;
  }

  /**
   * 获得订单中商品信息
   */
  public List<Map<String, Object>> getOrderProductList(SPNum spNum) {
    List list1 = new ArrayList();
    if (spNum != null) {
      List<SPNum> spNumList = spNumDao.querySPNumList(spNum);
      for (SPNum spNum1 : spNumList) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        String ppManageId = spNum1.getPpManageId();
        String productId = spNum1.getProduct().getProductId();
        PPManage ppManage = ppManageDao.queryPPManageByUnique(ppManageId,productId);
        if (ppManage == null) {
          Product product = productDao.queryProductById(productId);
          ProductRepertory productRepertory = productRepertoryDao.queryProductRepertory(product.getProductRepertory().getProrepId());
          product.setProductRepertory(productRepertory);
          spNum1.setProduct(product);
          List a = new ArrayList();
          map.put("spNum", spNum1);
          map.put("propertyValueList",a);
          list.add(map);
          if(!list1.contains(list)){
            list1.add(list);
          }
        } else {
          Product product = productDao.queryProductById(productId);
          ProductRepertory productRepertory = productRepertoryDao.queryProductRepertory(product.getProductRepertory().getProrepId());
          product.setProductRepertory(productRepertory);
          product.setPromotionMobi(ppManage.getPpmanageMobi());
          product.setProductIntegral(ppManage.getPpManageIntegral());
          spNum1.setProduct(product);
          map.put("spNum",spNum1);
          ppManageId = ppManage.getPpManageId();
          List<ProductProperty> productPropertyList = productPropertyDao.queryProductPropertyByProductIdAndPPManageId(productId, ppManageId);
          List propertyList = new ArrayList();
          String[] pvIds = new String[productPropertyList.size()];
          for (int i=0;i < productPropertyList.size();i++) {
            pvIds[i]=productPropertyList.get(i).getPropertyValue().getPvId();
          }
          List<PropertyValue> propertyValueList = propertyValueDao.getByIds(pvIds);
          map.put("propertyValueList", propertyValueList);
          list.add(map);
          list1.add(list);
        }
      }
      return list1;
    } else {
      return null;
    }
  }

  @Transactional
  public SPNumExecution modifySPNum(SPNum spNum) {
    try {
      int result = spNumDao.updateSPNum(spNum);
      if(result>0){
        return new SPNumExecution(SPNumEnum.SUCCESS,spNum);
      }else {
        throw new SPNumOperationException("modifySPNum error");
      }
      }catch (Exception e){
      throw new SPNumOperationException(e.getMessage());
    }
  }

  public SPNumExecution querySPNumIsExist(SPNum spNum) {
    return new SPNumExecution(SPNumEnum.SUCCESS,spNumDao.querySPNumIsExist(spNum));
  }

}
