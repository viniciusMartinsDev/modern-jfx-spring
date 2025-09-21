package com.modern.jfx.spring.controller;

import com.modern.jfx.spring.model.Nota;
import com.modern.jfx.spring.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {
    @Autowired
    private NotaService notaService;

    @GetMapping
    public List<Nota> getAllNotas() {
        return notaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> getNotaById(@PathVariable Long id) {
        return notaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Nota createNota(@RequestBody Nota nota) {
        return notaService.save(nota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable Long id) {
        notaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
