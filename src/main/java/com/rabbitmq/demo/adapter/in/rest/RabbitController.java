package com.rabbitmq.demo.adapter.in.rest;

import com.rabbitmq.demo.application.in.simple.api.SimpleUseCaseApi;
import com.rabbitmq.demo.application.in.simple.api.WorkTaskUseCaseApi;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

    private final WorkTaskUseCaseApi workTaskUseCase;

    @GetMapping(value = "simple")
    public ResponseEntity<?> simple(@RequestParam String message){

        simpleUseCase.send(message);

        SimpleResponse response = new SimpleResponse();
        response.setResult("success");
        response.setMessage(message);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "worktask")
    public ResponseEntity<?> workTask(@RequestParam String message){

        workTaskUseCase.send(message);

        SimpleResponse response = new SimpleResponse();
        response.setResult("success");
        response.setMessage(message);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
