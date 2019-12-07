package com.example.projeto;

public class Lista_questionario {

    String quantidade;
    String titulo;
    String status;
    String id;


    public Lista_questionario() {
    }

    public Lista_questionario(String quantidade, String titulo, String status) {
        super();
        this.quantidade = quantidade;
        this.titulo = titulo;
        this.status = status;
        this.id = id;

    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
