package com.mrstride.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

@ConsoleWorker(display="Sort work")
public class SortingWork  {
    public static void start() {
        quiz();
        executeSortUsing("Exercise #1: Sort normally", arr -> Collections.sort(arr, null));
        executeSortUsing("Exercise #2: Sort reversed order", arr -> Collections.sort(arr, Collections.reverseOrder()));
        executeSortUsing("Exercise #3: Sort by 1's digits", arr -> Collections.sort(arr, SortingWork::onesComp));
        executeSortUsing("Exercise #4: Sort 1st: Even vs Odd. 2nd: 1's digits.  3rd: value", arr -> Collections.sort(arr, SortingWork::evenOrder));
        executeSortUsing("Exercise #5: digits reversed", arr -> Collections.sort(arr, (i1, i2) -> reverse(i1).compareTo(reverse(i2))));
        executeSortUsing("Exercise #6: Sort via thenComparing like #4", SortingWork::sortInts);
    }

    public static List<Integer> genArray(int size) {
        Random rand = new Random();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(rand.nextInt(90));
        }
        return list;
    }

    public static void executeSortUsing(String desc, Consumer<List<Integer>> doSort) {
        System.out.println(desc);
        List<Integer> arr = genArray(20);
        System.out.println("   Orig: " + arr);
        doSort.accept(arr);
        System.out.println("  After: " + arr);
    }


    public static Integer reverse(Integer number) {
        int reversed = 0;
        while (number != 0) {
            reversed = reversed * 10 + number % 10;
            number = number / 10;
        }
        return reversed;
    }

    public static void sortInts(List<Integer> list) {

        // this is the easy way to use one Comparator
        //list.sort(SortingWork::evenOrder);

        // this is a way to compose a multi-tiered sort using thenComparing
        Comparator<Integer> primary = SortingWork::oddComp;
        Comparator<Integer> comparator = primary
                .thenComparing(SortingWork::onesComp)
                .thenComparing(Integer::compare);
           
        // here is how we inoke the sort using the 3 different composed above
        list.sort(comparator);

        /* 
          This explores Comparator.comparing()

          public static <T, U extends Comparable<? super U>> Comparator<T> 
         comparing(Function<? super T, ? extends U> keyExtractor)

        public U keyExtractor(T obj) and U IS-A Comparable<U>

        Comparing accepts an arg of type Function<T, U>. 
        Comparing will return a comparator. (t1, t2) -> {
            U a1 = arg.apply(t1);
            U a2 = arg.apply(t2);
            return a1.compareTo(a2)
        }
        That is implemented by calling a1.compareTo(a2)
            It gets a1 by calling arg.apply(c1)
            It gets a2 by calling arg.apply(c2)
        
        thenComparing is a default method in the Comparator interface
            if this.compare == 0, call other.compare().
        */
    }

    public static int evenOrder(Integer i1, Integer i2) {
        // if parity is different, odds are larger
        if (i1 % 2 != i2 % 2) {
            return i1%2 - i2%2;
        }
        // use 1's digit if those are different
        if (i1 % 10 != i2 % 10) {
            return i1 % 10 - i2 % 10;
        }

        // otherwise, use the size of the number
        return i1.compareTo(i2);
    }

    public static int oddComp(Integer i1, Integer i2) {
        return i1 % 2 - i2 % 2;
    }

    public static int onesComp(Integer i1, Integer i2) {
        return i1 % 10 - i2 % 10;
    }

    public static void quiz() {
        List<Integer> list = Arrays.asList(3, 5, 1, 2);
        Function<Integer, Integer> x = n -> -n;
        Comparator<Integer> r = Comparator.comparing(x);
        Collections.sort(list, r);
        System.out.println(list);
    }
}

