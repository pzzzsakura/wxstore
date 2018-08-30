package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.AddressDao;
import com.irecssa.mmns.dto.execution.AddressExecution;
import com.irecssa.mmns.entity.Address;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.enums.AddressEnum;
import com.irecssa.mmns.exceptions.AddressException;
import com.irecssa.mmns.service.AddressService;
import com.irecssa.mmns.util.UUIDUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 14:18
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  private AddressDao addressDao;

  /**
   * 添加收货地址
   * @param address
   * @param personInfo
   * @return
   */
  @Transactional
  public AddressExecution addAddress(Address address,PersonInfo personInfo) {
      if(address!=null){
      List<Address> list =  addressDao.queryAddressList(personInfo.getPersonInfoId());
      if(list!=null&&list.size()>0){
        if(address.getIsDefault()==1){
          address.setAddressId(UUIDUtil.createUUID());
          address.setPersonInfo(personInfo);
          try{
            int result = addressDao.insertAddress(address);
            if(result>0){
              modifyIsDefault(address.getAddressId(),personInfo.getPersonInfoId());
              return new AddressExecution(AddressEnum.SUCCESS,address);
            }else{
              throw new AddressException("insertAddress error");
            }
          }catch(Exception e){
            throw new AddressException("insertAddress error"+e.getMessage());
          }
        }else{
          address.setAddressId(UUIDUtil.createUUID());
          address.setPersonInfo(personInfo);
          try{
            int result = addressDao.insertAddress(address);
            if(result>0){
              return new AddressExecution(AddressEnum.SUCCESS,address);
            }else{
              throw new AddressException("insertAddress error");
            }
          }catch(Exception e){
            throw new AddressException("insertAddress error"+e.getMessage());
          }
        }
      }else{
        address.setAddressId(UUIDUtil.createUUID());
        address.setIsDefault(1);
        address.setPersonInfo(personInfo);
        try{
          int result = addressDao.insertAddress(address);
          if(result>0){
            return new AddressExecution(AddressEnum.SUCCESS,address);
          }else{
            throw new AddressException("insertAddress error");
          }
        }catch(Exception e){
          throw new AddressException("insertAddress error"+e.getMessage());
        }
      }
    }else{
      return new AddressExecution(AddressEnum.NULL_AUTH_INFO);
    }
  }

  /**
   * 编辑收货地址
   * @param address
   * @return
   */
  @Transactional
  public AddressExecution modifyAddress(Address address) {
    if(address!=null){
      try{
        int result = addressDao.modifyAddress(address);
        if(result>0){
          return new AddressExecution(AddressEnum.SUCCESS,address);
        }else{
          throw new AddressException("modifyAddress error");
        }
      }catch(Exception e){
        throw new AddressException("modifyAddress error"+e.getMessage());
      }
    }else{
      return new AddressExecution(AddressEnum.NULL_AUTH_INFO);
    }
  }

  /**
   * 得到收货地址列表
   * @param personInfoId
   * @return
   */
  public AddressExecution getAddressList(String personInfoId) {
    List<Address> list = addressDao.queryAddressList(personInfoId);
    return new AddressExecution(AddressEnum.SUCCESS,list);
  }

  /**
   * 查询当前那订单地址
   * @param address
   * @return
   */
  public AddressExecution getAddress(Address address) {
    if(address!=null){
      Address  as= addressDao.queryAddress(address);
      return new AddressExecution(AddressEnum.SUCCESS,as);
    }else{
      return new AddressExecution(AddressEnum.NULL_AUTH_INFO);
    }
  }

  /**
   * 得到默认地址
   * @param personInfoId
   * @return
   */
  public AddressExecution getIsDefaultAddress(String personInfoId) {
    return new AddressExecution(AddressEnum.SUCCESS,addressDao.queryIsDefaultAddress(personInfoId));

  }

  /**
   * 修改默认地址
   * @param addressId
   * @return
   */
@Transactional
  public AddressExecution modifyIsDefault(String addressId,String personInfoId) {
  try{
    int result = addressDao.updateAllIsDefault(personInfoId);
    int effectedNum = addressDao.updateIsDefault(addressId);
    Address address = new Address();
    address.setAddressId(addressId);
    PersonInfo personInfo = new PersonInfo();
    personInfo.setPersonInfoId(personInfoId);
    address.setPersonInfo(personInfo);
    if(result>0&&effectedNum>0){
      return new AddressExecution(AddressEnum.SUCCESS,addressDao.queryAddress(address));
    }else{
      throw new AddressException("modifyIsDefault error");

    }
  }catch (Exception e){
    throw new AddressException("modifyIsDefault error"+e.getMessage());
  }

  }

  /**
   * 删除当前收货地址
   * @param addressId
   * @param personInfoId
   * @return
   */

  @Transactional
  public AddressExecution removeAddress(String addressId, String personInfoId) {
    Address address = new Address();
    address.setAddressId(addressId);
    PersonInfo personInfo = new PersonInfo();
    personInfo.setPersonInfoId(personInfoId);
    address.setPersonInfo(personInfo);
    try {
      int result = addressDao.deleteAddress(address);
      if(result>0){
          return new AddressExecution(AddressEnum.SUCCESS);
        } else{
        throw new AddressException("removeAddress error");
      }
    }catch (Exception e){
      throw new AddressException("removeAddress error"+e.getMessage());
    }
  }
}
