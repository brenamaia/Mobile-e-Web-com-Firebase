package com.example.pasid.trabalhosdfirebase.modelo;

import android.support.v7.app.AppCompatActivity;

public class Trabalho {

    //definição de variáveis
    private String id;
    private String nome;
    private String autor;
    private String linguagem;
    private String enderecoGit;
    private String userGit;
    private String senhaGit;

    public Trabalho(){

    }

    //getters e setters


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLinguagem() {
        return linguagem;
    }
    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public String getEnderecoGit() {
        return enderecoGit;
    }
    public void setEnderecoGit(String enderecoGit) {
        this.enderecoGit = enderecoGit;
    }

    public String getUserGit() {
        return userGit;
    }
    public void setUserGit(String userGit) {
        this.userGit = userGit;
    }

    public String getSenhaGit() {
        return senhaGit;
    }
    public void setSenhaGit(String senhaGit) {
        this.senhaGit = senhaGit;
    }

    @Override
    public String toString() {
        return "Titulo= " + nome;
    }
}
