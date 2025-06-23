package dev.trendio_back.controller;

import dev.trendio_back.dto.LikeDto;
import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    
    private final LikeService likeService;

    @PostMapping("/{requestId}")
    public LikeDto likeRequest(@AuthenticationPrincipal AuthUser authUser,
                               @PathVariable Long requestId) {
        return likeService.likeRequest(authUser.getId(), requestId);
    }

    @DeleteMapping("/{requestId}")
    public void unlikeRequest(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long requestId) {
        likeService.unlikeRequest(authUser.getId(), requestId);
    }

    @GetMapping("/request/{requestId}")
    public List<LikeDto> getLikesForRequest(@PathVariable Long requestId) {
        return likeService.getLikesForRequest(requestId);
    }
}
