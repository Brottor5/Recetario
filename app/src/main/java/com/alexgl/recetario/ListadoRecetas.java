package com.alexgl.recetario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alexgl.recetario.database.DBHelper;
import com.alexgl.recetario.models.RecetaModel;
import com.alexgl.recetario.recyclerviews.ListadoRecyclerviewAdapter;

import java.util.ArrayList;

public class ListadoRecetas extends AppCompatActivity {

    ArrayList<RecetaModel> recetas;
    RecetaModel recetaModel;
    DBHelper connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_recetas);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Genera lista de recetas
        generaListado();

        //inicia recyclerview
        RecyclerView recyclerview = findViewById(R.id.listadoRecyclerview);

        ListadoRecyclerviewAdapter adapter = new ListadoRecyclerviewAdapter(this, recetas);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Pasos.pasoActual = 1;
    }

    private void generaListado() {
        recetas = new ArrayList<>();
        connection = new DBHelper(this, "Recetario", null, 1);
        Cursor cur = connection.getTodasRecetas();
        if(cur.moveToFirst()){
            do{
                String nombre = cur.getString(0);
                RecetaModel receta = new RecetaModel(nombre, "epoca", 0);
                recetas.add(receta);
            } while (cur.moveToNext());
        }
    }

    public void anterior(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);
    }
}