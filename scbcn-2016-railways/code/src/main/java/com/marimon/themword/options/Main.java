package com.marimon.themword.options;

import com.marimon.themword.lists.*;

public class Main {

  public static void main(String[] args) {
    final List<Integer> nil = Lists.nil();

    final List<Integer> a3 = nil.prepend(3);
    final List<Integer> a2 = a3.prepend(2);
    final List<Integer> a1 = a2.prepend(1);

    final List<Integer> a0 = a1.prepend(0);
    final List<Integer> b = a1.prepend(4);

    System.out.println("Starting... ");
    System.out.println("----------- ");
    a0.forEach(System.out::println);
    System.out.println("----------- ");
    b.forEach(System.out::println);
    System.out.println("----------- ");
    System.out.println("Completed");

  }

}
