package com.pagamentos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_pagamento;

    @Column
    private int ano;

    @Column
    private int mes;

    @Column(length = 10, precision = 2)
    private float valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_jogador")
    private Jogador jogador;

    public Pagamento() {
    }

    public int getCod_pagamento() {
        return cod_pagamento;
    }

    public void setCod_pagamento(int cod_pagamento) {
        this.cod_pagamento = cod_pagamento;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    @JsonIgnore
    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public int getCod_jogador() {
        return this.jogador.getCod_jogador();
    }

    public void setCod_jogador(int cod_jogador) {
        this.jogador.setCod_jogador(cod_jogador);
    }
}
