package com.nhnacademy.miniDooray.service.impl;

import com.nhnacademy.miniDooray.DTO.UserPartialUpdateRequest;
import com.nhnacademy.miniDooray.DTO.UserRegistrationRequest;
import com.nhnacademy.miniDooray.DTO.UserRequest;
import com.nhnacademy.miniDooray.DTO.UserUpdateRequest;
import com.nhnacademy.miniDooray.entity.User;
import com.nhnacademy.miniDooray.exception.UserNotFoundException;
import com.nhnacademy.miniDooray.repository.UserRepository;
import com.nhnacademy.miniDooray.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 생성
    @Override
    public User registUser(UserRegistrationRequest userRequest){
        
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(userRequest.userPassword());

        User newUser = new User(userRequest.userName()
                ,encryptedPassword
                ,userRequest.userEmail());

        log.info("user save ID: {}", newUser.getUserId());
        return userRepository.save(newUser);
    }

    // 유저 조회
    @Override
    public User getUser(Long userId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundException(userId) );

        log.info("getUser ID: {}" , targetUser.getUserId());
        return targetUser;
    }

    // 모든 유저 조회
    @Override
    public List<User> getAllUsers() {
        log.info("get All users");
        return userRepository.findAll();
    }

    // 유저 정보 수정
    @Override
    public void updateUser(UserUpdateRequest userRequest) {
        User updateUser = userRepository.findById(userRequest.userId())
                .orElseThrow( () -> new UserNotFoundException(userRequest.userId()) );
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(userRequest.userPassword());

        updateUser.setUserName(userRequest.userName());
        updateUser.setUserPassword(encryptedPassword);
        updateUser.setUserEmail(userRequest.userEmail());

        userRepository.save(updateUser);
        log.info("user updated ID: {}" ,updateUser.getUserId());
    }

    // 유저 부분 업데이트
    @Override
    public void partialUpdateUser(Long userId, UserPartialUpdateRequest userRequest) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // 필드가 null이 아닌 경우에만 업데이트
        if (userRequest.userName() != null) {
            existingUser.setUserName(userRequest.userName());
        }
        if (userRequest.userPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(userRequest.userPassword());
            existingUser.setUserPassword(encryptedPassword);
        }
        if (userRequest.userEmail() != null) {
            existingUser.setUserEmail(userRequest.userEmail());
        }

        userRepository.save(existingUser);
        log.info("User with ID {} has been partially updated", userId);
    }

    // 유저 삭제
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundException(userId) );

        log.info("User deleted ID: {}", userId);
        userRepository.delete(user);
    }

    // 입력된 비밀번호와 암호화 된 비밀번호 비교 (로그인 시 검증용)
    @Override
    public boolean comparePassword(String rawPassword, String storedEncryptedPassword) {
        return passwordEncoder.matches(rawPassword, storedEncryptedPassword);  // 비밀번호 검증
    }
}
