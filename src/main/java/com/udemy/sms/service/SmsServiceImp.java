package com.udemy.sms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.sms.apiResponse.EskizApiResponse;
import com.udemy.sms.dto.SmsLoginDTO;
import com.udemy.sms.dto.SmsSendDTO;
import com.udemy.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Data
@RequiredArgsConstructor
public class SmsServiceImp {

    private final SmsService smsService;

    @Value("${security.sms.eskiz.email}")
    private String email;

    @Value("${security.sms.eskiz.password}")
    private String password;

    public String getSmsToken(){
        return smsService.getSmsToken(new SmsLoginDTO(email,password));
    }

    public String sendSms(SmsSendDTO smsSendDTO) {
        try {
//            String smsToken = getSmsToken();
//            ObjectMapper objectMapper = new ObjectMapper();
//            EskizApiResponse eskizApiResponse = objectMapper.readValue(smsToken, EskizApiResponse.class);
//            String token = eskizApiResponse.getData().getToken();
//            System.out.println("token = " + token);
            return smsService.sendSms("Bearer %s".formatted("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MDE2ODM0MTMsImlhdCI6MTY5OTA5MTQxMywicm9sZSI6InVzZXIiLCJzdWIiOiI0OTY0In0.rTceBhojZHmp_nWKo689aTv6_7lguHCtW4x-3_gLhhs"), smsSendDTO);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
