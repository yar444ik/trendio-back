package dev.trendio_back.controller;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/request/{requestId}")
    public List<CommentDto> getCommentsForRoute(@PathVariable("requestId") long requestId ) {
        return commentService.findAllForRequest(requestId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentDto addComment(@RequestBody CommentDto comment) {
        String username = AuthenticationController.getCurrentUsername();
        return commentService.create(comment, username);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@commentService.isCreator(authentication.name,#id)")
    public CommentDto updateComment(@RequestBody CommentDto comment, @PathVariable Long id) {
        String username = AuthenticationController.getCurrentUsername();
        return commentService.update(comment, username, id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@commentService.isCreator(authentication.name,#id)")
    public Long deleteComment(@PathVariable("id") long id) {
        return commentService.delete(id);
    }
}
