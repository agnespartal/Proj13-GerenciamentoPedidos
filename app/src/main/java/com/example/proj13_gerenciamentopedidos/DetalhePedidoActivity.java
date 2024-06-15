package com.example.proj13_gerenciamentopedidos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proj13_gerenciamentopedidos.model.Pedido;
import com.example.proj13_gerenciamentopedidos.persistence.PedidoDAO;
import com.example.proj13_gerenciamentopedidos.persistence.PedidoNaoEncontradoExcecao;

public class DetalhePedidoActivity extends AppCompatActivity {
    private PedidoDAO dbHelper;
    private Pedido pedido;
    private TextView tvDetalhe;
    private int idPedido;
    private Button btnExcluirPedido, btnEditaPedido, btnVoltar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_pedido);

        dbHelper = new PedidoDAO(this);
        tvDetalhe = findViewById(R.id.tvDetalhe);
        btnExcluirPedido = findViewById(R.id.btnExcluirPedido);
        btnEditaPedido = findViewById(R.id.btnEditaPedido);
        btnVoltar = findViewById(R.id.btnVoltarMain);

        idPedido = getIntent().getIntExtra("idPedido", -1);
        try {
            pedido = dbHelper.getPedido(idPedido);
            tvDetalhe.setText(pedido.toString());
        } catch (PedidoNaoEncontradoExcecao e) {
            Toast.makeText(this, "Pedido não Encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnExcluirPedido.setOnClickListener(v -> {
            dbHelper.excluirPedido(pedido);
            Toast.makeText(this, "Pedido Excluído", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnEditaPedido.setOnClickListener(v -> {
            Intent intent = new Intent(DetalhePedidoActivity.this, EditaPedidoActivity.class);
            intent.putExtra("idPedido", idPedido);
            startActivity(intent);
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        try {
            pedido = dbHelper.getPedido(idPedido);
            tvDetalhe.setText(pedido.toString());
        } catch (PedidoNaoEncontradoExcecao e) {
            Toast.makeText(this, "Pedido não Encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}