package com.example.restauranteAPI.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record estrutura(@NotBlank String nome_produto , @NotNull float valor_produto , @NotNull boolean vai_para_cozinha) {
}
