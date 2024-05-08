package com.rabbitmq.demo.adapter.in.rest.delete;

import lombok.Data;

import java.util.List;

@Data
public class RabbitObject {

    private List<String> exchange;

    private List<String> queue;
}
