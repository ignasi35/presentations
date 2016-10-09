package com.marimon.sampleapp.impl.db;

import com.marimon.railways.themword.tries.Tries;
import com.marimon.railways.themword.tries.Try;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class InMemH2DB implements DB {

  private final String url;
  private final String user;
  private final String pwds;

  public InMemH2DB() throws ClassNotFoundException {
    Class.forName("org.h2.Driver");
    url = "jdbc:h2:mem:orders;INIT=RUNSCRIPT FROM 'src/main/resources/db/orders/create.sql'";
    user = "sa";
    pwds = "filepwd userpwd";
  }

  public Try<Connection> getConnection() {
    return Tries.to(() -> {
      try {
        return DriverManager.getConnection(url, user, pwds);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    });
  }

}
