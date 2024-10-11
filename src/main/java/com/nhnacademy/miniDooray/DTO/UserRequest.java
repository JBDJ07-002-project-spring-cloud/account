package com.nhnacademy.miniDooray.DTO;

public record UserRequest(Long userId, String userName, String userPassword, String userEmail) {
}
