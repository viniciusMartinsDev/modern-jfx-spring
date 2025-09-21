package com.modern.jfx.spring.service;

import com.modern.jfx.spring.model.Nota;
import com.modern.jfx.spring.model.Usuario;
import com.modern.jfx.spring.repository.NotaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class NotaServiceTest {
    @Mock
    private NotaRepository notaRepository;

    @InjectMocks
    private NotaService notaService;

    @Test
    void testCriarNota() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Maria");
        Nota nota = new Nota();
        nota.setDescricao("Nota de teste");
        nota.setUsuario(usuario);
        Mockito.when(notaRepository.save(Mockito.any(Nota.class))).thenReturn(nota);
        Nota salva = notaService.save(nota);
        Assertions.assertEquals("Nota de teste", salva.getDescricao());
        Assertions.assertEquals("Maria", salva.getUsuario().getNome());
    }
}
