package com.modern.jfx.spring.service;

import com.modern.jfx.spring.model.Nota;
import com.modern.jfx.spring.repository.NotaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class NotaServiceDeleteTest {
    @Mock
    private NotaRepository notaRepository;

    @InjectMocks
    private NotaService notaService;

    @Test
    void testDeletarNota() {
        Long notaId = 5L;
        Mockito.doNothing().when(notaRepository).deleteById(notaId);
        notaService.deleteById(notaId);
        Mockito.verify(notaRepository, Mockito.times(1)).deleteById(notaId);
    }
}
