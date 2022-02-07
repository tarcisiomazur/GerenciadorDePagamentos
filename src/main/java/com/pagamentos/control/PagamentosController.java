package com.pagamentos.control;

import com.pagamentos.model.Jogador;
import com.pagamentos.model.Pagamento;
import com.pagamentos.repository.JogadorRepository;
import com.pagamentos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PagamentosController {
    @Autowired
    JogadorRepository jogadores_rep;
    @Autowired
    PagamentoRepository pagamento_rep;

    @GetMapping("/jogadores")
    public ResponseEntity<List<Jogador>> getAllJogadores() {
        try
        {
            List<Jogador> jogadores = new ArrayList<>();
            jogadores_rep.findAll().forEach(jogadores::add);
            if (jogadores.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(jogadores, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/jogadores/{id}")
    public ResponseEntity<Jogador> getJogadorById(@PathVariable("id") int id) {
        try
        {
            Optional<Jogador> jogador = jogadores_rep.findById(id);
            if (jogador.isPresent())
                return new ResponseEntity<>(jogador.get(), HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/jogadores")
    public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jogador)
    {
        try {
            Jogador j = jogadores_rep.save(jogador);
            return new ResponseEntity<>(j, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/jogadores/{id}")
    public ResponseEntity<Jogador> updateJogador(@PathVariable("id") int id, @RequestBody Jogador jogador){
        try
        {
            Optional<Jogador> data = jogadores_rep.findById(id);
            if (data.isPresent())
            {
                Jogador j = data.get();
                j.setDatanasc(jogador.getDatanasc());
                j.setEmail(jogador.getEmail());
                j.setNome(jogador.getNome());
                return new ResponseEntity<>(jogadores_rep.save(j), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/jogadores/{id}")
    public ResponseEntity<HttpStatus> deleteJogador(@PathVariable("id") int id) {
        try {
            if (jogadores_rep.existsById(id))
            {
                jogadores_rep.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/jogadores/{cod_jogador}/pagamentos")
    public ResponseEntity<List<Pagamento>> getAllPagamentos(@PathVariable("cod_jogador") int cod_jogador) {
        try
        {
            var jogador = jogadores_rep.findById(cod_jogador);
            if(jogador.isPresent()){

                return new ResponseEntity<>(jogador.get().getPagamentos(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/jogadores/{cod_jogador}/pagamentos")
    public ResponseEntity<HttpStatus> deleteAllPagamentos(@PathVariable("cod_jogador") int id) {
        try {
            Optional<Jogador> data = jogadores_rep.findById(id);
            if (data.isPresent())
            {
                Jogador j = data.get();
                j.getPagamentos().clear();
                jogadores_rep.saveAndFlush(j);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pagamentos/{cod_pagamento}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable("cod_pagamento") int cod_pagamento) {
        try
        {
            var pagamento = pagamento_rep.findById(cod_pagamento);
            if(pagamento.isPresent()){

                return new ResponseEntity<>(pagamento.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<Pagamento> createPagamento(@RequestParam(name = "cod_jogador") int cod_jogador, @RequestBody Pagamento pagamento)
    {
        try {
            Optional<Jogador> data = jogadores_rep.findById(cod_jogador);
            if(data.isPresent()){
                Jogador j = data.get();
                j.getPagamentos().add(pagamento);
                pagamento.setJogador(j);
                jogadores_rep.saveAndFlush(j);
                return new ResponseEntity<>(pagamento, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/pagamentos/{cod_pagamento}")
    public ResponseEntity<Pagamento> updatePagamento(@PathVariable("cod_pagamento") int cod_pagamento, @RequestBody Pagamento pagamento){
        try
        {
            Optional<Pagamento> data = pagamento_rep.findById(cod_pagamento);
            if (data.isPresent())
            {
                Pagamento p = data.get();
                p.setAno(pagamento.getAno());
                p.setMes(pagamento.getMes());
                p.setValor(pagamento.getValor());
                return new ResponseEntity<>(pagamento_rep.save(p), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/pagamentos/{cod_pagamento}")
    public ResponseEntity<HttpStatus> deletePagamento(@PathVariable("cod_pagamento") int id) {
        try {
            Pagamento p = pagamento_rep.getById(id);
            if (p!=null)
            {
                pagamento_rep.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
