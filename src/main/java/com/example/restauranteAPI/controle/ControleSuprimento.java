package com.example.restauranteAPI.controle;

import com.example.restauranteAPI.modelo_produto.Suprimento;
import com.example.restauranteAPI.record.RecordSuprimento;
import com.example.restauranteAPI.repositorio.RepositorioSuprimento;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class ControleSuprimento {
    @Autowired
    RepositorioSuprimento repositorioSuprimento;
    @PostMapping("/suprimento")
    public ResponseEntity<Suprimento> salvar(@RequestBody @Valid RecordSuprimento recordSuprimento){
        var suprimento = new Suprimento();
        BeanUtils.copyProperties(recordSuprimento , suprimento);
        suprimento.setHorario(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(repositorioSuprimento.save(suprimento));
    }
}
