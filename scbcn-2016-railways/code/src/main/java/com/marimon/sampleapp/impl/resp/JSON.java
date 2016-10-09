package com.marimon.sampleapp.impl.resp;


import com.marimon.sampleapp.impl.Resp;

import java.util.Optional;

public class JSON implements Resp {
  private String jsonString;

  public JSON(String jsonString) {
    this.jsonString = jsonString;
  }

  @Override
  public Optional<String> payload() {
    return Optional.of(jsonString);
  }
}
