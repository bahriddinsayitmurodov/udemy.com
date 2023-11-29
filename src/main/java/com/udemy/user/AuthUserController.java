package com.udemy.user;

import com.udemy.common.jwt.JwtUtils;
import com.udemy.user.dto.UserCreateDto;
import com.udemy.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto createDto){
        UserResponseDto responseDto = userService.create(createDto);
        String token = jwtUtils.generateToken(createDto.getPhoneNumber());
        return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .header(HttpHeaders.AUTHORIZATION,"Bearer %s".formatted(token))
                 .body(responseDto);
    }
    @PostMapping("/send/sms/{id}")
    public ResponseEntity<?> sendVerificationCode(@PathVariable UUID id){
        String smsSendResponse = userService.sendVerificationCode(id);
        String phoneNumber = userService.phoneNumber(id);
        String token = jwtUtils.generateToken(phoneNumber);
        return ResponseEntity
                 .status(HttpStatus.OK)
                 .header(HttpHeaders.AUTHORIZATION,"Bearer %s".formatted(token))
                 .body(smsSendResponse);
    }
    @PostMapping("/verify/sms/{id}")
    public ResponseEntity<?> verifyPhone(@PathVariable UUID id,
                                         @RequestParam Integer verificationCode){

        String phoneNumber = userService.phoneNumber(id);
        String token = jwtUtils.generateToken(phoneNumber);

        UserResponseDto responseDto = userService.checkVerificationCode(id,verificationCode);

        return ResponseEntity
                 .status(HttpStatus.OK)
                 .header(HttpHeaders.AUTHORIZATION,"Bearer %s".formatted(token))
                 .body(responseDto);
    }
}


