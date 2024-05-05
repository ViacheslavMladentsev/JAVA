package edu.lieineyes.models;

import edu.lieineyes.annotation.OrmColumn;
import edu.lieineyes.annotation.OrmColumnId;
import edu.lieineyes.annotation.OrmEntity;


@OrmEntity(table = "simple_car")
public class Car {

    @OrmColumnId
    private Long id;

    @OrmColumn(name = "model")
    private String model;

    @OrmColumn(name = "speed", length = 3)
    private Integer speed;


    public Car() {
        this.model = "default model";
        this.speed = 0;
    }

    public Car(String model, Integer speed) {
        this.model = model;
        this.speed = speed;
    }


    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Integer getSpeed() {
        return speed;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", speed=" + speed +
                '}';
    }
}
