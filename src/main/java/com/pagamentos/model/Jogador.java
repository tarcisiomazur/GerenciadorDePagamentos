package com.pagamentos.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "jogador")
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_jogador;

    @Column(length = 60)
    private String nome;

    @Column(length = 60)
    private String email;

    @Column
    private Date datanasc;

    @Nullable
    @OneToMany(mappedBy = "jogador", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos;

    public Jogador() {
    }

    public int getCod_jogador() {
        return cod_jogador;
    }

    public void setCod_jogador(int cod_jogador) {
        this.cod_jogador = cod_jogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }
}
