package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.PPManage;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/24 15:32
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface PPManageDao {

  int insertPPManage(PPManage ppManage);

  PPManage queryPPManageById(@Param("ppManageId") String ppManageId,
      @Param("productId") String productId);

  List<PPManage> queryPPManageList(String productId);

  int updatePPManage(PPManage ppManage);

  PPManage queryPPManageByUnique(@Param("ppUnique") String ppUnique,
      @Param("productId") String productId);
}
