package com.modern.jfx.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modern.jfx.spring.model.Nota;
import com.modern.jfx.spring.model.Usuario;
import com.modern.jfx.spring.repository.NotaRepository;
import com.modern.jfx.spring.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioNotasIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Test
    void testListarNotasDeUsuario() throws Exception {
        // Criar usuário
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario Listagem");
        usuario = usuarioRepository.save(usuario);
        // Criar notas
        Nota nota1 = new Nota();
        nota1.setDescricao("Nota A");
        nota1.setUsuario(usuario);
        Nota nota2 = new Nota();
        nota2.setDescricao("Nota B");
        nota2.setUsuario(usuario);
        notaRepository.saveAll(Arrays.asList(nota1, nota2));
        // Buscar usuário e verificar notas
        MvcResult result = mockMvc.perform(get("/usuarios/" + usuario.getId()))
                .andExpect(status().isOk())
                .andReturn();
        Usuario usuarioComNotas = objectMapper.readValue(result.getResponse().getContentAsString(), Usuario.class);
        Assertions.assertNotNull(usuarioComNotas.getNotas());
        Assertions.assertEquals(2, usuarioComNotas.getNotas().size());
    }
}
