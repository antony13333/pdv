package com.example.restauranteAPI.modelo_produto;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fechamento_nota")
public class Fechamneto_nota implements Serializable {
    private static final long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFN;
    @OneToMany
    @JoinColumn(name = "fechamento")
    private List<Nota> notas;
    @OneToOne
    @JoinColumn(name = "fechamento")
    private Modelo_Fechamento fechamento;

    public UUID getIdFN() {
        return idFN;
    }

    public void setIdFN(UUID idFN) {
        this.idFN = idFN;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public Modelo_Fechamento getFechamento() {
        return fechamento;
    }

    public void setFechamento(Modelo_Fechamento fechamento) {
        this.fechamento = fechamento;
    }
}


