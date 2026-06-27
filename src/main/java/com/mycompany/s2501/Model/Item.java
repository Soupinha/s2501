package com.mycompany.s2501.Model;

public class Item {

    private int id;
    private String nome;
    private String descricao;
    private int pontos;
    private String imagemInventario;

    public Item() {
    }

    public Item(int id, String nome, String descricao, int pontos, String imagemInventario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.pontos = pontos;
        this.imagemInventario = imagemInventario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }   

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }   

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getImagemInventario() {
        return imagemInventario;
    }

    public void setImagemInventario(String imagemInventario) {
        this.imagemInventario = imagemInventario;
    }
}