package com.udemy.sms.service;

import com.udemy.sms.dto.SmsLoginDTO;
import com.udemy.sms.dto.SmsSendDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "sendingSms" , url = "notify.eskiz.uz")
public interface SmsService {

    @PostMapping("/api/auth/login")
    String getSmsToken(@RequestBody SmsLoginDTO smsLoginDTO);

    @PostMapping("/api/message/sms/send")
    String sendSms(@RequestHeader(name = "Authorization") String token,
                   @RequestBody SmsSendDTO smsSendDTO);
}
