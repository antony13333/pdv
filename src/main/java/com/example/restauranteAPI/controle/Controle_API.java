package com.example.restauranteAPI.controle;

import com.example.restauranteAPI.modelo_produto.Modelo_Produto;
import com.example.restauranteAPI.record.estrutura;
import com.example.restauranteAPI.repositorio.Repositorio00;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@CrossOrigin(origins = {"http://localhost:5173","https://dz38dsepaezxv.cloudfront.net"})
@RestController
public class Controle_API {
    @Autowired
    Repositorio00 Jpa_funcoes;
    @PostMapping("/produtosrestaurante")
    public ResponseEntity<Modelo_Produto> salvar(@RequestBody @Valid estrutura Estrutura){
        var modelo = new Modelo_Produto();
        BeanUtils.copyProperties(Estrutura , modelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(Jpa_funcoes.save(modelo));
    }
    @GetMapping("/produtosrestaurante")
    public ResponseEntity<List<Modelo_Produto>> getALL(){
        List<Modelo_Produto> Lista_produtos= Jpa_funcoes.findAll();
        if (!Lista_produtos.isEmpty()){
            for (Modelo_Produto produtos:Lista_produtos){
            UUID id = produtos.getID_produto();
            produtos.add(linkTo(methodOn(Controle_API.class).getOne(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(Lista_produtos);
    }
    @GetMapping("/produtosrestaurante/{ID}")
    public ResponseEntity<Object>getOne(@PathVariable(value = "ID") UUID id){
        Optional<Modelo_Produto> produtoO = Jpa_funcoes.findById(id);
        if (produtoO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("produto não encontrado");
        }
        produtoO.get().add(linkTo(methodOn(Controle_API.class).getALL()).withRel("Lista de Produtos"));
        return ResponseEntity.status(HttpStatus.OK).body(produtoO.get());
    }
    @PutMapping("/produtosrestaurante/{ID}")
    public ResponseEntity<Object> atualizar(@PathVariable(value = "ID")UUID id,
                                            @RequestBody @Valid estrutura estrutura){
        Optional <Modelo_Produto> produtoO = Jpa_funcoes.findById(id);
        if (produtoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não Encontrado!!!");
        }
        var newP = produtoO.get();
        BeanUtils.copyProperties(estrutura , newP);
        return ResponseEntity.status(HttpStatus.OK).body(Jpa_funcoes.save(newP));
    }
    @DeleteMapping("/produtosrestaurante/{ID}")
    public ResponseEntity<Object>Deletar(@PathVariable(value = "ID") UUID id){
        Optional <Modelo_Produto> ProdutoO = Jpa_funcoes.findById(id);
        if (ProdutoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não Encontrado!!!");
        }
        Jpa_funcoes.delete(ProdutoO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto Deletado");
    }


}
