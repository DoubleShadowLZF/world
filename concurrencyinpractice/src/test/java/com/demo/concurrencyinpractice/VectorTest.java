package com.demo.concurrencyinpractice;

import java.util.Arrays;
import java.util.Vector;

public class VectorTest {
    public void test(){
        Vector<Object> objects = new Vector<>();
        objects.add(1);
        objects.add(1);
        objects.add(1);
        objects.add(1);

        String s = "a" +objects+ "b" + Arrays.asList(1,2,3,4)+"c" + "d";

    }

    public static void main(String[] args) {
        VectorTest vectorTest = new VectorTest();
        vectorTest.test();
    }
}
