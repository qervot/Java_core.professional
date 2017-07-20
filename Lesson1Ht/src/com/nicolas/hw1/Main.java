package com.nicolas.hw1;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //region Task 1 Change
//        Integer[] iMass = {1,2,3,4,5};
//        System.out.println("Before changing: " + Arrays.toString(iMass));
//        changePlaces(iMass, 1, 3);
//        System.out.println("Changed elements: " + Arrays.toString(iMass));
        //endregion

        //region Task2 Convert
//        ArrayList<Integer> newList = convToArrayList(iMass);
//        System.out.println("List from array: " + newList);
        //endregion

        //region Task 3 Boxes
//        Box<Apple> appleBox = new Box<>(new Apple(), new Apple(), new Apple());
//        Box<Apple> appleBox2 = new Box<>(new Apple(), new Apple(), new Apple());
//        Box<Orange> orangeBox = new Box<>(new Orange(), new Orange());
//
//        System.out.println("Before transferring");
//        System.out.println(appleBox.getWeight());
//        System.out.println(appleBox2.getWeight());
//
//        System.out.println("Move to box2...");
//        appleBox.transferTo(appleBox2);
//
//        System.out.println("After transferring");
//        System.out.println(appleBox.getWeight());
//        System.out.println(appleBox2.getWeight());
//
//        System.out.println("Apple and Orange boxes have same weight: " + orangeBox.compare(appleBox2));
        //endregion

        Integer firstStr = 10;

        someMeth(firstStr);

        System.out.println(firstStr);

    }


    private static void someMeth(Integer aStr) {
        aStr += 5;
    }

    private static <T> void changePlaces(T[] arr, int firstItem, int scndItem) {
        T obj = arr[firstItem];
        arr[firstItem] = arr[scndItem];
        arr[scndItem] = obj;
    }

    private static <T> ArrayList<T> convToArrayList(T[] arr) {
        ArrayList<T> retList = new ArrayList<>();
        for (T item : arr) {
            retList.add(item);
        }

        return retList;
    }
}
