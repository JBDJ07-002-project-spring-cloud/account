package com.nhnacademy.miniDooray.controller;

import com.nhnacademy.miniDooray.DTO.UserPartialUpdateRequest;
import com.nhnacademy.miniDooray.DTO.UserRegistrationRequest;
import com.nhnacademy.miniDooray.DTO.UserRequest;
import com.nhnacademy.miniDooray.DTO.UserUpdateRequest;
import com.nhnacademy.miniDooray.entity.User;
import com.nhnacademy.miniDooray.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    User 엔티티의 컨트롤러입니다.(API)
    기능
    POST    / 유저 가입
    GET     / 전체 유저 정보 가져오기
    GET     / 단일 유저 정보 가져오기
    PUT     / 유저 수정
    DELETE  / 유저 삭제

    +
    HTTP 201 Created 는 요청이 성공적으로 처리되었으며
    자원이 생성되었음을 나타내는 성공 상태 응답 코드
 */

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/users")
public class UserContoller {

    private final UserService userService;

    // 사용자 등록
    @PostMapping("/register")
    public ResponseEntity<User> registUser(@RequestBody UserRegistrationRequest userRequest){
        User newUser = userService.registUser(userRequest); // 중복 시 UserAlredyExites 예외 처리 됨

        //HTTP 상태코드 201, user 객체 반환
        log.info("registerUser() User 추가 됨.");
        // Map<String, String> response = new HashMap<>();
        // response body 만들 때 형식 지정해주기
        
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);

    }

    // 단일 사용자 조회
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId){
        User targetUser = userService.getUser(userId);

        // HTTP 200, user 반환
        log.info("getUser() 단일 User 조회.");
        return ResponseEntity.ok(targetUser);
    }

    // 전체 사용자 조회
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        
        // HTTP 200, User List 반환
        return ResponseEntity.ok(users);
    }

    // 사용자 정보 수정
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userRequest){
        userService.updateUser(userRequest);

        return ResponseEntity.ok().build();
    }

    // 사용자 부분 업데이트
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> partialUpdateUser(@PathVariable Long userId, @RequestBody UserPartialUpdateRequest userRequest) {
        userService.partialUpdateUser(userId, userRequest);
        // HTTP 204 (No Content) 반환
        return ResponseEntity.noContent().build();
    }

    // 사용자 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
