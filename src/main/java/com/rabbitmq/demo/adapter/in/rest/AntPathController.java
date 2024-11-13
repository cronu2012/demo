package com.rabbitmq.demo.adapter.in.rest;

import com.rabbitmq.demo.utils.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/generation/response")
@Slf4j
public class AntPathController {


    private final AntPathMatcher antPathMatcher;

    @PostMapping(value = "v1/**/isSuccess")
    public ResponseEntity<?> doDepositNotify(HttpServletRequest request) {

        try {
            String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
            String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            String api = antPathMatcher.extractPathWithinPattern(pattern, path);
            String[] apiSplit = api.split("/");
            String merchantName = apiSplit.length > 0 ? apiSplit[0] : "unknown";

            // params 转 map
            Map<String, String> reqMap = HttpUtils.getResMap(request);
            // header 转 map
            Map<String, String> headerMap = HttpUtils.getHeaderMap(request);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("path", path);
            response.put("pattern", pattern);
            response.put("api", api);
            response.put("apiSplit", apiSplit);
            response.put("merchantName", merchantName);
            response.put("headerMap", headerMap);
            response.put("reqMap", reqMap);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("失败:", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @PostMapping(value = "v2/{name}/isSuccess")
    public ResponseEntity<?> doDepositNotify2(HttpServletRequest request) {

        try {
            Map<String, String> template =
                    (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

            // params 转 map
            Map<String, String> reqMap = HttpUtils.getResMap(request);
            // header 转 map
            Map<String, String> headerMap = HttpUtils.getHeaderMap(request);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("template", template);
            response.put("pattern", pattern);
            response.put("headerMap", headerMap);
            response.put("reqMap", reqMap);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("失败:", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * url: v3/fuckboy;version=1;order=10,20/isSuccess
     * matrix: "fuckboy": {
     *             "version": [
     *                 "1"
     *             ],
     *             "order": [
     *                 "10",
     *                 "20"
     *             ]
     *         }
     */
    @PostMapping(value = "v3/{fuckboy}/isSuccess")
    public ResponseEntity<?> doDepositNotify3(HttpServletRequest request) {

        try {
            Map<String, Map<String, String>> matrix =
                    (Map<String, Map<String, String>>) request.getAttribute(HandlerMapping.MATRIX_VARIABLES_ATTRIBUTE);
            String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

            // params 转 map
            Map<String, String> reqMap = HttpUtils.getResMap(request);
            // header 转 map
            Map<String, String> headerMap = HttpUtils.getHeaderMap(request);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("matrix", matrix);
            response.put("pattern", pattern);
            response.put("headerMap", headerMap);
            response.put("reqMap", reqMap);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("失败:", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @GetMapping(value = "v4/**/isSuccess")
    public ResponseEntity<?> doDepositNotify4(HttpServletRequest request) {

        try {
            String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
            String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            String api = antPathMatcher.extractPathWithinPattern(pattern, path);
            String[] apiSplit = api.split("/");
            String merchantName = apiSplit.length > 0 ? apiSplit[0] : "unknown";
            // params 转 map
            Map<String, String> reqMap = HttpUtils.getResMap(request);
            // header 转 map
            Map<String, String> headerMap = HttpUtils.getHeaderMap(request);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("path", path);
            response.put("pattern", pattern);
            response.put("api", api);
            response.put("apiSplit", apiSplit);
            response.put("merchantName", merchantName);
            response.put("headerMap", headerMap);
            response.put("reqMap", reqMap);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("失败:", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
