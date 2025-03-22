package com.example.restauranteAPI.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record Postpedido(@NotEmpty List<UUID> produtos, @NotNull UUID comanda, @NotEmpty List<Integer> quantidades) {
}
