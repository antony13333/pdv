package com.example.restauranteAPI.modelo_produto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_Comandas")
public class Modelo_Comanda extends RepresentationModel<Modelo_Comanda> implements Serializable {
    private static final long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id_Comanda;
    private boolean status;
    private float valor_Total;
    //@OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<ProdutoComanda> produtos = new ArrayList<>();
    private String Identificador;
    private List<Integer> quantidade_prod = new ArrayList<>();
    /*public void adicionarItem(ProdutoComanda item) {
        //this.produtos_comanda.add(item);
        this.produtos.add(item);
    }
    public List<ProdutoComanda> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoComanda> produtos) {
        this.produtos = produtos;
    }*/
    public void setQuantidade_prod(List<Integer> quantidade_prod) {
    this.quantidade_prod = quantidade_prod;
    }

    public List<Integer> getQuantidade_prod() {
        return quantidade_prod;
    }

    public void setQuantidae_prod(List<Integer> quantidade_prod) {
        this.quantidade_prod = quantidade_prod;
    }
    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String identificador) {
        Identificador = identificador;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public float getValor_Total() {
        return valor_Total;
    }

    public void setValor_Total(float valor_Total) {
        this.valor_Total = valor_Total;
    }

    public UUID getId_Comanda() {
        return Id_Comanda;
    }

    public void setId_Comanda(UUID id_Comanda) {
        Id_Comanda = id_Comanda;
    }

}
