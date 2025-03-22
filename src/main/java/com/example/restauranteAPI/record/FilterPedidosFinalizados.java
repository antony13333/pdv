package com.example.restauranteAPI.record;

import java.time.LocalDate;

public record FilterPedidosFinalizados  (String Nome_produto , String Identificador, LocalDate Data_Criacao, LocalDate Data_Finalizado) {
}
