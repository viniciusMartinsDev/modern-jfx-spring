package com.modern.jfx.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modern.jfx.spring.model.Usuario;
import com.modern.jfx.spring.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testCriarBuscarDeletarUsuario() throws Exception {
        // Criar usuário
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Integracao");
        MvcResult result = mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andReturn();
        Usuario salvo = objectMapper.readValue(result.getResponse().getContentAsString(), Usuario.class);
        Assertions.assertNotNull(salvo.getId());
        // Buscar usuário
        mockMvc.perform(get("/usuarios/" + salvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Integracao"));
        // Deletar usuário
        mockMvc.perform(delete("/usuarios/" + salvo.getId()))
                .andExpect(status().isNoContent());
        // Verificar remoção
        Assertions.assertFalse(usuarioRepository.findById(salvo.getId()).isPresent());
    }
}
