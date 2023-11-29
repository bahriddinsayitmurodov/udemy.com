package com.udemy.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@AllArgsConstructor
public class SmsLoginDTO {

    private String email;

    private String password;
}
