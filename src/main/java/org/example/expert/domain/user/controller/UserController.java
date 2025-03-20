package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.service.S3Service;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3Service s3Service;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/users")
    public void changePassword(@AuthenticationPrincipal AuthUser authUser, @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }

    @PostMapping("/users/uploadProfile")
    public ResponseEntity<String> uploadProfileImage(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam("file") MultipartFile file
    ) {
        try{
            String imageUrl = s3Service.uploadFile(file, "profile/image");
            userService.updateProfileImage(authUser.getId(),imageUrl);
            return ResponseEntity.ok(imageUrl);
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이미지 업로드 실패 : " + e.getMessage());
        }
    }

    @DeleteMapping("/users/deleteProfile")
    public ResponseEntity<String> deleteProfileImage (
        @AuthenticationPrincipal AuthUser authUser,
        @RequestParam("key") String key
    ){
        try {
            s3Service.deleteFile(key);
            userService.removeProfileImage(authUser.getId());
            return ResponseEntity.ok("이미지 삭제 성공");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이미지 삭제 실패 : " + e.getMessage());
        }
    }
}
