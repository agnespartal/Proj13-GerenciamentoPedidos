package com.example.proj13_gerenciamentopedidos.model;

public class Pedido {
    private int id;
    private String usuario;
    private String loja;
    private String data;
    private String status;

    public Pedido(int id, String usuario, String loja, String data, String status) {
        this.id = id;
        this.usuario = usuario;
        this.loja = loja;
        this.data = data;
        this.status = status;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getLoja() { return loja; }
    public void setLoja(String loja) { this.loja = loja; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Pedido nº " + id + " - " + loja + " - " + data;
    }

    public void processaPedido() {
        System.out.println("Processing order...");
    }
}
