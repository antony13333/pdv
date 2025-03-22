package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Fechamneto_nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Fechamnetos_notasR extends JpaRepository<Fechamneto_nota, UUID> {
}
