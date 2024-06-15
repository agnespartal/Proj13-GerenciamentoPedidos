package com.example.proj13_gerenciamentopedidos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proj13_gerenciamentopedidos.model.Pedido;
import com.example.proj13_gerenciamentopedidos.persistence.PedidoDAO;

import java.util.List;

public class ListaPedidoActivity extends AppCompatActivity {
    private PedidoDAO dbHelper;
    private ListView listView;
    private ArrayAdapter<Pedido> adapter;
    private List<Pedido> pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedido);

        dbHelper = new PedidoDAO(this);
        listView = findViewById(R.id.listView);
        Button btnInserirPedido = findViewById(R.id.btnAdicionaPedido);
        Button btnVoltar = findViewById(R.id.btnVoltarMain);

        pedidos = dbHelper.getAllPedidos();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pedidos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Pedido pedido = pedidos.get(position);
            Intent intent = new Intent(ListaPedidoActivity.this, DetalhePedidoActivity.class);
            intent.putExtra("idPedido", pedido.getId());
            startActivity(intent);
        });

        btnInserirPedido.setOnClickListener(v -> {
            Intent intent = new Intent(ListaPedidoActivity.this, AdicionaPedidoActivity.class);
            startActivity(intent);
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        pedidos.clear();
        pedidos.addAll(dbHelper.getAllPedidos());
        adapter.notifyDataSetChanged();
    }
}