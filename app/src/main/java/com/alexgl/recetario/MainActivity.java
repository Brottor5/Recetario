package com.alexgl.recetario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alexgl.recetario.database.*;

public class MainActivity extends AppCompatActivity {

    private DBHelper connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Crea/conecta la base de datos.
        connection = new DBHelper(this, "Recetario", null, 1);
    }

    public void irListado(View view){
        Intent intent = new Intent(this, ListadoRecetas.class);
        startActivity(intent);
    }

    public void ReiniciarDB(View view){
        getApplicationContext().deleteDatabase("Recetario");
        SQLiteDatabase db = connection.getWritableDatabase();
        Log.d("DB", "DB reiniciada");
    }
}