package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.AberturaFechamento;
import com.example.restauranteAPI.modelo_produto.ModeloAbertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface RepositorioAberFech extends JpaRepository<AberturaFechamento , UUID> {
    List<AberturaFechamento> findByidAbertura(ModeloAbertura abertura);

}
