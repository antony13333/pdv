package com.example.restauranteAPI.record;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record Record_Fechamento(@NotNull float Credito ,
                                @NotNull float Debito, @NotNull float Dinheiro, @NotNull float Pix) {
}
