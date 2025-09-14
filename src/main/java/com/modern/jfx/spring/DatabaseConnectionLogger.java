package com.modern.jfx.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionLogger implements CommandLineRunner {

	@Autowired
	private final JdbcTemplate jdbcTemplate;

	public DatabaseConnectionLogger(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... args) {
		try {
			jdbcTemplate.execute("SELECT 1");
			System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso!");
		} catch (Exception e) {
			System.err.println("❌ Falha ao conectar ao banco de dados: " + e.getMessage());
		}
	}
}
