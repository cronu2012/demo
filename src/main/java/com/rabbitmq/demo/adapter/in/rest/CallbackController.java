package com.rabbitmq.demo.adapter.in.rest;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/callback")
@Slf4j
public class CallbackController {
    @PostMapping(value = "pay",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String mtnCallback(@RequestBody String payload,
                                         @RequestHeader Map<String, String> headers) {
        log.info("Received Webhook Request:");
        log.info("Headers: {}", headers);
        log.info("Payload: {}", payload);

        Map<String, String> res = new HashMap<>();
        res.put("error_code", "0000");
        res.put("message", "Success");
        return JSONObject.toJSONString(res);
    }
}
