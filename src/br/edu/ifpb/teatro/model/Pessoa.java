package br.edu.ifpb.teatro.model;

import br.edu.ifpb.teatro.enums.PessoaSexo;

public class Pessoa {
    private String nome;
    private PessoaSexo sexo;
    private String cpf;
    private String email;

    public Pessoa(String nome, String cpf, String email, PessoaSexo sexo) {
        this.email = email;
        this.cpf = cpf; // lembrar de criar uma validação para melhorar nosso projeto
        this.sexo = sexo;
        this.nome = nome;
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
}
