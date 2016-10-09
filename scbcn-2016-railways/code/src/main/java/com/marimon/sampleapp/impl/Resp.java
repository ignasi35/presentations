package com.marimon.sampleapp.impl;

import java.util.Optional;

public interface Resp {
  default Optional<String> payload() {
    return Optional.empty();
  }
}
