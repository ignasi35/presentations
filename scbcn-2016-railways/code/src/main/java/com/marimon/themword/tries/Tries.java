package com.marimon.themword.tries;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Tries {

  public static <T> Try<T> to(Supplier<T> f) {
    try {
      return new Success<>(f.get());
    } catch (Throwable t) {
      return new Failure<>(t);
    }

  }

  public static <T> Try<T> to(Supplier<T> f, Consumer<T> onFailure) {
    T t = null;
    try {
      t =f.get();
      return new Success<>(t);
    } catch (Throwable throwable) {
      if (t != null) {
        try {
          onFailure.accept(t);
        } catch (Exception e) {
          e.addSuppressed(throwable);
          return new Failure<>(e);
        }
      }
      return new Failure<>(throwable);
    }

  }
}
