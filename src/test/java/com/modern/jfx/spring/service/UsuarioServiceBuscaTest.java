package com.modern.jfx.spring.service;

import com.modern.jfx.spring.model.Usuario;
import com.modern.jfx.spring.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceBuscaTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testBuscarUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);
        usuario.setNome("Carlos");
        Mockito.when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        Optional<Usuario> resultado = usuarioService.findById(10L);
        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals("Carlos", resultado.get().getNome());
    }
}
