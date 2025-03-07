package com.rabbitmq.demo.adapter.in.rest;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.demo.utils.AppleUtil;
import com.rabbitmq.demo.utils.HttpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Controller
@AllArgsConstructor
@RequestMapping("/callback")
@Slf4j
public class CallbackController {


    @PostMapping(value = "send")
    @ResponseBody
    public String send(@RequestBody String payload,
                       @RequestHeader Map<String, String> headers) throws Exception {
        log.info("Received  Request:");
        log.info("Headers: {}", headers);
        log.info("Payload: {}", payload);

        Map<String, String> params = new TreeMap<>();
        params.put("orderNo", AppleUtil.getStringRandom(5));

        String url = "https://27be-123-195-4-124.ngrok-free.app/callback/pay";
        String result = HttpUtils.doJsonPost(url, JSONObject.toJSONString(params));
        JSONObject res = JSONObject.parseObject(result);
        log.info("Response for send: {}", result);
        String message = res.getString("message");
        log.info("message: {}", message);


        return message;
    }


    @PostMapping(value = "pay")
    @ResponseBody
    public String mtnCallback(@RequestBody String payload,
                              @RequestHeader Map<String, String> headers) {
        log.info("Received Request:");
        log.info("Headers: {}", headers);
        log.info("Payload: {}", payload);

        Map<String, String> res = new HashMap<>();
        res.put("error_code", "0000");
        res.put("message", "Success");
        return JSONObject.toJSONString(res);
    }
}
