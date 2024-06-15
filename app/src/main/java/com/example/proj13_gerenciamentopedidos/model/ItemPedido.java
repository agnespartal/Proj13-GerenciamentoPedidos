package com.example.proj13_gerenciamentopedidos.model;

public class ItemPedido extends Pedido {
    private String item;

    public ItemPedido(int id, String usuario, String loja, String data, String item, String status) {
        super(id, usuario, loja, data, status);
        this.item = item;        ;
    }

    // Getters e Setters
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }



    @Override
    public String toString() {
        return super.toString() + "\n Item: " + item + "\n Status: " + getStatus();
    }

    @Override
    public void processaPedido() {
        System.out.println("Processando Pedido...");
    }


}