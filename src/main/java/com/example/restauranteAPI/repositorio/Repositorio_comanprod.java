package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Comandas_produtos;
import com.example.restauranteAPI.modelo_produto.Modelo_Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface Repositorio_comanprod  extends JpaRepository<Comandas_produtos , UUID> {
    List<Comandas_produtos> findByIdcomanda(Modelo_Comanda comanda);
}
