package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.Address;
import com.irecssa.mmns.enums.AddressEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/25 13:31
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class AddressExecution {
private int state;
private String stateInfo;
private Address address;
private List<Address> addressList;

  public AddressExecution(AddressEnum addressEnum) {
    this.state = state;
    this.stateInfo = addressEnum.getStateInfo();
  }
  public AddressExecution(AddressEnum addressEnum,Address address) {
    this.state = state;
    this.stateInfo = addressEnum.getStateInfo();
    this.address= address;
  }
  public AddressExecution(AddressEnum addressEnum,List<Address> addressList) {
    this.state = state;
    this.stateInfo = addressEnum.getStateInfo();
    this.addressList = addressList;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getStateInfo() {
    return stateInfo;
  }

  public void setStateInfo(String stateInfo) {
    this.stateInfo = stateInfo;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<Address> getAddressList() {
    return addressList;
  }

  public void setAddressList(List<Address> addressList) {
    this.addressList = addressList;
  }
}
