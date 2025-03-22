package com.example.restauranteAPI.modelo_produto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Tb_abertura")
public class ModeloAbertura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID IdAbertura;
    private LocalDateTime dataAbertura;
    private float DinheiroAbertura;
    @OneToOne(mappedBy = "idAbertura", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private AberturaFechamento relacao;

    public AberturaFechamento getRelacao() {
        return relacao;
    }

    public void setRelacao(AberturaFechamento relacao) {
        this.relacao = relacao;
    }

    public UUID getIdAbertura() {
        return IdAbertura;
    }

    public void setIdAbertura(UUID idAbertura) {
        IdAbertura = idAbertura;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public float getDinheiroAbertura() {
        return DinheiroAbertura;
    }

    public void setDinheiroAbertura(float dinheiroAbertura) {
        DinheiroAbertura = dinheiroAbertura;
    }
}
