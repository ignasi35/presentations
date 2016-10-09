package com.marimon.railways.themword.lists;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public interface List<T> {

  <R> List<R> flatMap(Function<? super T, List<R>> f);

  <R> List<R> map(Function<? super T, R> f);

  T head();

  List<T> tail();

  boolean isEmpty();

  List<T> filter(Predicate<? super T> p);

  <Z> Z reduce(Z z, BiFunction<Z, T, Z> f);

  void forEach(Consumer<? super T> f);

  List<T> append(List<T> b);

  default List<T> prepend(T t) {
    return new Const<>(t, this);
  }


}

class Const<T> implements List<T> {

  private T head;
  private List<T> tail;

  Const(T head, List<T> tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  public <R> List<R> flatMap(Function<? super T, List<R>> f) {
    return f.apply(head).append(tail.flatMap(f));
  }

  @Override
  public <R> List<R> map(Function<? super T, R> f) {
    return new Const<>(f.apply(head), tail.map(f));
  }

  @Override
  public T head() {
    return this.head;
  }

  @Override
  public List<T> tail() {
    return this.tail;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public List<T> filter(Predicate<? super T> p) {
    if (p.test(head)) return new Const<>(head, tail.filter(p));
    else return tail.filter(p);
  }

  @Override
  public <Z> Z reduce(Z z, BiFunction<Z, T, Z> f) {
    return tail.reduce(f.apply(z, head), f);
  }

  @Override
  public void forEach(Consumer<? super T> f) {
    f.accept(head);
    tail.forEach(f);
  }

  @Override
  public List<T> append(List<T> b) {
    return new Const<>(head, tail.append(b));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Const aConst = (Const) o;

    if (head != null ? !head.equals(aConst.head) : aConst.head != null) return false;
    if (tail != null ? !tail.equals(aConst.tail) : aConst.tail != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = head != null ? head.hashCode() : 0;
    result = 31 * result + (tail != null ? tail.hashCode() : 0);
    return result;
  }
}

class Nil<T> implements List<T> {

  @Override
  public <R> List<R> flatMap(Function<? super T, List<R>> f) {
    return (List<R>) this;
  }

  @Override
  public <R> List<R> map(Function<? super T, R> f) {
    return (List<R>) this;
  }

  @Override
  public T head() {
    throw new NoSuchElementException();
  }

  @Override
  public List<T> tail() {
    throw new NoSuchElementException();
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public List<T> filter(Predicate<? super T> p) {
    return this;
  }

  @Override
  public <Z> Z reduce(Z z, BiFunction<Z, T, Z> f) {
    return z;
  }

  @Override
  public void forEach(Consumer<? super T> f) {
  }

  @Override
  public List<T> append(List<T> b) {
    return b;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Nil;
  }

  @Override
  public int hashCode() {
    return 23;
  }
}
