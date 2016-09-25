package com.marimon.themword.tries;

import java.util.function.Function;

/**
 *
 */
class Success<T> implements Try<T> {

  private T s;

  Success(T s) {
    this.s = s;
  }

  @Override
  public <R> Try<R> map(Function<T, R> f) {
    return new Success<>(f.apply(s));
  }

  @Override
  public <R> Try<R> flatMap(Function<T, Try<R>> f) {
    return f.apply(s);
  }

  @Override
  public T get() {
    return s;
  }

  @Override
  public boolean isSuccess() {
    return true;
  }

  @Override
  public String toString() {
    return "Success{" + "s=" + s + '}';
  }
}
