package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Modelo_Comanda;
import com.example.restauranteAPI.modelo_produto.Modelo_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface Repositorio_comandas extends JpaRepository<Modelo_Comanda, UUID> {
    List<Modelo_Comanda> findByStatus(boolean status);
}
