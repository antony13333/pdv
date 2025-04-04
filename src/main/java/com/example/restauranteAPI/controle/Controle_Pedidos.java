package com.example.restauranteAPI.controle;

import com.example.restauranteAPI.modelo_produto.Modelo_Comanda;
import com.example.restauranteAPI.modelo_produto.Modelo_Produto;
import com.example.restauranteAPI.modelo_produto.Pedidos;
import com.example.restauranteAPI.record.ChangePedido;
import com.example.restauranteAPI.record.FilterPedidosFinalizados;
import com.example.restauranteAPI.record.Postpedido;
import com.example.restauranteAPI.repositorio.Repositorio00;
import com.example.restauranteAPI.repositorio.Repositorio_Pedidos;
import com.example.restauranteAPI.repositorio.Repositorio_comandas;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:5173","https://d2wk73bhxwtu9d.cloudfront.net"})
public class Controle_Pedidos {
    @Autowired
    Repositorio_Pedidos repositorioPedidos;
    @Autowired
    Repositorio00 repositorioprod;
    @Autowired
    Repositorio_comandas repositorioComandas;

    @PostMapping("/postpedido")
    public ResponseEntity<Object> Pedidospost(@RequestBody @Valid Postpedido record) {

        Modelo_Comanda comanda = repositorioComandas.findById(record.comanda())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comanda não encontrada"));

        List<Pedidos> pedidosProcessados = new ArrayList<>();


        for (int i = 0; i < record.produtos().size(); i++) {
            UUID produtoId = record.produtos().get(i);
            Integer quantidade = record.quantidades().get(i);


            Modelo_Produto produto = repositorioprod.findById(produtoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + produtoId));

            Optional<Pedidos> pedidoExistente = repositorioPedidos.findByComandaAndProduto(comanda, produto)
                    .stream()
                    .findFirst();

            if (pedidoExistente.isPresent()) {
                Pedidos pedido = pedidoExistente.get();

                if (pedido.getQuantidade()!=quantidade) {
                    pedido.setQuantidade(quantidade);
                    repositorioPedidos.save(pedido);
                }
                pedidosProcessados.add(pedido);
            } else {
                Pedidos novoPedido = new Pedidos();
                novoPedido.setId_produto(produto);
                novoPedido.setIdcomanda(comanda);
                novoPedido.setQuantidade(quantidade);
                novoPedido.setHoraCriado(LocalDateTime.now());
                novoPedido.setStatus("Espera");
                repositorioPedidos.save(novoPedido);
                pedidosProcessados.add(novoPedido);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidosProcessados);
    }
    @GetMapping("/pedidosEspera")
    public ResponseEntity<Object> PedidosEspera() {
        List<Object[]> produtosComanda = repositorioPedidos.findbystatusO("Espera");
        return ResponseEntity.status(HttpStatus.OK).body( produtosComanda);
    }
    @GetMapping("/pedidosPreparo")
    public  ResponseEntity<Object> PedidosPreparo(){
        List<Object[]> produtosComanda = repositorioPedidos.findbystatusO("Preparo");
        return ResponseEntity.status(HttpStatus.OK).body(produtosComanda);
    }
    @GetMapping("/comandaspedido")
    public ResponseEntity<List<String>> comandasassociadas(){
        List<Pedidos> findall = repositorioPedidos.findAll();
        List<String> identificadores = findall.stream()
                .map(Pedidos::getIdcomanda) // Acessa a comanda
                .map(Modelo_Comanda::getIdentificador) // Acessa o identificador
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(identificadores);
    }
    @PostMapping("/pedidosFinalizados")
    public  ResponseEntity<Object> PedidosFinalizados(@RequestBody FilterPedidosFinalizados filtro){
        LocalDateTime inicioDiac = null;
        LocalDateTime fimDiac = null;
        LocalDateTime inicioDiaf = null;
        LocalDateTime fimDiaf = null;
        System.out.println(filtro.Data_Criacao());
        if(filtro.Data_Criacao() != null){
            LocalDate diac = filtro.Data_Criacao();
           inicioDiac = (diac != null) ? diac.atStartOfDay() : null;
            fimDiac = (diac != null) ? diac.plusDays(1).atStartOfDay() : null;
        }
        if(filtro.Data_Finalizado()!=null){
            LocalDate diaf = filtro.Data_Finalizado();
             inicioDiaf = (diaf != null) ? diaf.atStartOfDay() : null;
             fimDiaf = (diaf != null) ? diaf.plusDays(1).atStartOfDay() : null;
        }
        List<Object[]> Finalizados = repositorioPedidos.findbyNomeproduto(filtro.Nome_produto(), filtro.Identificador(),inicioDiaf, fimDiaf, inicioDiac, fimDiac );
        return ResponseEntity.status(HttpStatus.OK).body(Finalizados);
    }
    @GetMapping("/pedidosFin")
    public  ResponseEntity<Object> PedidosFin(){
        List<Object[]> Finalizados = repositorioPedidos.findbystatusO("Finalizado");
        return ResponseEntity.status(HttpStatus.OK).body(Finalizados);
    }

    @PutMapping("/atualizarPedido")
    public ResponseEntity<Object> AttPedido(@RequestBody @Valid ChangePedido record){
        List<UUID> pedidos = record.Idpedido();
        for (UUID uuid : pedidos) {
            Optional<Pedidos> pedido = repositorioPedidos.findById(uuid);
            if (!pedido.isEmpty()) {
                if(Objects.equals(record.Newstatus(), "Finalizado")){
                    pedido.get().setStatus(record.Newstatus());
                    pedido.get().setHoraFinalizado(LocalDateTime.now());
                    repositorioPedidos.save(pedido.get());
                    return  ResponseEntity.status(HttpStatus.OK).body("Finalizado");
                }
                else {
                    pedido.get().setStatus(record.Newstatus());
                    repositorioPedidos.save(pedido.get());
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("não encontado");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Atualizado");
    }
}
