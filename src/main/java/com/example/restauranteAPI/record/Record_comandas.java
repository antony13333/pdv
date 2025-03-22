package com.example.restauranteAPI.record;

import com.example.restauranteAPI.modelo_produto.Modelo_Produto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record Record_comandas(@NotEmpty List<UUID> produtosIds, String Identificador, @NotEmpty List<Integer> quantidade_prod) {
}
