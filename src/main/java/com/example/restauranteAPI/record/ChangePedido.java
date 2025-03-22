package com.example.restauranteAPI.record;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ChangePedido(@NotNull List<UUID> Idpedido, @NotNull String Newstatus) {
}
