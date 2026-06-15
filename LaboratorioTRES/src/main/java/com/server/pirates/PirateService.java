package com.server.pirates;

import com.server.pirates.dto.request.CreatePirateRequest;
import com.server.pirates.dto.request.UpdatePirateRequest;
import com.server.pirates.dto.response.PirateResponse;
import com.server.pirates.exceptions.ResourceNotFoundException;
import com.server.pirates.mappers.PirateMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class PirateService {

    private final PirateRepository pirateRepository;
    private final PirateMapper pirateMapper;

    public PirateService(PirateRepository pirateRepository, PirateMapper pirateMapper) {
        this.pirateRepository = pirateRepository;
        this.pirateMapper = pirateMapper;
    }

    public Page<PirateResponse> findAll(int page, int size) {
        return pirateRepository.findAll(PageRequest.of(page, size))
                .map(pirateMapper::toDto);
    }

    public PirateResponse findById(UUID id) {
        return pirateMapper.toDto(
                pirateRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Pirata no encontrado con id: " + id))
        );
    }

    @Transactional
    public PirateResponse create(CreatePirateRequest request) {
        return pirateMapper.toDto(
                pirateRepository.save(pirateMapper.toEntityCreate(request))
        );
    }

    @Transactional
    public PirateResponse update(UUID id, UpdatePirateRequest request) {
        findById(id);
        return pirateMapper.toDto(
                pirateRepository.save(pirateMapper.toEntityUpdate(request, id))
        );
    }

    @Transactional
    public PirateResponse delete(UUID id) {
        PirateResponse existing = findById(id);
        pirateRepository.deleteById(id);
        return existing;
    }
}