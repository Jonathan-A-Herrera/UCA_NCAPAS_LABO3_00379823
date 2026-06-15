package com.server.pirates.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreatePirateRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotNull(message = "La recompensa no puede estar vacía")
    @Positive(message = "La recompensa debe ser positiva")
    private Double bounty;

    @NotBlank(message = "La tripulación no puede estar vacía")
    private String crew;

    @NotNull(message = "Debe especificarse si el pirata está vivo")
    private Boolean isAlive;
}