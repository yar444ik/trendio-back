package dev.trendio_back.controller;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/requests")
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<RequestDto> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return requestService.findAll(pageable);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestDto createRequest(@AuthenticationPrincipal AuthUser authUser, @RequestBody RequestDto newRequest) {
        return requestService.create(newRequest, authUser);
    }

    @DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteRequest(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long id) {
        return requestService.delete(id, authUser);
    }

    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestDto changeRequest(@AuthenticationPrincipal AuthUser authUser, @RequestBody RequestDto newRequest, @PathVariable Long id) {
        return requestService.update(newRequest, authUser, id);
    }
}
