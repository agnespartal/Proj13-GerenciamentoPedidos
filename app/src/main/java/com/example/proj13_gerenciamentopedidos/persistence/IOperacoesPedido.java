package com.example.proj13_gerenciamentopedidos.persistence;

import com.example.proj13_gerenciamentopedidos.model.Pedido;

public interface IOperacoesPedido {
    void inserirPedido(Pedido pedido);
    void excluirPedido(Pedido pedido);
    Pedido listarPedido(int id) throws PedidoNaoEncontradoExcecao;
}