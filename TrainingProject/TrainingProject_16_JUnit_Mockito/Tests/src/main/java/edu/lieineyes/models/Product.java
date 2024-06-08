package edu.lieineyes.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Product {

    private Long id;
    private String name;
    private int price;

}
