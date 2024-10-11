package com.nhnacademy.miniDooray.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(Long id) {
        super(String.valueOf(id));

    }
}
