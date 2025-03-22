package com.example.restauranteAPI.modelo_produto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Tb_Fechamentos")
public class Modelo_Fechamento implements Serializable {
    private static final long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID IdFechamento;
    private LocalDateTime DataFechamento;
    private float Credito;
    private float Debito;
    private float Dinheiro;
    private float Pix;
    private float QuebraCredito;
    private float QuebraDebito;
    private float QuebraDinheiro;
    private float QuebraPix;
    @OneToOne(mappedBy = "idFechamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private AberturaFechamento relacao;

    public AberturaFechamento getRelacao() {
        return relacao;
    }

    public void setRelacao(AberturaFechamento relacao) {
        this.relacao = relacao;
    }

    public UUID getIdFechamento() {
        return IdFechamento;
    }

    public void setIdFechamento(UUID idFechamento) {
        IdFechamento = idFechamento;
    }

    public LocalDateTime getDataFechamento() {
        return DataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        DataFechamento = dataFechamento;
    }

    public float getCredito() {
        return Credito;
    }

    public void setCredito(float credito) {
        Credito = credito;
    }

    public float getDebito() {
        return Debito;
    }

    public void setDebito(float debito) {
        Debito = debito;
    }

    public float getDinheiro() {
        return Dinheiro;
    }

    public void setDinheiro(float dinheiro) {
        Dinheiro = dinheiro;
    }

    public float getPix() {
        return Pix;
    }

    public void setPix(float pix) {
        Pix = pix;
    }

    public float getQuebraCredito() {
        return QuebraCredito;
    }

    public void setQuebraCredito(float quebraCredito) {
        QuebraCredito = quebraCredito;
    }

    public float getQuebraDebito() {
        return QuebraDebito;
    }

    public void setQuebraDebito(float quebraDebito) {
        QuebraDebito = quebraDebito;
    }

    public float getQuebraDinheiro() {
        return QuebraDinheiro;
    }

    public void setQuebraDinheiro(float quebraDinheiro) {
        QuebraDinheiro = quebraDinheiro;
    }

    public float getQuebraPix() {
        return QuebraPix;
    }

    public void setQuebraPix(float quebraPix) {
        QuebraPix = quebraPix;
    }
}
