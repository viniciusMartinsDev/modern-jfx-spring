package com.modern.jfx.spring.repository;

import com.modern.jfx.spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
