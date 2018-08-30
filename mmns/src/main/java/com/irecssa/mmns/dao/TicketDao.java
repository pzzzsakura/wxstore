package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.Ticket;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 13:58
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface TicketDao {

  int insertTicket(Ticket ticket);

  List<Ticket> queryTicket();
  int updateTicket(Ticket ticket);
}
