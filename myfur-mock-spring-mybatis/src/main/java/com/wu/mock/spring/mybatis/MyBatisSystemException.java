package com.wu.mock.spring.mybatis;

import org.springframework.dao.UncategorizedDataAccessException;

/**
 * MyBatis specific subclass of {@code UncategorizedDataAccessException}, for MyBatis system errors that do
 * not match any concrete {@code org.springframework.dao} exceptions.
 *
 * In MyBatis 3 {@code org.apache.ibatis.exceptions.PersistenceException} is a {@code RuntimeException},
 * but using this wrapper class to bring everything under a single hierarchy will be easier for client code to
 * handle.
 *
 * @author Hunter Presnall
 * 
 * @version $Id$
 */
public class MyBatisSystemException extends UncategorizedDataAccessException {

  private static final long serialVersionUID = -5284728621670758939L;

  public MyBatisSystemException(Throwable cause) {
    super(null, cause);
  }

}