package com.example.restauranteAPI.controle;

import com.example.restauranteAPI.modelo_produto.Sangria;
import com.example.restauranteAPI.record.RecordSangria;
import com.example.restauranteAPI.repositorio.RepositorioSangria;
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
@CrossOrigin(origins = {"http://localhost:5173","https://d2wk73bhxwtu9d.cloudfront.net"})
public class ControleSangria {
    @Autowired
    RepositorioSangria repositorioSangria;
    @PostMapping("/sangria")
    public ResponseEntity<Sangria> salvar(@RequestBody @Valid RecordSangria recordSangria){
        var sangria = new Sangria();
        BeanUtils.copyProperties(recordSangria , sangria);
        sangria.setHorario(LocalDateTime.now());
        return  ResponseEntity.status(HttpStatus.CREATED).body(repositorioSangria.save(sangria));
    }
}
