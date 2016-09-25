package com.marimon.themword.options;

import java.util.function.Function;

public interface Option<T> {
  <R> Option<R> flatMap(Function<T, Option<R>> f);

  <R> Option<R> map(Function<T, R> f);

  T get();

  T getOrElse(T x);
}


class None<T> implements Option<T> {

  @Override
  public <R> Option<R> flatMap(Function<T, Option<R>> f) {
    return (None<R>) this;
  }

  @Override
  public <R> Option<R> map(Function<T, R> f) {
    return (None<R>) this;
  }

  @Override
  public T get() {
    throw new NullPointerException();
  }

  @Override
  public T getOrElse(T x) {
    return x;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return true;
  }

  @Override
  public int hashCode() {
    return 42;
  }
}

class Some<T> implements Option<T> {
  private final T t;

  public Some(T t) {
    this.t = t;
  }

  @Override
  public <R> Option<R> flatMap(Function<T, Option<R>> f) {
    return f.apply(t);
  }

  @Override
  public <R> Option<R> map(Function<T, R> f) {
    return new Some(f.apply(t));
  }


  @Override
  public T get() {
    return t;
  }

  @Override
  public T getOrElse(T x) {
    return t;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Some some = (Some) o;

    if (t != null ? !t.equals(some.t) : some.t != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return t != null ? t.hashCode() : 0;
  }
}
