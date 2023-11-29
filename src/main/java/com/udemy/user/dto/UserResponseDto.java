package com.udemy.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserResponseDto extends UserBaseDto{

    private UUID id;
    private boolean isPhoneNumberVerified;
}
