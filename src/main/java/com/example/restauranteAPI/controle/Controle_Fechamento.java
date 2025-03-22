package com.example.restauranteAPI.controle;

import com.example.restauranteAPI.modelo_produto.*;
import com.example.restauranteAPI.record.Record_Fechamento;
import com.example.restauranteAPI.repositorio.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173","https://dz38dsepaezxv.cloudfront.net"})
public class Controle_Fechamento {
    @Autowired
    RepositoryNotas repositoryNotas;
    @Autowired
    Repositorio_abertura repositorioAbertura;
    @Autowired
    Repositorio_fechamento repositorioFechamento;
    @Autowired
    RepositorioAberFech repositorioAberFech;
    @Autowired
    Fechamnetos_notasR repositorioFN;
    @Autowired
    RepositorioSangria repositorioSangria;
    @Autowired
    RepositorioSuprimento repositorioSuprimento;


    @PostMapping("/fechamento")
    public ResponseEntity<Object> fecharCaixa (@RequestBody  @Valid Record_Fechamento recordFechamento) {
        var fechamento = new Modelo_Fechamento();
        List<ModeloAbertura> aberturas = repositorioAbertura.findAll();
        ModeloAbertura aberturaCorrespondente = aberturas.getLast();
        List<Modelo_Fechamento> fechamentos = repositorioFechamento.findAll();
        LocalDateTime hora = LocalDateTime.now();

        if(aberturas.isEmpty() || fechamentos.size()+1!=aberturas.size()){
          return ResponseEntity.status(HttpStatus.CONFLICT).body("Para fechar o Caixa deve ser feita uma abertura antes");
        }
        
        fechamento.setDataFechamento(LocalDateTime.now());
        List<Nota> notasDia = repositoryNotas.findByDataBetween(aberturaCorrespondente.getDataAbertura(), hora);
        fechamento.setCredito(recordFechamento.Credito());
        fechamento.setPix(recordFechamento.Pix());
        fechamento.setDinheiro(recordFechamento.Dinheiro());
        fechamento.setDebito(recordFechamento.Debito());
        //List<Nota> listaNotas = repositoryNotas.findAll();

        float debito= 0;float credito =0;float dinheiro= 0;float pix= 0;
        var fn = new Fechamneto_nota();
        for (int i = 0; i < notasDia.size(); i++) {
            var nota = new Nota();
            nota = notasDia.get(i);
            fn.setNotas(notasDia);
            for (int a =0 ; a<nota.getFormapagamento().size() ; a++) {
                if (a<nota.getFormapagamento().size()){
                switch (nota.getFormapagamento().get(a).toUpperCase()) {
                    case "CREDITO":
                        credito+= nota.getValores().get(a);
                        break;
                    case "DEBITO":
                        debito+= nota.getValores().get(a);
                        break;
                    case "DINHEIRO":
                        dinheiro+= nota.getValores().get(a);
                        break;
                    case "PIX":
                        pix+= nota.getValores().get(a);
                        break;
                }
                }
            }
        }
        float totalSangria = 0;
        float totalSuprimento = 0;
        List<Suprimento> suprimentoList = repositorioSuprimento.findByHorarioBetween(aberturaCorrespondente.getDataAbertura(), hora);
        List<Sangria> sangriaList = repositorioSangria.findByHorarioBetween(aberturaCorrespondente.getDataAbertura(), hora);
        for (int i =0 ; i<suprimentoList.size(); i++){
            suprimentoList.get(i).setFechamento(fechamento);
            totalSuprimento+=suprimentoList.get(i).getValor();
        }
        for(int i =0 ; i< sangriaList.size(); i++){
            sangriaList.get(i).setFechamento(fechamento);
            totalSangria+=sangriaList.get(i).getValor();
        }
         fechamento.setQuebraCredito(fechamento.getCredito()-credito);
        fechamento.setQuebraDebito(fechamento.getDebito()- debito);
        fechamento.setQuebraDinheiro(fechamento.getDinheiro() - (aberturas.get(aberturas.size()-1).getDinheiroAbertura()+totalSuprimento-totalSangria+dinheiro));
        fechamento.setQuebraPix(fechamento.getPix() - pix);
        repositorioFechamento.save(fechamento);
        fn.setFechamento(fechamento);
        repositorioFN.save(fn);
        var relacao = new AberturaFechamento();
        List<AberturaFechamento> aberturaFechamentos = repositorioAberFech.findByidAbertura(aberturaCorrespondente);
        relacao = aberturaFechamentos.getLast();
        relacao.setIdFechamento(fechamento);
        repositorioAberFech.save(relacao);
        fechamento.setRelacao(relacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(fechamento);
    }
}