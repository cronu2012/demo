package com.rabbitmq.demo.utils.playground;

import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
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
        log.info("Answer:{}", lambdaPlay.question5());
        log.info("Answer:{}", lambdaPlay.question6());

//        try {
//            String result = lambdaPlay.sign(dataStr, key);
//            log.info("SIGN:{}", result);
//        } catch (Exception e) {
//            log.error("ERROR", e);
//        }

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

    /**
     * 找出每個城市中年紀最大的那位女性是誰
     */
    private Map<String, Optional<Person>> question5() {
        log.info("找出每個城市中年紀最大的那位女性是誰");
        return persons.stream()
                .filter(person -> "Female".equals(person.getGender()))
                .collect(Collectors.groupingBy(Person::getCity,
                        Collectors.maxBy(Comparator.comparing(Person::getAge))));

    }

    /**
     * 統計各個職業的平均年齡，並依照年齡由高到低排序
     */
    private Map<String, Double> question6() {
        log.info("統計各個職業的平均年齡，並依照年齡由高到低排序");
        return
    }

    //將所有成年人（年齡 >= 18）依照性別分組，並列出每組的姓名清單（只顯示名字）
    //
    //提示：filter + groupingBy + mapping
    //
    //列出所有城市中包含職業為 Engineer 的人員清單，並依城市排序（不重複）
    //
    //提示：filter + map + distinct + sorted
    //
    //找出每個城市中平均年齡最高的職業
    //
    //提示：先 group by 城市再 group by 職業，然後計算平均年齡後找最大值
    //
    //統計每個職業中有多少人來自不同的城市（例如：Engineer 來自 3 個城市）
    //
    //提示：使用 groupingBy + mapping + Collectors.toSet().size()
    //
    //將年齡小於 30 的人依照性別與職業分組，並列出姓名清單
    //
    //提示：filter + groupingBy（兩層 key）+ mapping
    //
    //依照城市分組後，列出每個城市中所有人年齡的總和、最大值、最小值與平均值
    //
    //提示：使用 Collectors.summarizingInt
    //
    //列出每個職業中女性比例超過 50% 的職業名稱
    //
    //提示：分組後計算男女比例
    //
    //找出所有職業中最年輕與最年長的人的名字（每種職業一對）
    //
    //提示：group by 職業後使用 minBy 和 maxBy



}
