package com.irecssa.mmns.exceptions;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/21 17:33
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class ProductCategoryOperationException extends RuntimeException{

  public ProductCategoryOperationException(String msg) {
  super(msg);
  }
}
