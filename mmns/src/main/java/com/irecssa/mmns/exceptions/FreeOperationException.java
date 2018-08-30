package com.irecssa.mmns.exceptions;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/23 17:07
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class FreeOperationException extends RuntimeException {

  public FreeOperationException(String msg) {
    super(msg);
  }
}
