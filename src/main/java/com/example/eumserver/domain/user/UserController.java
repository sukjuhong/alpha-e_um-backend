package com.example.eumserver.domain.user;

import com.example.eumserver.domain.jwt.PrincipalDetails;
import com.example.eumserver.domain.user.dto.UserResponse;
import com.example.eumserver.domain.user.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserResponse userResponse = UserMapper.INSTANCE.principalDetailsToUserResponse(principalDetails);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid final UserUpdateRequest request) {
        long userId = principalDetails.getUserId();
        User user = userService.updateInfo(userId, request);
        UserResponse userResponse = UserMapper.INSTANCE.userToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserInfo(@PathVariable long userId) {
        User user = userService.findById(userId);
        UserResponse userResponse = UserMapper.INSTANCE.userToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
}
