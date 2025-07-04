package dev.trendio_back.controller;

import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.service.AvatarService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/avatar")
@AllArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @PostMapping("/upload")
    public String uploadAvatar(@AuthenticationPrincipal AuthUser authUser, @RequestParam("file") MultipartFile file) {
        return avatarService.uploadFile(file, authUser.getId());
    }

    @GetMapping("/get/{filename}")
    public String getAvatarUrl(@PathVariable String filename) {
        return avatarService.getFileUrl(filename);
    }
}
