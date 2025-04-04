package com.example.restauranteAPI.controle;

import com.example.restauranteAPI.modelo_produto.Modelo_Comanda;
import com.example.restauranteAPI.modelo_produto.Nota;
import com.example.restauranteAPI.modelo_produto.Pedidos;
import com.example.restauranteAPI.record.FilterNotas;
import com.example.restauranteAPI.repositorio.RepositoryNotas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@RestController
@CrossOrigin(origins = {"http://localhost:5173","https://d2wk73bhxwtu9d.cloudfront.net"})
public class Controle_Notas {
    @Autowired
    RepositoryNotas repositoryNotas;

    @DeleteMapping("/notas/{ID}")
    public ResponseEntity<Object> deletar_nota (@PathVariable(value = "ID")UUID id){
        Optional<Nota> notaOptional = repositoryNotas.findById(id);
        if (notaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nota não encontrada");
        }
        repositoryNotas.delete(notaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Nota deletada");

    }
    @GetMapping("/notas")
    public ResponseEntity<List<Nota>> GetAll (){
        List<Nota> ListaNotas = repositoryNotas.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ListaNotas);
    }
    @PostMapping("/filternotas")
    public  ResponseEntity<List<Object[]>> filtrar(@RequestBody FilterNotas record){
        List<Object[]> notas = repositoryNotas.findNotasFiltradas(record.Identificador(),record.Data_Criacao() , record.Nome_produto(), record.FormaPagamento());
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }
    @GetMapping("/notasall")
    public ResponseEntity<List<Object[]>> GetAllN (){
        List<Object[]> ListaNotas = repositoryNotas.findallNotas();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ListaNotas);
    }
    @GetMapping("/comandasnota")
    public ResponseEntity<List<String>> comandasassociadas(){
        List<Nota> findall = repositoryNotas.findAll();
        List<String> identificadores = findall.stream()
                .map(Nota::getIdComanda) // Acessa a comanda
                .map(Modelo_Comanda::getIdentificador) // Acessa o identificador
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(identificadores);
    }

}
