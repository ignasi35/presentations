package com.marimon.sampleapp.impl.orders;

import com.marimon.railways.themword.tries.Tries;
import com.marimon.railways.themword.tries.Try;
import com.marimon.sampleapp.impl.Ctlr;
import com.marimon.sampleapp.impl.Req;
import com.marimon.sampleapp.impl.Resp;
import com.marimon.sampleapp.impl.db.DB;
import com.marimon.sampleapp.impl.resp.JSON;
import com.marimon.sampleapp.impl.resp.OK;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GetAllOrdersController implements Ctlr {
  private DB db;

  public GetAllOrdersController(DB db) {
    this.db = db;
  }

  @Override
  public Try<Resp> handle(Req req) {
    return db.getStatement()
        .flatMap(getAllOrders)
        .flatMap(asOrders)
        .map(GetAllOrdersController::asJSON);
  }

  private Function<Statement, Try<ResultSet>> getAllOrders =
      stmt -> Tries.to(() -> {
        try {
          return stmt.executeQuery("SELECT * FROM ORDERS;");
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      });

  private Function<ResultSet, Try<List<Order>>> asOrders = rs -> {

    return Tries.to(() -> {
      ArrayList<Order> orders = new ArrayList<>(1);

      try {
        while (rs.next()) {
          orders.add(new Order(
              rs.getInt("ID"),
              rs.getString("NAME"),
              rs.getString("USERNAME")
          ));
        }
        return orders;
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    });
  };

  // must use static method because erasure :(
  private static Resp asJSON(List<Order> orders) {
    return new JSON(orders.stream().map(Order::asJSON).collect(Collectors.joining(", ", "[", "]")));
  }


}
