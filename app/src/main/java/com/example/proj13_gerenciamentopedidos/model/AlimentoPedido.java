package com.example.proj13_gerenciamentopedidos.model;

public class AlimentoPedido extends Pedido {
    private String alimento;

    public AlimentoPedido(int id, String usuario, String loja, String data, String alimento, String status) {
        super(id, usuario, loja, data, status);
        this.alimento = alimento;
    }
    public String getAlimento() { return alimento; }
    public void setAlimento(String alimento) { this.alimento = alimento; }



    @Override
    public String toString() {
        return super.toString() + "\n Alimento: " + alimento + "\n Status: " + getStatus();
    }


    @Override
    public void processaPedido() {
        System.out.println("Processando Pedido...");
    }


}