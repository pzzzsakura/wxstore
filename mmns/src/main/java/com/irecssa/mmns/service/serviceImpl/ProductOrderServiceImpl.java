package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.PPManageDao;
import com.irecssa.mmns.dao.PersonInfoDao;
import com.irecssa.mmns.dao.ProductDao;
import com.irecssa.mmns.dao.ProductOrderDao;
import com.irecssa.mmns.dao.ProductPropertyDao;
import com.irecssa.mmns.dao.ProductRepertoryDao;
import com.irecssa.mmns.dao.PropertyValueDao;
import com.irecssa.mmns.dao.SPNumDao;
import com.irecssa.mmns.dao.TrackDao;
import com.irecssa.mmns.dto.ShoppingDto;
import com.irecssa.mmns.dto.execution.ProductOrderExecution;
import com.irecssa.mmns.entity.Address;
import com.irecssa.mmns.entity.PPManage;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.entity.ProductOrder;
import com.irecssa.mmns.entity.ProductProperty;
import com.irecssa.mmns.entity.ProductRepertory;
import com.irecssa.mmns.entity.PropertyValue;
import com.irecssa.mmns.entity.SPNum;
import com.irecssa.mmns.entity.Track;
import com.irecssa.mmns.enums.ProductOrderEnum;
import com.irecssa.mmns.exceptions.PPManageOperationException;
import com.irecssa.mmns.exceptions.ProductOrderException;
import com.irecssa.mmns.exceptions.ProductPropertyoperationException;
import com.irecssa.mmns.exceptions.SPNumOperationException;
import com.irecssa.mmns.exceptions.TrackExxception;
import com.irecssa.mmns.service.ProductOrderService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 18:34
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

  @Value("${acceptername}")
  private String ACCEPTERNAME;
  @Value("${desc1}")
  private String DESC1;
  @Value("${expressfee}")
  private String EXPRESSFEE;
  @Value("${freeexpress}")
  private String FREEEXPRESS;
  @Autowired
  private ProductOrderDao productOrderDao;
  @Autowired
  private SPNumDao spNumDao;
  @Autowired
  private ProductPropertyDao productPropertyDao;
  @Autowired
  private PPManageDao ppManageDao;
  @Autowired
  private ProductDao productDao;
  @Autowired
  private ProductRepertoryDao productRepertoryDao;
  @Autowired
  private PersonInfoDao personInfoDao;
  @Autowired
  private TrackDao trackDao;
  @Autowired
  private PropertyValueDao propertyValueDao;

  @Transactional
  public Map<String, Object> addProductOrder(List<ShoppingDto> shoppingDtoList, int totalMobi,
      int totalIntegral, Address address, String personInfoId) {
    Map<String, Object> map = new HashMap<String, Object>();
    ProductOrder productOrder = new ProductOrder();
    productOrder.setProorderId(UUIDUtil.createUUID());
    productOrder.setAccepter(ACCEPTERNAME);//收款方
    productOrder.setCreateTime(new Date());
    productOrder.setProductIntegral(totalIntegral);
    productOrder.setEnableStatus(1);//设置初始状态为未支付。之后更新状态
    int expressFee = 0;
    if (totalMobi < Integer.valueOf(FREEEXPRESS)) {//若超过指定价格则免除快递费
      expressFee = Integer.valueOf(EXPRESSFEE);
    }
    productOrder.setExpressFee(expressFee);
//    if (address != null) {  //若默认地址不为空则添加，否则为空，之后更新
//      productOrder.setAddressId(address.getAddressId());
//    }
    productOrder.setPersonInfoId(personInfoId);
    productOrder.setProorderNumber(UUIDUtil.createOrderNum());//订单编号，6+6+时间戳
    productOrder.setTotalMobi(totalMobi + expressFee);//订单总价
    productOrder.setProorderDesc(DESC1);//订单描述
    productOrder.setProductMobi(totalMobi);
    productOrder.setLastEditTime(new Date());
    //直接购买，新增购物车
    if (shoppingDtoList.get(0).getSpNumId() == null) {
      insertSPNum(shoppingDtoList, productOrder.getProorderId(), personInfoId);
    }
    //得到刚刚下单的商品列表，商品信息数量和所选参数
    List<List> spNumList = updateSPNum(shoppingDtoList,
        productOrder.getProorderId());
    map.put("spNumList", spNumList);
    //生成订单，减少库存
    try {
      int result = productOrderDao.insertProductOrder(productOrder);
      if (result > 0) {
        //减少相应库存
        updateRepertory(shoppingDtoList);
        map.put("productOrder", productOrder);
        return map;
      } else {
        throw new ProductOrderException("生成订单失败");
      }
    } catch (Exception e) {
      throw new ProductOrderException("生成订单失败：" + e.getMessage());
    }

  }

  /**
   * 商家确认发货
   */
  public ProductOrderExecution sureSend(Track track,String personInfoId, String proorderId) {
    ProductOrder productOrder = new ProductOrder();
    productOrder.setPersonInfoId(personInfoId);
    productOrder.setProorderId(proorderId);
    productOrder.setEnableStatus(2);//代发货
    productOrder = productOrderDao.queryProductOrder(productOrder);
    int mobi = productOrder.getTotalMobi();
    int integral = productOrder.getProductIntegral();
    PersonInfo personInfo = personInfoDao.queryPersonInfo(personInfoId);
    personInfo.setMobi(personInfo.getMobi() + mobi);
    personInfo.setShopIntegral(-integral);
    try {
      int result = personInfoDao.updatePersonInfo(personInfo);
      if (result > 0) {
        productOrder.setEnableStatus(3);//已发货
        try {
          int effectedNum = productOrderDao.updateProductOrder(productOrder);
          if (effectedNum > 0) {
            track.setTrackId(UUIDUtil.createUUID());
            try {
              trackDao.insertTrack(track);
            }catch (Exception e){
              throw new TrackExxception("增加物流信息失败 insertTrack error");
            }
            return new ProductOrderExecution(ProductOrderEnum.SUCCESS, productOrder,track);
          } else {
            throw new ProductOrderException("增加物流信息失败 updateProductOrder error");
          }
        } catch (Exception e) {
          throw new ProductOrderException("增加物流信息失败 updatePersonInfo error" + e.getMessage());
        }
      } else {
        throw new ProductOrderException("增加物流信息失败 updatePersonInfo error");
      }
    } catch (Exception e) {
      throw new ProductOrderException("增加物流信息失败 updatePersonInfo error" + e.getMessage());
    }
  }

  //按条件获取订单列表
  public ProductOrderExecution getProductOrderList(ProductOrder productOrderCondition) {
    if (productOrderCondition != null) {
      return new ProductOrderExecution(ProductOrderEnum.SUCCESS,
          productOrderDao.queryProductOrderList(productOrderCondition));
    } else {
      return new ProductOrderExecution(ProductOrderEnum.NULL_AUTH_INFO);
    }
  }

  /**
   * 去支付，判断膜币后，同时改变更新
   */
  @Transactional
  public ProductOrderExecution goPay(String personInfoId, String proorderId) {
    ProductOrder productOrder = new ProductOrder();
    productOrder.setPersonInfoId(personInfoId);
    productOrder.setProorderId(proorderId);
    productOrder.setEnableStatus(1);//未支付
    productOrder = productOrderDao.queryProductOrder(productOrder);
    int mobi = productOrder.getTotalMobi();
    int integral = productOrder.getProductIntegral();
    PersonInfo personInfo = personInfoDao.queryPersonInfo(personInfoId);
    if (personInfo.getMobi() >= mobi) {
      personInfo.setMobi(personInfo.getMobi() - mobi);
      personInfo.setShopIntegral(integral);
      try {
        int result = personInfoDao.updatePersonInfo(personInfo);
        if (result > 0) {
          productOrder.setEnableStatus(2);//已支付
          try {
            int effectedNum = productOrderDao.updateProductOrder(productOrder);
            if (effectedNum > 0) {
              return new ProductOrderExecution(ProductOrderEnum.SUCCESS, productOrder);
            } else {
              throw new ProductOrderException("支付失败 updatePersonInfo error");
            }
          } catch (Exception e) {
            throw new ProductOrderException("支付失败 updatePersonInfo error" + e.getMessage());
          }
        } else {
          throw new ProductOrderException("支付失败 updatePersonInfo error");
        }
      } catch (Exception e) {
        throw new ProductOrderException("支付失败 updatePersonInfo error" + e.getMessage());
      }
    } else {
      return new ProductOrderExecution(ProductOrderEnum.MOBIDECLINE);
    }
  }

  /**
   * 更新状态
   */
  @Transactional
  public ProductOrderExecution modifyProductOrder(ProductOrder productOrder) {
    if (productOrder != null) {
      try {
        int result = productOrderDao.updateProductOrder(productOrder);
        if (result > 0) {
          return new ProductOrderExecution(ProductOrderEnum.SUCCESS, productOrderDao.queryProductOrder(productOrder));
        } else {
          throw new ProductOrderException("modifyProductOrder失败");
        }
      } catch (Exception e) {
        throw new ProductOrderException(e.getMessage());
      }
    } else {
      throw new ProductOrderException("modifyProductOrder失败：");
    }
  }

  /**
   * 退货，返回膜币以及商品库存
   */

  @Transactional
  public ProductOrderExecution salesReturn(String personInfoId, String proorderId) {
    ProductOrder productOrder = new ProductOrder();
    productOrder.setPersonInfoId(personInfoId);
    productOrder.setProorderId(proorderId);
    productOrder.setEnableStatus(7);//未支付
    productOrder = productOrderDao.queryProductOrder(productOrder);
    int mobi = productOrder.getTotalMobi();
    int integral = productOrder.getProductIntegral();
    PersonInfo personInfo = personInfoDao.queryPersonInfo(personInfoId);
    personInfo.setMobi(personInfo.getMobi() + mobi);
    personInfo.setShopIntegral(-integral);
    try {
      int result = personInfoDao.updatePersonInfo(personInfo);
      if (result > 0) {
        productOrder.setEnableStatus(8);//已退货
        try {
          int effectedNum = productOrderDao.updateProductOrder(productOrder);
          if (effectedNum > 0) {
            SPNum spNum = new SPNum();
            spNum.setProOrderId(proorderId);
            spNum.setPersonInfoId(personInfoId);
            spNum.setShoppingCart(0);
            List<SPNum> spNumList = spNumDao.querySPNumList(spNum);
            for (SPNum spNum1 : spNumList) {
              int num = spNum1.getSpNumNum();
              if (spNum1.getPpManageId() != null) {
                PPManage ppManage = new PPManage();
                ppManage.setPpManageId(spNum1.getPpManageId());
                ppManage.setPpManageNum(-num);
                try {
                  ppManageDao.updatePPManage(ppManage);
                } catch (Exception e) {
                  throw new ProductOrderException("退货失败 updatePPManage error");
                }
              } else {
                ProductRepertory productRepertory = new ProductRepertory();
                productRepertory.setCurrentNum(-num);
                productRepertory
                    .setProrepId(spNum1.getProduct().getProductRepertory().getProrepId());
                try {
                  productRepertoryDao.updateProductRepertory(productRepertory);
                } catch (Exception e) {
                  throw new ProductOrderException("退货失败 updateProductRepertory error");

                }
              }
            }
            return new ProductOrderExecution(ProductOrderEnum.SUCCESS, productOrder);
          } else {
            throw new ProductOrderException("退货失败 updateProductOrder error");
          }
        } catch (Exception e) {
          throw new ProductOrderException("退货失败 updatePersonInfo error" + e.getMessage());
        }
      } else {
        throw new ProductOrderException("退货失败 updatePersonInfo error");
      }
    } catch (Exception e) {
      throw new ProductOrderException("退货失败 updatePersonInfo error" + e.getMessage());
    }

  }

  /**
   * 订单审核中
   */
  public ProductOrderExecution salesReturning(String personInfoId, String proorderId) {
    ProductOrder productOrder = new ProductOrder();
    productOrder.setPersonInfoId(personInfoId);
    productOrder.setProorderId(proorderId);
    productOrder.setEnableStatus(2);//审核状态
    productOrder = productOrderDao.queryProductOrder(productOrder);
    int mobi = productOrder.getTotalMobi();
    int integral = productOrder.getProductIntegral();
    PersonInfo personInfo = personInfoDao.queryPersonInfo(personInfoId);
    personInfo.setMobi(personInfo.getMobi() + mobi);
    personInfo.setShopIntegral(-integral);
    try {
      int result = personInfoDao.updatePersonInfo(personInfo);
      if (result > 0) {
        productOrder.setEnableStatus(7);//退货审核中
        try {
          int effectedNum = productOrderDao.updateProductOrder(productOrder);
          if (effectedNum > 0) {
            return new ProductOrderExecution(ProductOrderEnum.SUCCESS, productOrder);
          } else {
            throw new ProductOrderException("退货失败 updateProductOrder error");
          }
        } catch (Exception e) {
          throw new ProductOrderException("退货失败 updatePersonInfo error" + e.getMessage());
        }
      } else {
        throw new ProductOrderException("退货失败 updatePersonInfo error");
      }
    } catch (Exception e) {
      throw new ProductOrderException("退货失败 updatePersonInfo error" + e.getMessage());
    }

  }

  /**
   * 确认订单
   */
  public ProductOrderExecution sure(String personInfoId, String proorderId) {
    ProductOrder productOrder = new ProductOrder();
    productOrder.setPersonInfoId(personInfoId);
    productOrder.setProorderId(proorderId);
    productOrder.setEnableStatus(3);//代收货
    productOrder = productOrderDao.queryProductOrder(productOrder);
    int mobi = productOrder.getTotalMobi();
    int integral = productOrder.getProductIntegral();
    PersonInfo personInfo = personInfoDao.queryPersonInfo(personInfoId);
    personInfo.setMobi(personInfo.getMobi() + mobi);
    personInfo.setShopIntegral(-integral);
    try {
      int result = personInfoDao.updatePersonInfo(personInfo);
      if (result > 0) {
        productOrder.setEnableStatus(4);//已完成
        try {
          int effectedNum = productOrderDao.updateProductOrder(productOrder);
          if (effectedNum > 0) {
            return new ProductOrderExecution(ProductOrderEnum.SUCCESS, productOrder);
          } else {
            throw new ProductOrderException("确认失败 updateProductOrder error");
          }
        } catch (Exception e) {
          throw new ProductOrderException("确认失败 updatePersonInfo error" + e.getMessage());
        }
      } else {
        throw new ProductOrderException("确认失败 updatePersonInfo error");
      }
    } catch (Exception e) {
      throw new ProductOrderException("确认失败 updatePersonInfo error" + e.getMessage());
    }
  }

  /**
   * 得到当前订单详情
   */
  public ProductOrderExecution getProductOrder(ProductOrder productOrder) {
    if (productOrder != null) {
      productOrder = productOrderDao.queryProductOrder(productOrder);
      return new ProductOrderExecution(ProductOrderEnum.SUCCESS, productOrder);
    } else {
      return new ProductOrderExecution(ProductOrderEnum.NULL_AUTH_INFO);
    }
  }

  /**
   * 直接购买更新订单商品信息及数量
   */
  private void insertSPNum(List<ShoppingDto> shoppingDtoList, String proorderId,
      String personInfoId) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    for (ShoppingDto shoppingDto : shoppingDtoList) {
      String ppManageId = shoppingDto.getPpManageId();
      String productId = shoppingDto.getProductId();
      int checkNum = shoppingDto.getCheckNum();
      SPNum spNum = new SPNum();
      spNum.setPpManageId(ppManageId);
      spNum.setShoppingCart(0);
      spNum.setSpNumNum(checkNum);
      spNum.setSpNumId(UUIDUtil.createUUID());
      spNum.setEnableStatus(1);
      spNum.setPersonInfoId(personInfoId);
      spNum.setProOrderId(proorderId);
      spNum.setCreateTime(new Date());
      Product product = new Product();
      product.setProductId(productId);
      spNum.setProduct(product);
      spNumDao.insertSPNum(spNum);

    }
  }

  /**
   * 购物车更新订单商品信息及数量
   */
  private List<List> updateSPNum(List<ShoppingDto> shoppingDtoList,
      String proorderId) {
    List<List> list = new ArrayList<List>();
    for (ShoppingDto shoppingDto : shoppingDtoList) {
      List cList = new ArrayList();
      String spNumId = shoppingDto.getSpNumId();
      String ppManageId = shoppingDto.getPpManageId();
      String productId = shoppingDto.getProductId();
      Product product = productDao.queryProductById(productId);
      int checkNum = shoppingDto.getCheckNum();
      SPNum spNum = new SPNum();
      spNum.setPpManageId(ppManageId);
      spNum.setShoppingCart(0);
      spNum.setSpNumId(spNumId);
      spNum.setProOrderId(proorderId);
      try {
        SPNum spNum2= spNumDao.querySPNum(spNumId);
        spNum.setSpNumNum(checkNum-spNum2.getSpNumNum());
        int result = spNumDao.updateSPNum(spNum);
        if (result > 0) {
          SPNum spNum1= spNumDao.querySPNum(spNumId);
          Map<String, Object> map = new HashMap<String, Object>();
          if (ppManageId != null&&ppManageId!="") {
            PPManage p = ppManageDao.queryPPManageByUnique(ppManageId,productId);
            product.setProductIntegral(p.getPpManageIntegral());
            product.setPromotionMobi(p.getPpmanageMobi());
            spNum1.setProduct(product);
            List<ProductProperty> productPropertyList = productPropertyDao.queryProductPropertyByProductIdAndPPManageId(productId, p.getPpManageId());
            List propertyList = new ArrayList();
            String[] pvIds = new String[productPropertyList.size()];
            for (int i=0;i < productPropertyList.size();i++) {
              pvIds[i]=productPropertyList.get(i).getPropertyValue().getPvId();
            }
            List<PropertyValue> propertyValueList = propertyValueDao.getByIds(pvIds);
            map.put("propertyValueList", propertyValueList);
            cList.add(map);
          }else{
            map.put("spNum", spNum1);
            map.put("propertyValueList",new ArrayList<String>());
            cList.add(map);
          }
          list.add(cList);
        } else {
          throw new SPNumOperationException("updateSPNum error");
        }
      } catch (Exception e) {
        throw new SPNumOperationException("updateSPNum error" + e.getMessage());
      }
    }
    return list;
  }

  /**
   * 减少相应库存
   */
  private void updateRepertory(List<ShoppingDto> shoppingDtoList) {
    for (ShoppingDto shoppingDto : shoppingDtoList) {
      String ppManageId = shoppingDto.getPpManageId();
      String productId = shoppingDto.getProductId();
      int checkNum = shoppingDto.getCheckNum();
      //有参数
      if (ppManageId != null&&ppManageId!="") {
        PPManage ppManage = new PPManage();
        ppManage.setProductId(productId);
        ppManage.setPpUnique(ppManageId);
        ppManage.setPpManageNum(-checkNum);
        try {
          PPManage p = ppManageDao.queryPPManageByUnique(ppManageId,productId);
          String ppMMId = p.getPpManageId();
          ppManage.setPpManageId(ppMMId);
          if(p.getPpManageNum()-checkNum<0){
            throw new ProductPropertyoperationException("系统异常");
          }
          ppManageDao.updatePPManage(ppManage);
        } catch (Exception e) {
          throw new PPManageOperationException("修改库存异常");
        }
      } else {
        //套餐商品
        Product product = new Product();
        ProductRepertory productRepertory = new ProductRepertory();
        product = productDao.queryProductById(productId);
        String propertyId = product.getProductRepertory().getProrepId();
        ProductRepertory repertory = productRepertoryDao.queryProductRepertory(product.getProductRepertory().getProrepId());
        if(repertory.getCurrentNum()-checkNum<0){
          throw new ProductPropertyoperationException("系统异常");
        }
        productRepertory.setLastEditTime(new Date());
        productRepertory.setCurrentNum(-checkNum);
        productRepertory.setProrepId(propertyId);
        try {
          productRepertoryDao.updateProductRepertory(productRepertory);
        } catch (Exception e) {
          throw new ProductPropertyoperationException("修改库存异常");
        }
      }
    }


  }
}
