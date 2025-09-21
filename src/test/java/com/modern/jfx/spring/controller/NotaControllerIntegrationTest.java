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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.test.context.ActiveProfiles("test")
public class NotaControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testCriarBuscarDeletarNota() throws Exception {
        // Criar usuário para associar à nota
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario Nota");
        usuario = usuarioRepository.save(usuario);
        // Criar nota
        Nota nota = new Nota();
        nota.setDescricao("Nota integração");
        nota.setUsuario(usuario);
        MvcResult result = mockMvc.perform(post("/notas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nota)))
                .andExpect(status().isOk())
                .andReturn();
        Nota salva = objectMapper.readValue(result.getResponse().getContentAsString(), Nota.class);
        Assertions.assertNotNull(salva.getId());
        // Buscar nota
        mockMvc.perform(get("/notas/" + salva.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Nota integração"));
        // Deletar nota
        mockMvc.perform(delete("/notas/" + salva.getId()))
                .andExpect(status().isNoContent());
        // Verificar remoção
        Assertions.assertFalse(notaRepository.findById(salva.getId()).isPresent());
    }
}
