package com.irecssa.mmns.service;

import com.irecssa.mmns.dto.execution.TicketExecution;
import com.irecssa.mmns.entity.Ticket;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 14:04
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface TicketService {

  TicketExecution insertTicket(Ticket ticket);
  TicketExecution queryTicket();
  TicketExecution updateTicket(Ticket ticket);
}
