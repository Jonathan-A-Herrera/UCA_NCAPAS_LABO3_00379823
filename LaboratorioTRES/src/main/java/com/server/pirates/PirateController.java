package com.server.pirates;

import com.server.pirates.dto.request.CreatePirateRequest;
import com.server.pirates.dto.request.UpdatePirateRequest;
import com.server.pirates.dto.response.GeneralResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/pirates")
public class PirateController {

    private final PirateService pirateService;

    public PirateController(PirateService pirateService) {
        this.pirateService = pirateService;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return buildResponse("Pirates found", HttpStatus.OK, pirateService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getById(@PathVariable UUID id) {
        return buildResponse("Pirate found", HttpStatus.OK, pirateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> create(@RequestBody @Valid CreatePirateRequest request) {
        return buildResponse("Pirate created successfully", HttpStatus.CREATED, pirateService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> update(
            @PathVariable UUID id,
            @RequestBody UpdatePirateRequest request) {
        return buildResponse("Pirate updated successfully", HttpStatus.OK, pirateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@PathVariable UUID id) {
        return buildResponse("Pirate deleted successfully", HttpStatus.OK, pirateService.delete(id));
    }

    private ResponseEntity<GeneralResponse> buildResponse(String message, HttpStatus status, Object data) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        return ResponseEntity.status(status)
                .body(GeneralResponse.builder()
                        .uri(uri)
                        .message(message)
                        .status(status.value())
                        .time(LocalDateTime.now())
                        .data(data)
                        .build()
                );
    }
}