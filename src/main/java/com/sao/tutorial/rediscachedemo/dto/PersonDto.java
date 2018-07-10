package com.sao.tutorial.rediscachedemo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonDto implements Serializable {
    Long id;
    String firstName;
    String lastName;
    Integer age;
}
