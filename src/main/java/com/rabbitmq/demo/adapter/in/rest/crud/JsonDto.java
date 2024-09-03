package com.rabbitmq.demo.adapter.in.rest.crud;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JsonDto {
    @JsonProperty("BankCode")
    private String BankCode;
    @JsonProperty("BankName")
    private String BankName;
}
