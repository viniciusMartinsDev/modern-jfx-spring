package com.modern.jfx.spring.repository;

import com.modern.jfx.spring.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
