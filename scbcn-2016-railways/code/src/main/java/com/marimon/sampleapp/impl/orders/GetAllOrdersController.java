package com.marimon.sampleapp.impl.orders;

import com.marimon.railways.themword.tries.Tries;
import com.marimon.railways.themword.tries.Try;
import com.marimon.sampleapp.impl.Controller;
import com.marimon.sampleapp.impl.Req;
import com.marimon.sampleapp.impl.Resp;
import com.marimon.sampleapp.impl.db.DB;
import com.marimon.sampleapp.impl.resp.JSON;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class GetAllOrdersController implements Controller {
  private DB db;

  public GetAllOrdersController(DB db) {
    this.db = db;
  }

  @Override
  public Try<Resp> handle(Req req) {
    return db.getStatement()
        .flatMap(getAllOrders)
        .flatMap(asOrders)
        .map(asJSON);
  }

  private Function<Statement, Try<ResultSet>> getAllOrders =
      stmt -> Tries.to(() -> {
        try {
          return stmt.executeQuery("SELECT * FROM ORDERS;");
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      });

  private Function<ResultSet, Try<List<Order>>> asOrders = rs ->
      Tries.to(() -> {
        ArrayList<Order> orders = new ArrayList<>(1);
        try {
          while (rs.next()) {
            orders.add(new Order(rs.getInt("ID"), rs.getString("NAME"), rs.getString("USERNAME")));
          }
          return orders;
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      });

  private Function<List<Order>, Resp> asJSON = orders ->
      new JSON(orders.stream().map(Order::asJSON).collect(Collectors.joining(", ", "[", "]")));


}
