package com.modern.jfx.spring.service;

import com.modern.jfx.spring.model.Nota;
import com.modern.jfx.spring.model.Usuario;
import com.modern.jfx.spring.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceNotasTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testListarNotasDeUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(20L);
        usuario.setNome("Ana");
        Nota nota1 = new Nota();
        nota1.setDescricao("Nota 1");
        Nota nota2 = new Nota();
        nota2.setDescricao("Nota 2");
        usuario.setNotas(Arrays.asList(nota1, nota2));
        Mockito.when(usuarioRepository.findById(20L)).thenReturn(Optional.of(usuario));
        Optional<Usuario> resultado = usuarioService.findById(20L);
        Assertions.assertTrue(resultado.isPresent());
        List<Nota> notas = resultado.get().getNotas();
        Assertions.assertEquals(2, notas.size());
        Assertions.assertEquals("Nota 1", notas.get(0).getDescricao());
        Assertions.assertEquals("Nota 2", notas.get(1).getDescricao());
    }
}
