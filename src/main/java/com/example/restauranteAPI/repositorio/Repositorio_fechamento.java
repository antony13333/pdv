package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Modelo_Fechamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Repositorio_fechamento extends JpaRepository<Modelo_Fechamento , UUID> {
}
