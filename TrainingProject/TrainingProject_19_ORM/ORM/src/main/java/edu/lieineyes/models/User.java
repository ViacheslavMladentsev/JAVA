package edu.lieineyes.models;

import edu.lieineyes.annotation.OrmColumn;
import edu.lieineyes.annotation.OrmColumnId;
import edu.lieineyes.annotation.OrmEntity;

@OrmEntity(table = "simple_user")
public class User {

    @OrmColumnId()
    private Long id;

    @OrmColumn(name = "first_name", length = 10)
    private String firstName;

    @OrmColumn(name = "last_name", length = 10)
    private String lastName;

    @OrmColumn(name = "height")
    private Integer height;


    public User() {
        this.firstName = "default first name";
        this.lastName = "default last name";
        this.height = 0;
    }

    public User(String firstName, String lastName, Integer height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getHeight() {
        return height;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", height=" + height +
                '}';
    }
}
