package com.marimon.sampleapp.impl.db;

import com.marimon.railways.themword.tries.Tries;
import com.marimon.railways.themword.tries.Try;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface DB {
  Try<Connection> getConnection();

  default Try<Statement> getStatement() {
    return getConnection().flatMap(
        conn -> Tries.to(() -> {
          try {
            return conn.createStatement();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        })
    );
  }

}
