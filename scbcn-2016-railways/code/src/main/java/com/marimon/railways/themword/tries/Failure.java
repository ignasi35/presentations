package com.marimon.railways.themword.tries;

import java.util.function.Function;

/**
 *
 */
class Failure<T> implements Try<T> {

  private RuntimeException t;

  public Failure(Throwable t) {
    this.t = new RuntimeException(t);
  }

  @Override
  public <R> Try<R> map(Function<T, R> f) {
    return (Try<R>) this;
  }

  @Override
  public <R> Try<R> flatMap(Function<T, Try<R>> f) {
    return (Try<R>) this;
  }

  @Override
  public T get() {
    throw t;
  }

  @Override
  public boolean isSuccess() {
    return false;
  }

  @Override
  public String toString() {
    return "Failure{" + "t=" + t + '}';
  }
}
