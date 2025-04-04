package com.example.restauranteAPI.controle;

import com.example.restauranteAPI.Servico.Servicos;
import com.example.restauranteAPI.modelo_produto.*;
import com.example.restauranteAPI.record.AddProdComanda;
import com.example.restauranteAPI.record.Record_comandas;
import com.example.restauranteAPI.record.Record_fecharComanda;
import com.example.restauranteAPI.repositorio.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController

@CrossOrigin(origins = {"http://localhost:5173","https://d2wk73bhxwtu9d.cloudfront.net"})
public class Controle_comandas {

    @Autowired
    private Repositorio00 funcoesprod;

    @Autowired
    private Repositorio_comandas funcoesCRUD;

    @Autowired
    private Servicos funcoes_caixa;

    @Autowired
    private RepositoryNotas respositorynotas;

    @Autowired
    private Repositorio_comanprod repositorioComanprod;

    @Autowired
    private Repositorio_Pedidos repositorioPedidos;


    @PostMapping("/comandas")
    public ResponseEntity<Object> salvarComanda(@RequestBody @Valid Record_comandas recordComandas) {
        var comanda000 = new Modelo_Comanda();
        //var produtocomanda = new ProdutoComanda();
        comanda000.setIdentificador(recordComandas.Identificador());
        comanda000.setQuantidae_prod(recordComandas.quantidade_prod());
        comanda000.setStatus(true);
        funcoesCRUD.save(comanda000);

        funcoes_caixa.calcularValorTotal(comanda000);
        for (int i =0; i<recordComandas.produtosIds().size(); i++) {
            Optional<Modelo_Produto> produto = funcoesprod.findById(recordComandas.produtosIds().get(i));
            if (produto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado: " );
            }
            if(recordComandas.quantidade_prod().size()!=recordComandas.produtosIds().size()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantidades diferentes");
            }
            if(produto.get().isVai_para_cozinha()&&repositorioPedidos.findByIdcomanda(comanda000).isEmpty()){
                var pedido = new Pedidos();
                pedido.setId_produto(produto.get());
                pedido.setStatus("Espera");
                pedido.setQuantidade(comanda000.getQuantidade_prod().get(i));
                pedido.setIdcomanda(comanda000);
                repositorioPedidos.save(pedido);
            }
            var comandaprod = new Comandas_produtos();
            comandaprod.setId_produto(produto.get());
            comandaprod.setIdcomanda(comanda000);
            repositorioComanprod.save(comandaprod);
        }
        if (comanda000.getIdentificador() == null || comanda000.getIdentificador().isEmpty()) {
            comanda000.setIdentificador("Sem nome");
        }

        try {
            funcoesCRUD.save(comanda000);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a comanda: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(comanda000);

    }
    @GetMapping("/ComandasAbertas")
    public ResponseEntity<List<Modelo_Comanda>> getComandas(){
        List<Modelo_Comanda> Comandas = funcoesCRUD.findByStatus(true);
        return ResponseEntity.status(HttpStatus.OK).body(Comandas);
    }
    @GetMapping("/ProdutosComanda/{ID}")
    public ResponseEntity<Object> getCP(@PathVariable(value = "ID")UUID id){
        Optional<Modelo_Comanda> comanda00= funcoesCRUD.findById(id);
        if(!comanda00.isEmpty()){
            List<Modelo_Produto> produtosComanda00 = new ArrayList<>();
            List<Comandas_produtos> cp =repositorioComanprod.findByIdcomanda(comanda00.get());
            for (int i = 0; i < cp.size(); i++){
                produtosComanda00.add(cp.get(i).getId_produto());
            }
            return ResponseEntity.status(HttpStatus.OK).body(produtosComanda00);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comanda não encontrada");
    }
    @GetMapping("/comandas")
    public ResponseEntity<List<Modelo_Comanda>> getAll(){
        List<Modelo_Comanda> liata_comandas = funcoesCRUD.findAll();
        if (!liata_comandas.isEmpty()){
            for (Modelo_Comanda comandas:liata_comandas){
                UUID ids = comandas.getId_Comanda();
                comandas.add(linkTo(methodOn(Controle_comandas.class).getOne(ids)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(liata_comandas);
    }
    @GetMapping("/comandas/{ID}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "ID")UUID id){
        Optional<Modelo_Comanda> comandaO = funcoesCRUD.findById(id);
        if (comandaO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comanda não encontrada");
        }
        comandaO.get().add(linkTo(methodOn(Controle_comandas.class).getAll()).withRel("Lista de comandas"));
        return ResponseEntity.status(HttpStatus.OK).body(comandaO.get());
    }
    @PutMapping("/addprod/{ID}")
    public  ResponseEntity<Object> addprod(@PathVariable(value = "ID")UUID id, @RequestBody @Valid AddProdComanda addProdComanda){
        Optional<Modelo_Comanda> comandaO = funcoesCRUD.findById(id);
        if (comandaO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comanda não encontrada");
        }
        List<Integer> quantidades = comandaO.get().getQuantidade_prod();
        if (quantidades == null) {
            quantidades = new ArrayList<>();
        }
        quantidades.add(1);
        System.out.println(quantidades);
        var attcomanda = comandaO.get();
        Optional<Modelo_Produto> prod = funcoesprod.findById(addProdComanda.produtosIds() );
        attcomanda.setId_Comanda(id);
        List<Comandas_produtos> produtosC =repositorioComanprod.findByIdcomanda(attcomanda);
        for(int i=0; i< produtosC.size(); i++){
            if(produtosC.get(i).getId_produto()==prod.get()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O produto Já existe na comanda");
            }
        }
        attcomanda.setQuantidade_prod(quantidades);
        attcomanda.setIdentificador(comandaO.get().getIdentificador());
        var nwprod = new Comandas_produtos();
        nwprod.setIdcomanda(attcomanda);
        if(prod.isPresent()){
            nwprod.setId_produto(prod.get());
        }
        repositorioComanprod.save(nwprod);
        funcoes_caixa.calcularValorTotal(attcomanda);
        attcomanda.setStatus(true);
        funcoesCRUD.save(attcomanda);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtosC);
    }
    @PutMapping("/comandas/{ID}")
    public ResponseEntity<Object> atualizar(@PathVariable(value = "ID")UUID id,
                                            @RequestBody @Valid Record_comandas record){
        Optional<Modelo_Comanda> comanda0ptional = funcoesCRUD.findById(id);
        if (comanda0ptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comanda não encontrada");
        }
        var newC = comanda0ptional.get();
        newC.setId_Comanda(id);
        newC.getQuantidade_prod().clear();
        newC.setQuantidae_prod(record.quantidade_prod());
        newC.setIdentificador(record.Identificador());
        if (newC.getIdentificador()==null){
            newC.setIdentificador("sem nome");
        }
        List<Comandas_produtos> comandasProdutos = repositorioComanprod.findByIdcomanda(newC);
        List<Modelo_Produto> produtoList = new ArrayList<>();
        for (Comandas_produtos comandasProduto : comandasProdutos) {
            repositorioComanprod.delete(comandasProduto);
        }



        for (int i= 0 ; i< record.produtosIds().size(); i++){
            Optional<Modelo_Produto> prods = funcoesprod.findById(record.produtosIds().get(i));
            if (prods.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("produto não encontrado:");
            }
            else {
                produtoList.add(prods.get());
            }
            if(produtoList.get(i).isVai_para_cozinha()){
                List<Pedidos> NPedidos = repositorioPedidos.findByComandaAndProduto(newC, produtoList.get(i));
                if(NPedidos.isEmpty()){
                    var pedidoN = new Pedidos();
                    pedidoN.setIdcomanda(newC);
                    pedidoN.setStatus("Espera");
                    pedidoN.setQuantidade(record.quantidade_prod().get(i));
                    pedidoN.setId_produto(produtoList.get(i));
                    repositorioPedidos.save(pedidoN);
                }
                else {
                    for(int a=0; a< NPedidos.size(); a++){
                        if(NPedidos.get(a).getQuantidade()!=record.quantidade_prod().get(i)){
                            if(NPedidos.get(a).getStatus()=="Preparo"){
                            var pedidoN = new Pedidos();
                                pedidoN.setIdcomanda(newC);
                                pedidoN.setStatus("Espera");
                                pedidoN.setQuantidade(record.quantidade_prod().get(i));
                                pedidoN.setId_produto(produtoList.get(i));
                                repositorioPedidos.save(pedidoN);
                            }
                            else if (NPedidos.get(a).getStatus()=="Espera"){
                                NPedidos.get(a).setQuantidade(record.quantidade_prod().get(i));
                            }
                        }
                    }
                }

            }
            var comanda_produto = new Comandas_produtos();
            comanda_produto.setIdcomanda(newC);
            comanda_produto.setId_produto(produtoList.get(i));
            repositorioComanprod.save(comanda_produto);
            //newC.adicionarItem(prods.get());
        }
        funcoes_caixa.calcularValorTotal(newC);
        newC.setStatus(true);
        return ResponseEntity.status(HttpStatus.OK).body(funcoesCRUD.save(newC));
    }
    @DeleteMapping("/comandas/{ID}")
    public ResponseEntity<Object> deletarone (@PathVariable(value = "ID")UUID id){
        Optional<Modelo_Comanda> comandaO = funcoesCRUD.findById(id);
        if (comandaO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comanda não encontrada");
        }
        funcoesCRUD.delete(comandaO.get());
        return ResponseEntity.status(HttpStatus.OK).body("comanda deletada");
    }
    @PostMapping("comandas/fecharcomanda/{ID}")
    public ResponseEntity<Object> fecharComanda (@PathVariable(value = "ID") UUID id , @RequestBody Record_fecharComanda RfecharComanda){
        Optional <Modelo_Comanda> comandaOptional = funcoesCRUD.findById(id);
        if (comandaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comanda não encontrada");
        }
        float valorTotal = RfecharComanda.valores().stream()
                .reduce(0.00F, Float::sum);
        if (valorTotal!=comandaOptional.get().getValor_Total()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Os valores não batem");
        }
        if (RfecharComanda.FormaDePagamento().size()!=RfecharComanda.valores().size()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Os tamanhos das listas são diferentes");
        }
        comandaOptional.get().setStatus(false);
        var nota = new Nota();
        nota.setData(RfecharComanda.dataTime());
        nota.setValores(RfecharComanda.valores());
        nota.setFormapagamento(RfecharComanda.FormaDePagamento());
        nota.setValor_total(comandaOptional.get().getValor_Total());
        nota.setQuantidades(comandaOptional.get().getQuantidade_prod());
        nota.setIdComanda(comandaOptional.get());
        List<Comandas_produtos> comandasProdutos = repositorioComanprod.findByIdcomanda(comandaOptional.get());
        List<Modelo_Produto> produtos = new ArrayList<>();
        for (int i = 0 ; i<comandasProdutos.size() ; i++){
            produtos.add(comandasProdutos.get(i).getId_produto());
        }
        for (int i = 0; i<produtos.size() ; i++){
            nota.getNomesProd().add(produtos.get(i).getNome_produto());
            nota.getValorUni().add(produtos.get(i).getValor_produto());
        }
        for (int i =0 ; i<produtos.size(); i++){
            nota.getValor().add(produtos.get(i).getValor_produto()*
                    nota.getQuantidades().get(i));
        }
        respositorynotas.save(nota);
        List<Comandas_produtos> cps = repositorioComanprod.findByIdcomanda(comandaOptional.get());
        for (Comandas_produtos cp : cps) {
            repositorioComanprod.delete(cp);
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(nota.formatar(nota));
    }



}
