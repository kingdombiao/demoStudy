package com.kingdombiao.orderservice;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author unovo
 * @create 2019-08-26 17:36
 */
public class DemoTest {

    public static void main(String[] args) {

        SortedSet<String> sortedSet = new TreeSet<>();

        sortedSet.add("1");
        sortedSet.add("3");
        sortedSet.add("6");
        sortedSet.add("2");

        System.out.println(sortedSet);

        SortedSet<String> sortedSet1 = sortedSet.headSet("3");

        System.out.println(sortedSet1);

        String last = sortedSet1.last();

        System.out.println(last);


    }
}
