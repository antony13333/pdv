package com.example.restauranteAPI.record;


import jakarta.validation.constraints.NotNull;


import java.util.UUID;

public record AddProdComanda(@NotNull UUID produtosIds) {
}
