package com.rabbitmq.demo.adapter.in.rest.crud;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JsonDto {
    @JsonProperty("Inter")
    private String Inter;
    @JsonProperty("Outer")
    private String Outer;
}
