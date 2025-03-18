package dev.trendio_back.controller;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/main")
@AllArgsConstructor
public class MainPageController {
    private final RequestService requestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<RequestDto> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return requestService.findAll(page, size, sortField, sortDirection);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestDto createRequest(@RequestBody RequestDto newRequest) {
        return requestService.create(newRequest);
    }

    @DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteRequest(@PathVariable Long id) { return requestService.delete(id); }

    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestDto changeRequest(@RequestBody RequestDto newRequest, @PathVariable Long id) {
        return requestService.update(newRequest, id);
    }
}
