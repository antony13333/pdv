package com.example.restauranteAPI.modelo_produto;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "AberturaFechamento")
public class AberturaFechamento implements Serializable {
    private static final long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID IdFechamento_Aberura;
    private LocalDate data;
    @OneToOne
    @JoinColumn(name = "IdAbertura" )
    private ModeloAbertura idAbertura;
    @OneToOne
    @JoinColumn(name = "IdFechamento" )
    private Modelo_Fechamento idFechamento;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public UUID getIdFechamento_Aberura() {
        return IdFechamento_Aberura;
    }

    public void setIdFechamento_Aberura(UUID idFechamento_Aberura) {
        IdFechamento_Aberura = idFechamento_Aberura;
    }

    public ModeloAbertura getIdAbertura() {
        return idAbertura;
    }

    public void setIdAbertura(ModeloAbertura idAbertura) {
        this.idAbertura = idAbertura;
    }

    public Modelo_Fechamento getIdFechamento() {
        return idFechamento;
    }

    public void setIdFechamento(Modelo_Fechamento idFechamento) {
        this.idFechamento = idFechamento;
    }
}
