package com.rabbitmq.demo.adapter.in.rest;

import com.rabbitmq.demo.adapter.in.rest.crud.JsonDto;
import com.rabbitmq.demo.adapter.in.rest.crud.RabbitObject;
import com.rabbitmq.demo.application.in.crud.CrudRabbitService;
import com.rabbitmq.demo.application.in.factory.QueueService;
import com.rabbitmq.demo.application.in.simple.SimpleRabbitService;
import com.rabbitmq.demo.application.in.work.WorkTaskRabbitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/rabbitmq")
public class RabbitController {

    private final QueueService queueService;

    private final CrudRabbitService crudRabbitService;

    @RequestMapping("json1/isSuccess")
    @ResponseBody
    public ResponseEntity<?> jsonTest1(JsonDto dto) {

        Map<String, Object> map = new HashMap<>();

        String resultReturn = "Operation Success";
        map.put("error_code",0);
        map.put("message",resultReturn);
        map.put("inter",dto.getInter());
        map.put("outer",dto.getOuter());

        return ResponseEntity.ok(map);
    }
    @RequestMapping("json2/isSuccess")
    @ResponseBody
    public Map<String, Object> jsonTest2(JsonDto dto) {

        Map<String, Object> map = new HashMap<>();

        String resultReturn = "Operation Success";
        map.put("error_code",0);
        map.put("message",resultReturn);
        map.put("inter",dto.getInter());
        map.put("outer",dto.getOuter());

        return map;
    }

    @GetMapping(value = SimpleRabbitService.RABBIT_TYPE)
    public ResponseEntity<?> simple(@RequestParam String message){
        String rabbitType = SimpleRabbitService.RABBIT_TYPE;

        queueService.queueSend(message, rabbitType);

        RabbitResponse response = getResponse(message);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @GetMapping(value = WorkTaskRabbitService.RABBIT_TYPE)
    public ResponseEntity<?> workTask(@RequestParam String message){
        String rabbitType = WorkTaskRabbitService.RABBIT_TYPE;

        queueService.queueSend(message, rabbitType);

        RabbitResponse response = getResponse(message);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody RabbitObject request){

        crudRabbitService.delete(request);

        RabbitResponse response = getResponse("已刪除");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 生成 Response
     *
     * @param message 發送訊息
     * @return SimpleResponse
     */
    private RabbitResponse getResponse(String message) {
        RabbitResponse response = new RabbitResponse();
        response.setResult("success");
        response.setMessage(message);
        return response;
    }
}
