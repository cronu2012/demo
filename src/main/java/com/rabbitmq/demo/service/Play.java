package com.rabbitmq.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Play {

    private final Map<String, TestService> testServiceMap;

    public void test(){
        for (Map.Entry entry : testServiceMap.entrySet()){
            String key = (String)entry.getKey();
            TestService service =  (TestService) entry.getValue();

            service.test(key);
        }
    }
}
