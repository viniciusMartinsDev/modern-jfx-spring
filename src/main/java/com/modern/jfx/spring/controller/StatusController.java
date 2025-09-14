package com.modern.jfx.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1")
public class StatusController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/status")
	public Map<String, Object> status() {
		Map<String, Object> status = new HashMap<>();
		status.put("updated_at", Instant.now());

		String versionFull = jdbcTemplate.queryForObject("SELECT version()", String.class);
		String version = "";
		Matcher matcher = Pattern.compile("PostgreSQL ([\\d\\.]+)").matcher(versionFull);

		if (matcher.find()) {
			version = matcher.group(1);
		}


		Map<String, Object> db = new HashMap<>();
		db.put("version", version);
		db.put("max_connections", jdbcTemplate.queryForObject("SHOW max_connections", Integer.class));
		db.put("opened_connections", jdbcTemplate.queryForObject("SELECT count(*) FROM pg_stat_activity", Integer.class));

		Map<String, Object> dependencies = new HashMap<>();
		dependencies.put("database", db);

		status.put("dependencies", dependencies);

		return status;
	}
}
