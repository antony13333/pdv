package com.example.restauranteAPI.modelo_produto;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Pedidos")
public class Pedidos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID IdPedido;

    @ManyToOne
    @JoinColumn(name = "Id_Comanda", nullable = false)
    private Modelo_Comanda idcomanda;

    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false)
    private Modelo_Produto id_produto;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime HoraCriado;

    private LocalDateTime HoraFinalizado;

    private String status;

    private int Quantidade;

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public LocalDateTime getHoraFinalizado() {
        return HoraFinalizado;
    }

    public void setHoraFinalizado(LocalDateTime horaFinalizado) {
        HoraFinalizado = horaFinalizado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getIdPedido() {
        return IdPedido;
    }

    public void setIdPedido(UUID idPedido) {
        IdPedido = idPedido;
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

    public LocalDateTime getHoraCriado() {
        return HoraCriado;
    }

    public void setHoraCriado(LocalDateTime horaCriado) {
        HoraCriado = horaCriado;
    }
}
