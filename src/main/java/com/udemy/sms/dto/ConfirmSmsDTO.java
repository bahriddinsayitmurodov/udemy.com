package com.udemy.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfirmSmsDTO {

    private String code;

    private String newPassword;
}
