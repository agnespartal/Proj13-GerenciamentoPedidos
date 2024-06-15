package com.example.proj13_gerenciamentopedidos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnPedidoLista;
    private Button btnInserirPedido;
    private Handler handler;
    private Runnable lembreteStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPedidoLista = findViewById(R.id.btnListaPedido);
        btnInserirPedido = findViewById(R.id.btnAdicionaPedido);

        btnPedidoLista.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaPedidoActivity.class);
            startActivity(intent);
        });

        btnInserirPedido.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionaPedidoActivity.class);
            startActivity(intent);
        });

        handler = new Handler();
        lembreteStatus = new Runnable() {
            @Override
            public void run() {
                lembreteUsuarioStatus();
                handler.postDelayed(this, 300000);
            }
        };

        handler.post(lembreteStatus);
    }

    private void lembreteUsuarioStatus() {
        Toast.makeText(this, "Lembrete: Atualize o status dos seus pedidos.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(lembreteStatus);
    }
}