package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.ModeloAbertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface Repositorio_abertura extends JpaRepository<ModeloAbertura , UUID> {
    List<ModeloAbertura> findByDataAbertura(LocalDate data);
}
