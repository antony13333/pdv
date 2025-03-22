package com.example.restauranteAPI.modelo_produto;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "Suprimentos")
public class Suprimento implements Serializable {
    private static final long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idSuprimento;
    private LocalDateTime horario;
    private float valor;
    @ManyToOne
    @JoinColumn(name = "fechamento")
    private Modelo_Fechamento fechamento;

    public UUID getIdSuprimento() {
        return idSuprimento;
    }

    public void setIdSuprimento(UUID idSuprimento) {
        this.idSuprimento = idSuprimento;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Modelo_Fechamento getFechamento() {
        return fechamento;
    }

    public void setFechamento(Modelo_Fechamento fechamento) {
        this.fechamento = fechamento;
    }
}
