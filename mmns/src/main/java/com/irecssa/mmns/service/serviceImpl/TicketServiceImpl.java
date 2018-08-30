package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.TicketDao;
import com.irecssa.mmns.dto.execution.TicketExecution;
import com.irecssa.mmns.entity.Ticket;
import com.irecssa.mmns.enums.TicketEnum;
import com.irecssa.mmns.exceptions.TicketException;
import com.irecssa.mmns.service.TicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/27 14:05
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class TicketServiceImpl implements TicketService {

  @Autowired
  private TicketDao ticketDao;


  public TicketExecution insertTicket(Ticket ticket) {
   try {
     int result  = ticketDao.insertTicket(ticket);
     if(result>0){
       return new TicketExecution(TicketEnum.SUCCESS,ticket);
     }else{
       throw new TicketException("insertTicket error");
     }
   }catch (Exception e){
     throw new TicketException("insertTicket error"+e.getMessage());
   }
  }

  public TicketExecution queryTicket() {
    List<Ticket> ticketList  = ticketDao.queryTicket();
    if(ticketList!=null&&ticketList.size()>0){
      return new TicketExecution(TicketEnum.SUCCESS,ticketList);
    }else{
      throw new TicketException("queryTicket error");
    }
  }

  public TicketExecution updateTicket(Ticket ticket) {
    try {
      int result  = ticketDao.updateTicket(ticket);
      if(result>0){
        return new TicketExecution(TicketEnum.SUCCESS,ticket);
      }else{
        throw new TicketException("updateTicket error");
      }
    }catch (Exception e){
      throw new TicketException("updateTicket error"+e.getMessage());
    }
  }
}
