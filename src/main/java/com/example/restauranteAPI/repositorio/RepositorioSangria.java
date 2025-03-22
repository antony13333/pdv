package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Nota;
import com.example.restauranteAPI.modelo_produto.Sangria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RepositorioSangria extends JpaRepository<Sangria , UUID> {
    List<Sangria> findByHorarioBetween(LocalDateTime inicio, LocalDateTime fim);
}
