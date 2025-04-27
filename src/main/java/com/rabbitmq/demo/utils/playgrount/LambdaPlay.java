package com.rabbitmq.demo.utils.playgrount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class LambdaPlay {

    private static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person("Alice", 25, "Female", "Engineer", "New York"));
        persons.add(new Person("Bob", 19, "Male", "Designer", "Los Angeles"));
        persons.add(new Person("Charlie", 17, "Male", "Doctor", "Chicago"));
        persons.add(new Person("Diana", 22, "Female", "Teacher", "New York"));
        persons.add(new Person("Evan", 17, "Male", "Student", "San Francisco"));
        persons.add(new Person("Fiona", 29, "Female", "Engineer", "Los Angeles"));
        persons.add(new Person("George", 40, "Male", "Architect", "Chicago"));
        persons.add(new Person("Hannah", 35, "Female", "Manager", "New York"));
        persons.add(new Person("Iris", 28, "Female", "Engineer", "San Francisco"));
        persons.add(new Person("Jack", 50, "Male", "Consultant", "Chicago"));
    }


    public static void main(String[] args) {
        LambdaPlay lambdaPlay = new LambdaPlay();
        log.info("Answer:{}", lambdaPlay.question1());
        log.info("Answer:{}", lambdaPlay.question2());
        log.info("Answer:{}", lambdaPlay.question3());
        log.info("Answer:{}", lambdaPlay.question4());
    }

    /**
     * 找出所有年齡 >= 30 且居住在 "Chicago" 的人名清單
     */
    private List<String> question1() {
        log.info("找出所有年齡 >= 30 且居住在 \"Chicago\" 的人名清單");
        return persons.stream()
                .filter(person -> person.getAge() >= 30 && "Chicago".equals(person.getCity()))
                .map(Person::getName)
                .collect(Collectors.toList());
    }

    /**
     * 統計每個城市的人數（Map<String, Long>）
     */
    private Map<String, Long> question2() {
        log.info("統計每個城市的人數（Map<String, Long>）");
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getCity, Collectors.counting())
                );
    }

    /**
     * 找出職業為 "Engineer" 且年齡小於 30 的女性名單
     */
    private List<String> question3() {
        log.info("找出職業為 \"Engineer\" 且年齡小於 30 的女性名單");
        return persons.stream()
                .filter(person -> person.getAge() < 30 && "Engineer".equals(person.getOccupation())
                        && "Female".equals(person.getGender()))
                .map(Person::getName)
                .collect(Collectors.toList());
    }

    /**
     * 找到年齡最小的男性和女性分別是誰
     */
    private Map<String, Person> question4() {
        log.info("找到年齡最小的男性和女性分別是誰");
        return persons.stream()
                .collect(Collectors.toMap(Person::getGender, Function.identity(),
                        BinaryOperator.minBy(Comparator.comparingInt(Person::getAge))));
    }
}
