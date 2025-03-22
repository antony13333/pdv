package com.example.restauranteAPI.record;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record Record_fecharComanda(@NotEmpty List<Float> valores , @NotEmpty List<String> FormaDePagamento , @NotNull LocalDateTime dataTime) {
}
