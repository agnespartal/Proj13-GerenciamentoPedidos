package com.example.proj13_gerenciamentopedidos.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proj13_gerenciamentopedidos.model.AlimentoPedido;
import com.example.proj13_gerenciamentopedidos.model.ItemPedido;
import com.example.proj13_gerenciamentopedidos.model.Pedido;

import java.util.ArrayList;
import java.util.List;
public class PedidoDAO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PedidoDB";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_PEDIDOS = "pedidos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USUARIO = "usuario";
    private static final String COLUMN_LOJA = "loja";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_ALIMENTOS = "alimentos";
    private static final String COLUMN_ITENS = "itens";

    public PedidoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_PEDIDOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USUARIO + " TEXT,"
                + COLUMN_LOJA + " TEXT,"
                + COLUMN_DATA + " TEXT,"
                + COLUMN_STATUS + " TEXT,"
                + COLUMN_ALIMENTOS + " TEXT,"
                + COLUMN_ITENS + " TEXT" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEDIDOS);
        onCreate(db);
    }

    public void inserirPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO, pedido.getUsuario());
        values.put(COLUMN_LOJA, pedido.getLoja());
        values.put(COLUMN_DATA, pedido.getData());
        values.put(COLUMN_STATUS, pedido.getStatus());

        if (pedido instanceof AlimentoPedido) {
            values.put(COLUMN_ALIMENTOS, ((AlimentoPedido) pedido).getAlimento());
        } else if (pedido instanceof ItemPedido) {
            values.put(COLUMN_ITENS, ((ItemPedido) pedido).getItem());
        }

        db.insert(TABLE_PEDIDOS, null, values);
        db.close();
    }
    @SuppressLint("Range")
    public Pedido getPedido(int id) throws PedidoNaoEncontradoExcecao {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PEDIDOS, new String[]{COLUMN_ID, COLUMN_USUARIO, COLUMN_LOJA, COLUMN_DATA, COLUMN_STATUS, COLUMN_ALIMENTOS, COLUMN_ITENS},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String usuario = cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO));
            String loja = cursor.getString(cursor.getColumnIndex(COLUMN_LOJA));
            String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
            String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
            String alimento = cursor.getString(cursor.getColumnIndex(COLUMN_ALIMENTOS));
            String item = cursor.getString(cursor.getColumnIndex(COLUMN_ITENS));

            cursor.close();
            if (alimento != null) {
                return new AlimentoPedido(id, usuario, loja, data, alimento, status);
            } else if (item != null) {
                return new ItemPedido(id, usuario, loja, data, item, status);
            } else {
                return new Pedido(id, usuario, loja, data, status);
            }
        }
        throw new PedidoNaoEncontradoExcecao("Pedido não Encontrado");
    }
    @SuppressLint("Range")
    public List<Pedido> getAllPedidos() {
        List<Pedido> pedidoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PEDIDOS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String usuario = cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO));
                String loja = cursor.getString(cursor.getColumnIndex(COLUMN_LOJA));
                String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
                String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
                String alimento = cursor.getString(cursor.getColumnIndex(COLUMN_ALIMENTOS));
                String item = cursor.getString(cursor.getColumnIndex(COLUMN_ITENS));

                if (alimento != null) {
                    pedidoList.add(new AlimentoPedido(id, usuario, loja, data, alimento, status));
                } else if (item != null) {
                    pedidoList.add(new ItemPedido(id, usuario, loja, data, item, status));
                } else {
                    pedidoList.add(new Pedido(id, usuario, loja, data, status));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pedidoList;
    }

    public int atualizaPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO, pedido.getUsuario());
        values.put(COLUMN_LOJA, pedido.getLoja());
        values.put(COLUMN_DATA, pedido.getData());
        values.put(COLUMN_STATUS, pedido.getStatus());

        if (pedido instanceof AlimentoPedido) {
            values.put(COLUMN_ALIMENTOS, ((AlimentoPedido) pedido).getAlimento());
        } else if (pedido instanceof ItemPedido) {
            values.put(COLUMN_ITENS, ((ItemPedido) pedido).getItem());
        }

        return db.update(TABLE_PEDIDOS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(pedido.getId())});
    }

    public void excluirPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PEDIDOS, COLUMN_ID + " = ?", new String[]{String.valueOf(pedido.getId())});
        db.close();
    }
}