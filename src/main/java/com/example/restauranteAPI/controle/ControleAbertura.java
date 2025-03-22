package com.example.restauranteAPI.controle;

import com.example.restauranteAPI.modelo_produto.AberturaFechamento;
import com.example.restauranteAPI.modelo_produto.ModeloAbertura;
import com.example.restauranteAPI.modelo_produto.Modelo_Fechamento;
import com.example.restauranteAPI.record.Record_Abertura;
import com.example.restauranteAPI.repositorio.RepositorioAberFech;
import com.example.restauranteAPI.repositorio.Repositorio_abertura;
import com.example.restauranteAPI.repositorio.Repositorio_fechamento;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173","https://dz38dsepaezxv.cloudfront.net"})
public class ControleAbertura {
    @Autowired
    Repositorio_abertura repositorioAbertura;
    @Autowired
    Repositorio_fechamento repositorioFechamento;
    @Autowired
    RepositorioAberFech repositorioAberFech;

    @GetMapping("/abre")
    public ResponseEntity<Boolean> conferencia (){
        List<ModeloAbertura> aberturas = repositorioAbertura.findAll();
        List<Modelo_Fechamento> fechamentos = repositorioFechamento.findAll();
        if(aberturas.size()== fechamentos.size()){
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }

    }
    @PostMapping("/aberturaCaixa")
    public ResponseEntity<Object> AberturaCaixa (@RequestBody @Valid Record_Abertura recordAbertura){
        var abertura = new ModeloAbertura();
        if (repositorioFechamento.findAll().size()!=repositorioAbertura.findAll().size()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("para fazer uma nova abertura deve ser feito o fechamento da anterior");
        }
        //BeanUtils.copyProperties(recordAbertura , abertura);
        abertura.setDataAbertura(LocalDateTime.now());
        abertura.setDinheiroAbertura(recordAbertura.Dinheiro_abertura());
        repositorioAbertura.save(abertura);
        var relacao = new AberturaFechamento();
        relacao.setIdAbertura(abertura);
        relacao.setData(LocalDate.now());
        repositorioAberFech.save(relacao);
        abertura.setRelacao(relacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(repositorioAbertura.save(abertura));
    }
    @GetMapping("/aberturaHoje")
    public ResponseEntity<List<ModeloAbertura>> getAberturaHoje(){
        LocalDate dta = LocalDate.now((ZoneId.of("America/Sao_Paulo")));
        List<ModeloAbertura> aberturas = repositorioAbertura.findByDataAbertura(dta);
        if(aberturas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(aberturas);
        }
        return ResponseEntity.status(HttpStatus.OK).body(aberturas);
    }
}
