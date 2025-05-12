package dev.trendio_back.controller;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagDto> getAllTags() { return tagService.findAll(); }

    @PostMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public TagDto createTag(@RequestBody TagDto newTag) { return tagService.create(newTag); }
}
