package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.ComboDao;
import com.irecssa.mmns.dao.PPManageDao;
import com.irecssa.mmns.dao.ProductPropertyDao;
import com.irecssa.mmns.dao.PropertyValueDao;
import com.irecssa.mmns.dto.execution.ProductPropertyExecution;
import com.irecssa.mmns.entity.Combo;
import com.irecssa.mmns.entity.PPManage;
import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.entity.ProductProperty;
import com.irecssa.mmns.entity.PropertyValue;
import com.irecssa.mmns.enums.ProductPropertyEnum;
import com.irecssa.mmns.exceptions.ProductPropertyoperationException;
import com.irecssa.mmns.service.ProductPropertyService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 15:22
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {

  @Autowired
  private ComboDao comboDao;
  @Autowired
  private ProductPropertyDao productPropertyDao;
  @Autowired
  private PPManageDao ppManageDao;
  @Autowired
  private PropertyValueDao propertyValueDao;

  /**
   * 得到商品相关规格类别及详情
   */
  public List getProductPropertyByProductId(String productId) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    if (productId != null) {
      List<ProductProperty> productPropertyList = productPropertyDao
          .queryProductPropertyByProductId(productId);
      if (productPropertyList != null && productPropertyList.size() > 0) {
        String[]  pvIds = new String[productPropertyList.size()];
       //取出当前产品的属性表主键
        for (int i=0; i<productPropertyList.size();i++) {
          String pvId = productPropertyList.get(i).getPropertyValue().getPvId();
          pvIds[i]=pvId;
        }
       //然后根据属性表主键批量查询出属性和值，并按属性id升排序
        List<PropertyValue> propertyValueList = propertyValueDao.getByIds(pvIds);
        //依次取出属性和对应id,放入 propertyNameList
        List<List> propertyNList = new ArrayList();//参数列表
        for(PropertyValue propertyValue:propertyValueList){
          List nameList = new ArrayList();
          nameList.add(propertyValue.getProperty().getPropertyName());
          nameList.add(propertyValue.getProperty().getId());
          if(propertyNList!=null&&propertyNList.size()>0){
            if(propertyNList.contains(nameList))continue;
          }
          propertyNList.add(nameList);//【【name1,id1】【name2,id2】】
        }
        for(int j=0;j<propertyNList.size();j++) {
          List propertyVList = new ArrayList();//参数属性列表
          for (PropertyValue propertyValue : propertyValueList) {
            if (propertyNList.get(j).get(0).equals(propertyValue.getProperty().getPropertyName())) {
              List list1 = new ArrayList();
              list1.add(propertyValue.getValue());
              list1.add(propertyValue.getVid());
              propertyVList.add(list1);
            }
          }
          //将对应属性值及id加入数组 成为【【name1,id1,[[value1,id1][value2,id2]]】】
          propertyNList.get(j).add(propertyVList);
        }
        return propertyNList;
      } else {
       return null;
      }
    } else {
      throw new ProductPropertyoperationException("getProductPropertyByProductId :productId为空");
    }
  }

  /**
   * 得到商品套餐详情类别
   */
  public List<Map<String, Object>> getComboProductList(String productId) {
    if (productId != null) {
      List<Map<String, Object>> comboList = new ArrayList<Map<String, Object>>();
      Product aProduct = new Product();
      aProduct.setProductId(productId);
      Combo aCombo = new Combo();
      aCombo.setaProduct(aProduct);
      List<Combo> aComboList = comboDao.queryComboByProductId(aCombo);
      if (aComboList != null && aComboList.size() > 0) {
        for (Combo combo : aComboList) {
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("comboId", combo.getComboId());
          map.put("comboName", combo.getComboName());
          comboList.add(map);
        }
      }
      Product bProduct = new Product();
      bProduct.setProductId(productId);
      Combo bCombo = new Combo();
      bCombo.setbProduct(bProduct);
      List<Combo> bComboList = comboDao.queryComboByProductId(bCombo);
      addNewComboList(bComboList, comboList);

      Product cProduct = new Product();
      cProduct.setProductId(productId);
      Combo cCombo = new Combo();
      cCombo.setcProduct(cProduct);
      List<Combo> cComboList = comboDao.queryComboByProductId(cCombo);
      addNewComboList(cComboList, comboList);
      return comboList;
    } else {
      throw new ProductPropertyoperationException(
          "getComboProductList error" + ProductPropertyEnum.NULL_AUTH_INFO);
    }
  }

//  /**
//   * 查寻参数组合对应价格
//   */
//  public PPManageExecution queryProductPropertyByProductIdAndPropertyValueAndPropertyName(
//      String productId, List<String> propertyValueList, List<String> propertyNameList) {
//    List list = new ArrayList();
//    for (int i = 0; i < propertyNameList.size(); i++) {
//      List perList = new ArrayList();
//      List<ProductProperty> productPropertyList = new ArrayList<ProductProperty>();
//      productPropertyList = productPropertyDao
//          .queryProductPropertyByProductIdAndPropertyValueAndPropertyName(productId,
//              propertyValueList.get(i), propertyNameList.get(i));
//      for(ProductProperty productProperty:productPropertyList ){
//        perList.add(productProperty.getPpManageId());
//      }
//      list.add(perList);
//    }
//    List firstList = null;
//    for (int j = 1; j < propertyNameList.size(); j++) {
//      List empList = (List) list.get(j);
//        firstList = (List) list.get(0);
//        for(int m=0;m<firstList.size();m++){
//          if(!empList.contains(firstList.get(m))){
//            firstList.remove(m);
//          }
//        }
//
//      }
//    String ppManageId = (String) firstList.get(0);
//      PPManage ppManage = ppManageDao.queryPPManageById(ppManageId, productId);
//      if (ppManage != null) {
//        return new PPManageExecution(PPManageEnum.SUCCESS, ppManage);
//      } else {
//        return new PPManageExecution(PPManageEnum.NULLOFRESULT);
//      }
//  }

  /**
   * 给商品增加多对参数
   */
  @Transactional
  public ProductPropertyExecution addProductPropertyList(List<PropertyValue> propertyValueList,
      PPManage ppManage) {
    List list = new ArrayList();
    if (propertyValueList != null && propertyValueList.size() > 0) {
      StringBuffer str = new StringBuffer();
      //形成一种组合

      ppManage.setPpManageId(UUIDUtil.createUUID());
      for (PropertyValue propertyValue : propertyValueList) {
        propertyValue.setPvId(UUIDUtil.createUUID());
        //属性联系表

        ProductProperty productProperty = new ProductProperty();
        productProperty.setProductId(ppManage.getProductId());
        productProperty.setPpManageId(ppManage.getPpManageId());
        productProperty.setPpId(UUIDUtil.createUUID());
        productProperty.setCreateTime(new Date());
        productProperty.setLastEditTime(new Date());
        productProperty.setPropertyValue(propertyValue);
        String id1 = String.valueOf(propertyValue.getProperty().getId());
        String id2 = String.valueOf(propertyValue.getVid());
        str.append(id1 + id2);
        list.add(productProperty);
      }
      try {
        productPropertyDao.insertProductProperty(list);
        //形成name1+value1+name2+value2的数串
        ppManage.setPpUnique(str.toString());
        ppManageDao.insertPPManage(ppManage);
        return new ProductPropertyExecution(ProductPropertyEnum.SUCCESS);
      } catch (Exception e) {
        throw new ProductPropertyoperationException("插入参数失败" + e.getMessage());
      }
    } else {
      return new ProductPropertyExecution(ProductPropertyEnum.NULL_AUTH_INFO);
    }
  }

  /**
   * 修改商品规格参数
   */
  @Transactional
  public ProductPropertyExecution modifyProductProperty(ProductProperty productProperty) {
    return null;
  }


  private void addNewComboList(List<Combo> childComboList, List<Map<String, Object>> comboList) {
    if (childComboList != null && childComboList.size() > 0) {
      for (Combo combo : childComboList) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (comboList != null && comboList.size() > 0) {
          for (int i = 0; i < comboList.size(); i++) {
            if (comboList.get(i).get("comboId").equals(combo.getComboId())) {
              comboList.remove(i);
            }
          }
        }
        map.put("comboId", combo.getComboId());
        map.put("comboName", combo.getComboName());
        comboList.add(map);
      }
    }
  }
}
