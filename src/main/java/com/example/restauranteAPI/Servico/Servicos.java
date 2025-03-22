package com.example.restauranteAPI.Servico;

import com.example.restauranteAPI.modelo_produto.Comandas_produtos;
import com.example.restauranteAPI.modelo_produto.Modelo_Comanda;
import com.example.restauranteAPI.modelo_produto.Modelo_Produto;
import com.example.restauranteAPI.repositorio.Repositorio_comanprod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class Servicos {
    @Autowired
    Repositorio_comanprod repositorioComanprod;
    public void calcularValorTotal(Modelo_Comanda comanda) {
        List<Comandas_produtos> comandasProdutos = repositorioComanprod.findByIdcomanda(comanda);
        List<Modelo_Produto> produtos = new ArrayList<>();
        for (int i = 0 ; i<comandasProdutos.size() ; i++){
            produtos.add(comandasProdutos.get(i).getId_produto());
        }
        float total = 0;
        for (int i = 0; i <produtos.size()/*comanda.getProdutos_comanda().size()*/; i++) {
            //Modelo_Produto produto = comanda.getProdutos_comanda().get(i);
            int quantidade = comanda.getQuantidade_prod().get(i);
            total+= produtos.get(i).getValor_produto()*quantidade;
            //total += produto.getValor_produto() * quantidade;
        }

        comanda.setValor_Total(total);
    }
    /*
    var Modelo_Fechamento fechamento;
    fechamento.setCredito(repositorio.Credito)
    fechamento.setPix(repositorio.Pix)
    fechamento.setDinheiro(repositorio.Dinheiro)
    fechamento.setDebito(repositorio.Debito)
    List<Nota> listaNotas = Repositorynotas.getALL();
    for(int i = 0; i<listaNotas.size() ; i++){
    var Nota nota = listaNotas.get(i);
    Switch(nota.getFormapagamento.get(i))
    case "CREDITO":
    fechamento.setQuebraCredito(fechamento.getCredito()-nota.getValor.get(i))
    break();
    case "DEBITO":
    fechamento.setQuebraDebito(fechamento.getDebito()-nota.getValor.get(i))
    break();
    case "CREDITO":
    fechamento.setQuebraDinheiro(fechamento.getDinheiro()-nota.getValor.get(i))
    break();
    case "CREDITO":
    fechamento.setQuebraPix(fechamento.getCredito()-nota.getValor.get(i))
    break();
    }


    }*/


    //float fator = 100f;
    //float valorformatado = Math.round(total * fator) / fator;;
    /*
    lista com as quantidades
    pedir no record
    vincular ao metodo post
    tirar o valor total do record
    calcular o valor total na camada de serviço
    pegando a lista de quantidades e multiplicado
    ex:
    quantidade 1 * produto 1
    quantidade 2 * produto 2...
    */
    /*public void calcularValorTotal() {
        float total = 0;
        for (int i = 0; i < produtos_comanda.size(); i++) {
            Modelo_Produto produto = produtos_comanda.get(i);
            int quantidade = quantidade_prod.get(i);
            total += produto.getPreco() * quantidade;
        }
        this.valor_Total = total;
    }*/
}
