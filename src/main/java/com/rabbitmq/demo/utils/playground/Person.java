package com.rabbitmq.demo.utils.playground;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person{
    String name;
    int age;
    String gender;
    String occupation;
    String city;
}