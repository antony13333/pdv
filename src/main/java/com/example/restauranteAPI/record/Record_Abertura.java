package com.example.restauranteAPI.record;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record   Record_Abertura(@NotNull float Dinheiro_abertura ) {
}
