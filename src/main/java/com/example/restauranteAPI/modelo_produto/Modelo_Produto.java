package com.example.restauranteAPI.modelo_produto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
@Entity
@Table(name = "Produtos-restaurante")
public class Modelo_Produto extends RepresentationModel<Modelo_Produto> implements Serializable {
    private static final long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID_produto;
    private String nome_produto;
    private float valor_produto;
    private boolean vai_para_cozinha;
    //getters e setters
    public boolean isVai_para_cozinha() {
        return vai_para_cozinha;
    }

    public void setVai_para_cozinha(boolean vai_para_cozinha) {
        this.vai_para_cozinha = vai_para_cozinha;
    }

    public float getValor_produto() {
        return valor_produto;
    }

    public void setValor_produto(float valor_produto) {
        this.valor_produto = valor_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public UUID getID_produto() {
        return ID_produto;
    }

    public void setID_produto(UUID ID_produto) {
        this.ID_produto = ID_produto;
    }
}
