package com.rabbitmq.demo.adapter.in.rest;

import com.rabbitmq.demo.application.in.simple.api.SimpleUseCaseApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/rabbitmq")
public class RabbitController {

    private final SimpleUseCaseApi simpleUseCase;

    @GetMapping(value = "simple")
    public ResponseEntity<?> simple(@RequestParam String message){

        simpleUseCase.send(message);

        SimpleResponse response = new SimpleResponse();
        response.setResult("success");
        response.setMessage(message);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

}
