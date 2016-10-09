package com.marimon.railways.themword.lists;

public class Lists {

    public static final List NIL = new Nil();

    public static <T> List<T> asList(T[] t) {
        if (t.length > 0) return Lists.of(t);
        else return Lists.nil();
    }

    public static <T> List<T> of(T... t) {
        List<T> acc = NIL;
        for (int i = t.length - 1; i >= 0; i--) {
            acc = new Const<T>(t[i], acc);
        }
        return acc;
    }

    public static <T> List<T> nil() {
        return NIL;
    }
}
