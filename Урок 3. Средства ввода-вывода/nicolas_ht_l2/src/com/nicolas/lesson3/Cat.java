package com.nicolas.lesson3;

import java.io.Serializable;

public class Cat extends Animal implements Serializable{

    private String name;
    private transient Ball ball;

    public Cat(String name) {
        super(2);
        System.out.println("Cat's constructor");
        this.name = name;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Ball getBall() {
        return ball;
    }

    public void info() {
        System.out.println(name);
    }
}
