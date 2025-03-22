package com.example.restauranteAPI.modelo_produto;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "coamndas_produtos")
public class Comandas_produtos implements Serializable {
    private static final long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_unico;
    @ManyToOne
    @JoinColumn(name = "Id_Comanda", nullable = false)
    private Modelo_Comanda idcomanda;
    @ManyToOne
    @JoinColumn(name = "idProdutoC", nullable = false)
    private Modelo_Produto id_produto;
    public UUID getId_unico() {
        return id_unico;
    }

    public void setId_unico(UUID id_unico) {
        this.id_unico = id_unico;
    }

    public Modelo_Comanda getIdcomanda() {
        return idcomanda;
    }

    public void setIdcomanda(Modelo_Comanda idcomanda) {
        this.idcomanda = idcomanda;
    }

    public Modelo_Produto getId_produto() {
        return id_produto;
    }

    public void setId_produto(Modelo_Produto id_produto) {
        this.id_produto = id_produto;
    }
}
