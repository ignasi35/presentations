package com.marimon.themword.tries;

import com.marimon.themword.lists.*;


import java.util.Arrays;
import java.util.function.Function;


public class Main {

  public static void main(String[] args) {
    final String[] arr = new String[]{"1", "2", "0", "jamon"};
    final List<String> stringList = Lists.asList(arr);

    System.out.println("  WITHOUT TRY ");
//    happy(Arrays.asList(arr));
    sad(Arrays.asList(arr));
    System.out.println("  WITH TRY ");

    grownupAttempt1(stringList);
    grownupAttempt2(stringList);

  }


  public static void happy(
      java.util.List<String> stringList) {

    for (String s : stringList) {
      int i = (50 / Integer.valueOf(s)) - 1;
      System.out.println(" -> " + i);
    }

  }






  public static void sad(java.util.List<String> stringList) {
    try {
      try {
        for (String s : stringList) {
          int parsed = Integer.valueOf(s);
          int div = 50 / parsed;
          int x = div - 1;
          System.out.println(" --> " + x);
        }
      } catch (ArithmeticException ae) {
        // What do we do ?!
      }
    } catch (NumberFormatException nfe) {
      // What do we do ?!
    }
  }


  static final Function<String, Try<Integer>> toInt =
      x -> Tries.to(() -> Integer.valueOf(x));

  static final Function<Integer, Try<Integer>> divide50 =
      (x) -> Tries.to(() -> 50 / x);

  static final Function<Integer, Integer> minusOne =
      x -> x - 1;


  public static void grownupAttempt1(List<String> stringList) {
    final Function<String, Try<Integer>> pipe = x ->
        toInt.apply(x)
            .flatMap(divide50)
            .map(minusOne);
    final List<Try<Integer>> tryList = stringList.map(pipe);
    tryList.forEach(System.out::println);
  }


  public static List<Try<Integer>> grownupAttempt2(List<String> stringList) {
    return
        stringList.map(x ->
            toInt.apply(x)
                .flatMap(divide50)
                .map(minusOne)
        );
  }

}
