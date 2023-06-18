package com.alexgl.recetario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alexgl.recetario.database.DBHelper;
import com.alexgl.recetario.models.RecetaModel;

public class PortadaReceta extends AppCompatActivity {

    private RecetaModel receta;
    private SharedPreferences sp;
    TextView titulo, listadoIngredientes;
    DBHelper connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada_receta);

        titulo = (TextView) findViewById(R.id.etiquetaTituloPortada);
        listadoIngredientes = (TextView) findViewById(R.id.etiquetaIngredientesPortada);

        connection = new DBHelper(this, "Recetario", null, 1);

        sp = getSharedPreferences("preferences", MODE_PRIVATE);

        String nombreReceta = sp.getString("nombre", "");
        receta = connection.getReceta(nombreReceta);
        titulo.setText(receta.getNombre());
        listadoIngredientes.setText(receta.getListadoIngredientes());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Pasos.pasoActual = 1;
    }

    public void atras(View view){
        Intent intent = new Intent(view.getContext(), ListadoRecetas.class);
        view.getContext().startActivity(intent);
    }

    public void continuar(View view){
        Intent intent = new Intent(view.getContext(), Pasos.class);
        intent.putExtra("receta", receta);
        view.getContext().startActivity(intent);
    }

}