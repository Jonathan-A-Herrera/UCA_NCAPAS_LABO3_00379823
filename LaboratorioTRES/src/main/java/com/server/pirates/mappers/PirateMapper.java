package com.server.pirates.mappers;

import com.server.pirates.Pirate;
import com.server.pirates.dto.request.CreatePirateRequest;
import com.server.pirates.dto.request.UpdatePirateRequest;
import com.server.pirates.dto.response.PirateResponse;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class PirateMapper {

    public Pirate toEntityCreate(CreatePirateRequest request) {
        return Pirate.builder()
                .name(request.getName())
                .bounty(request.getBounty())
                .crew(request.getCrew())
                .isAlive(request.getIsAlive())
                .build();
    }

    public Pirate toEntityUpdate(UpdatePirateRequest request, UUID id) {
        return Pirate.builder()
                .id(id)
                .name(request.getName())
                .bounty(request.getBounty())
                .crew(request.getCrew())
                .isAlive(request.getIsAlive())
                .build();
    }

    public PirateResponse toDto(Pirate pirate) {
        return PirateResponse.builder()
                .id(pirate.getId())
                .name(pirate.getName())
                .bounty(pirate.getBounty())
                .crew(pirate.getCrew())
                .isAlive(pirate.getIsAlive())
                .build();
    }
}