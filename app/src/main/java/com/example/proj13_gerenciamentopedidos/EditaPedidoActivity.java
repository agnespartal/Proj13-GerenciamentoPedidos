package com.example.proj13_gerenciamentopedidos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proj13_gerenciamentopedidos.model.AlimentoPedido;
import com.example.proj13_gerenciamentopedidos.model.ItemPedido;
import com.example.proj13_gerenciamentopedidos.model.Pedido;
import com.example.proj13_gerenciamentopedidos.persistence.PedidoDAO;
import com.example.proj13_gerenciamentopedidos.persistence.PedidoNaoEncontradoExcecao;

public class EditaPedidoActivity extends AppCompatActivity {
    private PedidoDAO dbHelper;
    private EditText etUsuario, etLoja, etData, etItem;
    private Button btnAtualizarPedido, btnVoltarLista;
    private Spinner spinnerStatus;
    private int idPedido;
    private Pedido pedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_pedido);

        dbHelper = new PedidoDAO(this);
        etUsuario = findViewById(R.id.etUsuario);
        etLoja = findViewById(R.id.etLoja);
        etData = findViewById(R.id.etData);
        etItem = findViewById(R.id.etItem);
        btnAtualizarPedido = findViewById(R.id.btnAtualizaPedido);
        btnVoltarLista = findViewById(R.id.btnVoltarMain);
        spinnerStatus = findViewById(R.id.spinnerStatus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayStatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        idPedido = getIntent().getIntExtra("idPedido", -1);
        try {
            pedido = dbHelper.getPedido(idPedido);
            etUsuario.setText(pedido.getUsuario());
            etLoja.setText(pedido.getLoja());
            etData.setText(pedido.getData());

            if (pedido instanceof ItemPedido) {
                etItem.setText(((ItemPedido) pedido).getItem());
            } else if (pedido instanceof AlimentoPedido) {
                etItem.setText(((AlimentoPedido) pedido).getAlimento());
            }

            int statusPosition = adapter.getPosition(pedido.getStatus());
            spinnerStatus.setSelection(statusPosition);

            btnAtualizarPedido.setOnClickListener(v -> {
                pedido.setUsuario(etUsuario.getText().toString());
                pedido.setLoja(etLoja.getText().toString());
                pedido.setData(etData.getText().toString());
                pedido.setStatus(spinnerStatus.getSelectedItem().toString());

                if (pedido instanceof ItemPedido) {
                    ((ItemPedido) pedido).setItem(etItem.getText().toString());
                } else if (pedido instanceof AlimentoPedido) {
                    ((AlimentoPedido) pedido).setAlimento(etItem.getText().toString());
                }

                dbHelper.atualizaPedido(pedido);
                Toast.makeText(this, "Pedido Atualizado", Toast.LENGTH_SHORT).show();
                finish();
            });
        } catch (PedidoNaoEncontradoExcecao e) {
            Toast.makeText(this, "Pedido não Encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnVoltarLista.setOnClickListener(v -> finish());
    }
}