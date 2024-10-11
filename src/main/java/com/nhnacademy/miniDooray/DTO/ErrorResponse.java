package com.nhnacademy.miniDooray.DTO;

import java.time.ZonedDateTime;


public record ErrorResponse(String title, int status, ZonedDateTime timestamp) {
    public ErrorResponse(String title, int status) {
        this(title, status, ZonedDateTime.now());
    }
}