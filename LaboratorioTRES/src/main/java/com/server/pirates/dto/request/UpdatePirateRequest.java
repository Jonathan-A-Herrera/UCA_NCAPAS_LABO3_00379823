package com.server.pirates.dto.request;

import lombok.Data;

@Data
public class UpdatePirateRequest {
    private String name;
    private Double bounty;
    private String crew;
    private Boolean isAlive;
}