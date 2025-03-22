package com.example.restauranteAPI.modelo_produto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "Tabela_Notas")
public class Nota implements Serializable {
    private static final long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_Nota;
    @OneToOne
    @JoinColumn(name = "Id_comanda" , nullable = false )
    private Modelo_Comanda idComanda;
    private LocalDateTime data;
    private float Valor_total;
    private List<String> formapagamento = new ArrayList<>();
    private List<Float> valores = new ArrayList<>();
    private List<String> nomesProd = new ArrayList<>();
    private List<Float> valorUni = new ArrayList<>();
    private List<Integer> quantidades = new ArrayList<>();
    private List<Float> valor = new ArrayList<>();
    public String formatar(Nota nota){
        List<String> formatado = new ArrayList<>();
        String formatacao = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<String> stringValores = nota.getValores().stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        List<String> valoresUniStr = nota.getValorUni().stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        List<String> valorSTR = nota.getValor().stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        for (int i = 0 ; i<nota.getValores().size() ; i++){
            String formapag = nota.formapagamento.get(i).toUpperCase();
           formatado.add(stringValores.get(i)+formapag);
        }
        for (int i = 0 ; i<nota.getNomesProd().size();i++){
            formatacao+= "nome: " +nota.getNomesProd().get(i)+"\n"+
                         "valor Unitário: "+ valoresUniStr.get(i)+"\n"+
                         "quantidade: "+nota.getQuantidades().get(i).toString()+"\n"+
                         "valor * quantidade: "+valorSTR.get(i)+"\n";
        }
        return formatacao+"Valor Total: "+String.format("%.2f" , nota.getValor_total())+"\n"+
                "Forma(s) de Pagamento:"+"\n"+String.join("\n" , formatado)+"\n"+
                "ID Nota: "+nota.getId_Nota().toString() +"\n"+
                "Hora e Data: "+nota.getData().format(formatter);
    }

    public Modelo_Comanda getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Modelo_Comanda idComanda) {
        this.idComanda = idComanda;
    }

    public List<String> getNomesProd() {
        return nomesProd;
    }

    public void setNomesProd(List<String> nomesProd) {
        this.nomesProd = nomesProd;
    }

    public List<Float> getValorUni() {
        return valorUni;
    }

    public void setValorUni(List<Float> valorUni) {
        this.valorUni = valorUni;
    }

    public List<Integer> getQuantidades() {
        return quantidades;
    }

    public void setQuantidades(List<Integer> quantidades) {
        this.quantidades = quantidades;
    }

    public List<Float> getValor() {
        return valor;
    }

    public void setValor(List<Float> valor) {
        this.valor = valor;
    }

    public float getValor_total() {
        return Valor_total;
    }

    public void setValor_total(float valor_total) {
        Valor_total = valor_total;
    }

    public List<Float> getValores() {
        return valores;
    }

    public void setValores(List<Float> valores) {
        this.valores = valores;
    }

    public List<String> getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(List<String> formapagamento) {
        this.formapagamento = formapagamento;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public UUID getId_Nota() {
        return id_Nota;
    }

    public void setId_Nota(UUID id_Nota) {
        this.id_Nota = id_Nota;
    }
}
