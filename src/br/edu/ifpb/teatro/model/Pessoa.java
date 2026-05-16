package br.edu.ifpb.teatro.model;

import br.edu.ifpb.teatro.enums.PessoaSexo;

import java.time.LocalDate;

public  class Pessoa {
    private String nome;
    private PessoaSexo sexo;
    private String cpf;
    private String email;
    private String telefone;
    private String dataNascimento;

    public Pessoa(String nome, String cpf, String email, PessoaSexo sexo, String telefone, String data) {
        this.email = email;
        this.cpf = cpf;
        this.sexo = sexo;
        this.nome = nome;
        this.telefone = telefone;
        this.dataNascimento = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PessoaSexo getSexo() {
        return sexo;
    }

    public void setSexo(PessoaSexo sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
