package br.edu.ifpb.teatro.model;

import br.edu.ifpb.teatro.enums.PessoaSexo;

import java.time.LocalDate;

public class ADM{
    private String senha;
    private String email;

    public ADM(String email, String senha){
        this.senha = senha;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
