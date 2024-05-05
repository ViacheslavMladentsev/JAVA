package edu.school21.classes;

import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;
import lombok.ToString;

@ToString
@HtmlForm(fileName = "user_form.html", action = "/users", method = "post")
public class User {

    @HtmlInput(type = "text", name = "firstName", placeholder = "Enter first name")
    private String firstName;

    @HtmlInput(type = "text", name = "lastName", placeholder = "Enter last name")
    private String lastName;

    @HtmlInput(type = "number", name = "height", placeholder = "Enter height user")
    private int height;

    public User() {
        this.firstName = "Default first name";
        this.lastName = "Default last name";
        this.height = 0;
    }

    public User(String firstName, String lastName, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
    }

    public int grow(int value) {
        this.height += value;
        return height;
    }

}
