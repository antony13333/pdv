package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Modelo_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Repositorio00 extends JpaRepository<Modelo_Produto , UUID> {
}
