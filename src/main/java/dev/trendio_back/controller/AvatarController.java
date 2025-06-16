package dev.trendio_back.controller;

import dev.trendio_back.service.AvatarService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/avatar")
@AllArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return avatarService.uploadFile(file, username);
    }

    @GetMapping("/get/{filename}")
    public String getAvatarUrl(@PathVariable String filename) {
        return avatarService.getFileUrl(filename);
    }
}
