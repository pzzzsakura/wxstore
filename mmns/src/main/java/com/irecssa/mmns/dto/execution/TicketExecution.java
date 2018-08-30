package com.irecssa.mmns.dto.execution;

import com.irecssa.mmns.entity.Ticket;
import com.irecssa.mmns.enums.TicketEnum;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 14:10
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class TicketExecution {
  private int state;
  private String stateInfo;
  private Ticket token;
  private List<Ticket> ticketList;

  public TicketExecution(TicketEnum ticketEnum,Ticket ticket) {
    this.token = ticket;
    this.state = ticketEnum.getState();
    this.stateInfo = ticketEnum.getStateInfo();
  }

  public TicketExecution(TicketEnum ticketEnum,List<Ticket> ticketList) {
    this.ticketList = ticketList;
    this.state = ticketEnum.getState();
    this.stateInfo = ticketEnum.getStateInfo();
  }

  public TicketExecution(TicketEnum ticketEnum) {
    this.state = ticketEnum.getState();
    this.stateInfo = ticketEnum.getStateInfo();
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


  public Ticket getToken() {
    return token;
  }

  public void setToken(Ticket token) {
    this.token = token;
  }

  public List<Ticket> getTicketList() {
    return ticketList;
  }

  public void setTicketList(List<Ticket> ticketList) {
    this.ticketList = ticketList;
  }
}
