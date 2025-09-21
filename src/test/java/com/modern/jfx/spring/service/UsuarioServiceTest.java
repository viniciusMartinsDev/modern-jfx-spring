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
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testCriarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("João");
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Usuario salvo = usuarioService.save(usuario);
        Assertions.assertEquals("João", salvo.getNome());
    }
}
