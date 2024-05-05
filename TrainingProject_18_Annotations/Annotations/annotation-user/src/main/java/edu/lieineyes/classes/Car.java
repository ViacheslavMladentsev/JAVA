package edu.school21.classes;

import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;
import lombok.ToString;

@ToString
@HtmlForm(fileName = "car_form.html", action = "/cars", method = "post")
public class Car {

    @HtmlInput(type = "text", name = "model", placeholder = "Enter model car")
    private final String model;

    @HtmlInput(type = "number", name = "speed", placeholder = "Enter speed car")
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
