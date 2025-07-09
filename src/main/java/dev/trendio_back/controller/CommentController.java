package dev.trendio_back.controller;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.service.OldCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final OldCommentService oldCommentService;

    @GetMapping("/request/{requestId}")
    public List<CommentDto> getCommentsForRoute(@PathVariable("requestId") long requestId ) {
        //todo add pageable , think over tree load algorithm, look at vk, youtube, habr comments
        return oldCommentService.findAllForRequest(requestId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentDto addComment(@AuthenticationPrincipal AuthUser authUser, @RequestBody CommentDto comment) {
        return oldCommentService.create(comment, authUser);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@oldCommentService.isCreator(authentication.name,#id)")
    public CommentDto updateComment(@AuthenticationPrincipal AuthUser authUser, @RequestBody CommentDto comment, @PathVariable Long id) {
        return oldCommentService.update(comment, authUser, id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@oldCommentService.isCreator(authentication.name,#id)")
    public Long deleteComment(@PathVariable("id") long id) {
        return oldCommentService.delete(id);
    }
}
