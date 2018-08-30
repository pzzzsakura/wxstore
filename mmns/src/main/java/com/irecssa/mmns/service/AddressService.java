package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.AddressExecution;
import com.irecssa.mmns.entity.Address;
import com.irecssa.mmns.entity.PersonInfo;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 14:17
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface AddressService {

  AddressExecution addAddress(Address address,PersonInfo personInfo);

  AddressExecution modifyAddress(Address address);

  AddressExecution getAddressList(String personInfoId);

  AddressExecution getAddress(Address address);

  AddressExecution getIsDefaultAddress(String personInfoId);

  AddressExecution modifyIsDefault(String addressId,String personInfoId);

  AddressExecution removeAddress(String address,String personInfoId);

}
