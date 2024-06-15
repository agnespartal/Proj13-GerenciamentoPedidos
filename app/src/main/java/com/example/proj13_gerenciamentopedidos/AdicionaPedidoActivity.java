package com.example.proj13_gerenciamentopedidos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proj13_gerenciamentopedidos.model.AlimentoPedido;
import com.example.proj13_gerenciamentopedidos.model.ItemPedido;
import com.example.proj13_gerenciamentopedidos.model.Pedido;
import com.example.proj13_gerenciamentopedidos.persistence.PedidoDAO;
public class AdicionaPedidoActivity extends AppCompatActivity {
    private PedidoDAO dbHelper;
    private EditText etUsuario, etLoja, etData, etItem;
    private Button btnSalvarPedido, btnVoltarMain;
    private RadioGroup radioGroupTipoPedido;
    private RadioButton radioPedidoItem, radioPedidoAlimento;
    private Spinner spinnerStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_pedido);

        dbHelper = new PedidoDAO(this);
        etUsuario = findViewById(R.id.etUsuario);
        etLoja = findViewById(R.id.etLoja);
        etData = findViewById(R.id.etData);
        etItem = findViewById(R.id.etItem);
        btnSalvarPedido = findViewById(R.id.btnInserirPedido);
        btnVoltarMain = findViewById(R.id.btnVoltarMain);
        radioGroupTipoPedido = findViewById(R.id.radioGroupTipoPedido);
        radioPedidoItem = findViewById(R.id.radioItem);
        radioPedidoAlimento = findViewById(R.id.radioAlimento);
        spinnerStatus = findViewById(R.id.spinnerStatus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayStatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        btnSalvarPedido.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String loja = etLoja.getText().toString();
            String data = etData.getText().toString();
            String item = etItem.getText().toString();
            String status = spinnerStatus.getSelectedItem().toString();

            Pedido pedido;
            if (radioPedidoItem.isChecked()) {
                pedido = new ItemPedido(0, usuario, loja, data, item, status);
            } else if (radioPedidoAlimento.isChecked()) {
                pedido = new AlimentoPedido(0, usuario, loja, data, item, status);
            } else {
                // Handle other order types if necessary
                pedido = new Pedido(0, usuario, loja, data, status);
            }

            dbHelper.inserirPedido(pedido);
            Toast.makeText(this, "Pedido Adicionado", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnVoltarMain.setOnClickListener(v -> finish());
    }
}