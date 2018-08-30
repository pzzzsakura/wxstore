package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.ComboDao;
import com.irecssa.mmns.dao.ProductDao;
import com.irecssa.mmns.entity.Combo;
import com.irecssa.mmns.entity.Product;
import com.irecssa.mmns.service.ComboService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 23:47
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class ComboServiceImpl implements ComboService {

  @Autowired
  private ComboDao comboDao;

  @Autowired
  private ProductDao productDao;
  public Map<String,Object> getComboById(String comboId) {
    List<Product> list = new ArrayList<Product>();
    Combo combo = comboDao.queryComboById(comboId);
    if(combo!=null&&combo.getaProduct()!=null){
      Product product = productDao.queryProductById(combo.getaProduct().getProductId());
      list.add(product);
    }
    if(combo!=null&&combo.getbProduct()!=null){
      Product product = productDao.queryProductById(combo.getbProduct().getProductId());
      list.add(product);
    }
    if(combo!=null&&combo.getcProduct()!=null){
      Product product = productDao.queryProductById(combo.getcProduct().getProductId());
      list.add(product);
    }
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("productList",list);
    map.put("combo",combo);
    return map;
  }
}
