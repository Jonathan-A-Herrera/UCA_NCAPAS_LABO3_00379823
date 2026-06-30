package com.server.pirates;

import com.server.pirates.dto.request.CreatePirateRequest;
import com.server.pirates.dto.request.UpdatePirateRequest;
import com.server.pirates.dto.response.PirateResponse;
import com.server.pirates.exceptions.ResourceNotFoundException;
import com.server.pirates.mappers.PirateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PirateServiceTest {

    @Mock
    private PirateRepository pirateRepository;

    @Mock
    private PirateMapper pirateMapper;

    @InjectMocks
    private PirateService pirateService;

    private Pirate pirate;
    private PirateResponse pirateResponse;
    private UUID pirateId;

    @BeforeEach
    void setUp() {
        pirateId = UUID.randomUUID();

        pirate = Pirate.builder()
                .id(pirateId)
                .name("Monkey D. Luffy")
                .bounty(3000000000.0)
                .crew("Sombrero de Paja")
                .isAlive(true)
                .build();

        pirateResponse = PirateResponse.builder()
                .id(pirateId)
                .name("Monkey D. Luffy")
                .bounty(3000000000.0)
                .crew("Sombrero de Paja")
                .isAlive(true)
                .build();
    }

    @Test
    void shouldFindPirateByIdSuccessfully() {
        when(pirateRepository.findById(pirateId)).thenReturn(Optional.of(pirate));
        when(pirateMapper.toDto(pirate)).thenReturn(pirateResponse);

        PirateResponse result = pirateService.findById(pirateId);

        assertNotNull(result);
        assertEquals("Monkey D. Luffy", result.getName());
        verify(pirateRepository, times(1)).findById(pirateId);
    }

    @Test
    void shouldThrowExceptionWhenPirateNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(pirateRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            pirateService.findById(nonExistentId);
        });

        verify(pirateRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void shouldCreatePirateSuccessfully() {
        CreatePirateRequest request = new CreatePirateRequest();
        request.setName("Roronoa Zoro");
        request.setBounty(1500000000.0);
        request.setCrew("Sombrero de Paja");
        request.setIsAlive(true);

        when(pirateMapper.toEntityCreate(request)).thenReturn(pirate);
        when(pirateRepository.save(pirate)).thenReturn(pirate);
        when(pirateMapper.toDto(pirate)).thenReturn(pirateResponse);

        PirateResponse result = pirateService.create(request);

        assertNotNull(result);
        verify(pirateRepository, times(1)).save(pirate);
        verify(pirateMapper, times(1)).toEntityCreate(request);
    }

    @Test
    void shouldUpdatePirateSuccessfully() {
        UpdatePirateRequest request = new UpdatePirateRequest();
        request.setName("Luffy Actualizado");
        request.setBounty(5000000000.0);
        request.setCrew("Sombrero de Paja");
        request.setIsAlive(true);

        when(pirateRepository.findById(pirateId)).thenReturn(Optional.of(pirate));
        when(pirateMapper.toDto(pirate)).thenReturn(pirateResponse);
        when(pirateMapper.toEntityUpdate(request, pirateId)).thenReturn(pirate);
        when(pirateRepository.save(pirate)).thenReturn(pirate);

        PirateResponse result = pirateService.update(pirateId, request);

        assertNotNull(result);
        verify(pirateRepository, times(1)).save(pirate);
    }

    @Test
    void shouldDeletePirateSuccessfully() {
        when(pirateRepository.findById(pirateId)).thenReturn(Optional.of(pirate));
        when(pirateMapper.toDto(pirate)).thenReturn(pirateResponse);
        doNothing().when(pirateRepository).deleteById(pirateId);

        PirateResponse result = pirateService.delete(pirateId);

        assertNotNull(result);
        assertEquals(pirateId, result.getId());
        verify(pirateRepository, times(1)).deleteById(pirateId);
    }

    @Test
    void shouldFindAllPiratesWithPagination() {
        List<Pirate> pirateList = List.of(pirate);
        Page<Pirate> piratePage = new PageImpl<>(pirateList);

        when(pirateRepository.findAll(any(PageRequest.class))).thenReturn(piratePage);
        when(pirateMapper.toDto(pirate)).thenReturn(pirateResponse);

        Page<PirateResponse> result = pirateService.findAll(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pirateRepository, times(1)).findAll(any(PageRequest.class));
    }
}