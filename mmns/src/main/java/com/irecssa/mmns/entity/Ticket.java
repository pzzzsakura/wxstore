package com.irecssa.mmns.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 13:56
 * @desc: accessToken
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Ticket {

  private String ticketId;

  private String ticket;

  private Integer expireIn;
  private Date time;

  public String getTicketId() {
    return ticketId;
  }

  public void setTicketId(String ticketId) {
    this.ticketId = ticketId;
  }

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public Integer getExpireIn() {
    return expireIn;
  }

  public void setExpireIn(Integer expireIn) {
    this.expireIn = expireIn;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }
}
