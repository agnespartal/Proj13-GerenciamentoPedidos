package com.example.proj13_gerenciamentopedidos.controller;

import com.example.proj13_gerenciamentopedidos.model.Pedido;
import com.example.proj13_gerenciamentopedidos.persistence.PedidoNaoEncontradoExcecao;

import java.util.ArrayList;
import java.util.List;

public abstract class PedidoControllerAbstract {
    protected List<Pedido> pedidos = new ArrayList<>();

    public abstract void inserirPedido(Pedido pedido);
    public abstract void excluirPedido(Pedido pedido);
    public abstract Pedido listarPedido(int id) throws PedidoNaoEncontradoExcecao, PedidoNaoEncontradoExcecao;
}
