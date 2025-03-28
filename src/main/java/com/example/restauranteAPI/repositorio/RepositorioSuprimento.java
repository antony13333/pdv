package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Nota;
import com.example.restauranteAPI.modelo_produto.Suprimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface RepositorioSuprimento extends JpaRepository<Suprimento , UUID> {
    List<Suprimento> findByHorarioBetween(LocalDateTime inicio, LocalDateTime fim);
}
