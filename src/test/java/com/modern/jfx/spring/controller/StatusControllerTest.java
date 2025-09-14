package com.modern.jfx.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatusController.class)
public class StatusControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private JdbcTemplate jdbcTemplate;

	@Test
	void statusEndpointReturnsOkAndJson() throws Exception {
		// Mock das respostas do banco
		when(jdbcTemplate.queryForObject("SELECT version()", String.class))
			.thenReturn("PostgreSQL 17.6 (Debian 17.6-1.pgdg13+1)");

		when(jdbcTemplate.queryForObject("SHOW max_connections", Integer.class))
			.thenReturn(100);

		when(jdbcTemplate.queryForObject("SELECT count(*) FROM pg_stat_activity", Integer.class))
			.thenReturn(5);

		// Executa o teste
		mockMvc.perform(get("/api/v1/status"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.updated_at").exists())
			.andExpect(jsonPath("$.dependencies.database.version").value("17.6"))
			.andExpect(jsonPath("$.dependencies.database.max_connections").value(100))
			.andExpect(jsonPath("$.dependencies.database.opened_connections").value(5));
	}
}
