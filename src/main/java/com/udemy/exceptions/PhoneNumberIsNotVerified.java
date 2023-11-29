package com.udemy.exceptions;

public class PhoneNumberIsNotVerified extends RuntimeException{
    public PhoneNumberIsNotVerified(String message) {
        super(message);
    }
}
