package com.rabbitmq.demo.service;

import org.springframework.stereotype.Service;

@Service
public class BTestServiceImpl implements TestService{
    @Override
    public void test(String input) {
        System.out.println("I am B. " + input);
    }
}
