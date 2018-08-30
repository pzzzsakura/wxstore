package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.Address;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 13:37
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface AddressDao {

  int insertAddress(Address address);

  int modifyAddress(Address address);

  Address queryAddress(Address address);

  List<Address> queryAddressList(String personInfoId);

  int updateAllIsDefault(String personInfoId);

  Address queryIsDefaultAddress(String personInfoId);

  int updateIsDefault(String addressId);

  int deleteAddress(Address address);
}
