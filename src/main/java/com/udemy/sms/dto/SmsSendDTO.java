package com.udemy.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SmsSendDTO {

    @JsonProperty("mobile_phone")
    private String mobilePhone;

    private String message;

    private final String from = "4546";
}
