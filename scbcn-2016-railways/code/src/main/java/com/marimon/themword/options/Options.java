package com.marimon.themword.options;


public class Options  {
    public static <T> Some<T> some(T t) { return new Some<T>(t); }
    public static <T> None<T> none() { return new None<T>(); }
}
