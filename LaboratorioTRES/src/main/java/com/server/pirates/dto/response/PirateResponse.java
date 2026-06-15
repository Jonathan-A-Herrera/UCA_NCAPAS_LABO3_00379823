package com.server.pirates.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class PirateResponse {
    private UUID id;
    private String name;
    private Double bounty;
    private String crew;
    private Boolean isAlive;
}