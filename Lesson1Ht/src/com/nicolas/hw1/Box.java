package com.nicolas.hw1;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class Box<T extends Fruit> {
    private ArrayList<T> container = new ArrayList<>();

    public Box(T... fruit) {
        container.addAll(asList(fruit));
    }

    public float getWeight() {
        if (container.size() > 0) {
            float wt = container.get(0).getWeight();
            return wt * container.size();
        }

        return 0;
    }

    public boolean compare(Box<?> box2) {
        return Math.abs(getWeight() - box2.getWeight()) < 0.00001;
    }

    public void transferTo(Box<T> box2) {
        box2.container.addAll(container);

        container.clear();
    }

    public void addFruit(T fruit) {
        container.add(fruit);
    }

}
