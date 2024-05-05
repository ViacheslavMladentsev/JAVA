package edu.school21.classes;

import lombok.ToString;

@ToString
public class Car {
    private final String model;
    private final int speed;

    public Car() {
        this.model = "Default model";
        this.speed = 220;
    }

    public Car(String model, int speed) {
        this.model = model;
        this.speed = speed;
    }

    private String powerIncrease(int idx1, Integer idx2, String str1) {
        return String.valueOf(speed + speed / 100.0 * idx1) + str1 + idx2;
    }

    public void printHello() {
        System.out.println("Hello");
    }

}
