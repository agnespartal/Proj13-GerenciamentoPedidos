package com.example.proj13_gerenciamentopedidos.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proj13_gerenciamentopedidos.model.AlimentoPedido;
import com.example.proj13_gerenciamentopedidos.model.ItemPedido;
import com.example.proj13_gerenciamentopedidos.model.Pedido;
import com.example.proj13_gerenciamentopedidos.persistence.IOperacoesPedido;
import com.example.proj13_gerenciamentopedidos.persistence.PedidoNaoEncontradoExcecao;

public class PedidoController extends PedidoControllerAbstract implements IOperacoesPedido {
    private SQLiteDatabase db;

    public PedidoController(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void inserirPedido(Pedido pedido) {
        ContentValues values = new ContentValues();
        values.put("usuario", pedido.getUsuario());
        values.put("loja", pedido.getLoja());
        values.put("data", pedido.getData());
        values.put("status", pedido.getStatus());

        if (pedido instanceof AlimentoPedido) {
            values.put("alimento", ((AlimentoPedido) pedido).getAlimento());
        } else if (pedido instanceof ItemPedido) {
            values.put("item", ((ItemPedido) pedido).getItem());
        }

        db.insert("pedidos", null, values);
    }

    @Override
    public void excluirPedido(Pedido pedido) {
        db.delete("pedidos", "id = ?", new String[]{String.valueOf(pedido.getId())});
    }
    @SuppressLint("Range")
    @Override
    public Pedido listarPedido(int id) throws PedidoNaoEncontradoExcecao {
        Cursor cursor = db.query("pedidos", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
             String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
            String loja = cursor.getString(cursor.getColumnIndex("loja"));
            String data = cursor.getString(cursor.getColumnIndex("data"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            String alimento = cursor.getString(cursor.getColumnIndex("alimento"));
            String item = cursor.getString(cursor.getColumnIndex("item"));

            if (alimento != null) {
                cursor.close();
                return new AlimentoPedido(id, usuario, loja, data, alimento, status);
            } else if (item != null) {
                cursor.close();
                return new ItemPedido(id, usuario, loja, data, item, status);
            } else {
                cursor.close();
                return new Pedido(id, usuario, loja, data, status);
            }
        }
        throw new PedidoNaoEncontradoExcecao("Pedido não encontrado");
    }
}