package com.alexgl.recetario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alexgl.recetario.database.DBHelper;
import com.alexgl.recetario.models.RecetaModel;

public class Pasos extends AppCompatActivity {

    public static int pasoActual = 1;
    private SharedPreferences sp;
    DBHelper connection;
    RecetaModel receta;
    TextView titulo, instrucciones;
    Button atras, continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);

        titulo = (TextView) findViewById(R.id.etiqueta_titulo_paso);
        instrucciones = (TextView) findViewById(R.id.etiqueta_instrucciones_paso);
        atras = (Button) findViewById(R.id.botonAtrasPasos);
        continuar = (Button) findViewById(R.id.botonContinuarPasos);

        connection = new DBHelper(this, "Recetario", null, 1);
        sp = getSharedPreferences("preferences", MODE_PRIVATE);
        //pasoActual = sp.getInt("pasoActual", 1);
        receta = (RecetaModel) getIntent().getSerializableExtra("receta");

        actualizarVista();
    }

    private void actualizarVista() {
        etiquetarBotones();
        mostrarPasoActual(receta);
    }

    private void etiquetarBotones() {
        if (pasoActual == 1){
            atras.setText("VOLVER");
            continuar.setText("SIGUIENTE");
        } else if(pasoActual == receta.getNumeroPasosTotal()){
            atras.setText("ANTERIOR");
            continuar.setText("FINALIZAR");
        } else {
            atras.setText("ANTERIOR");
            continuar.setText("SIGUIENTE");
        }
    }

    private void mostrarPasoActual(RecetaModel receta) {
        titulo.setText("PASO " + pasoActual + " DE " + receta.getNumeroPasosTotal());
        instrucciones.setText(receta.getPaso(pasoActual));
    }

    public void siguiente(View view){
        if(pasoActual < receta.getNumeroPasosTotal()){
            pasoActual++;
            actualizarVista();
        } else {
            pasoActual = 1;
            Intent intent = new Intent(view.getContext(), ListadoRecetas.class);
            view.getContext().startActivity(intent);
        }
    }

    public void anterior(View view){
        if(pasoActual > 1){
            pasoActual--;
            actualizarVista();
        } else {
            Intent intent = new Intent(view.getContext(), ListadoRecetas.class);
            view.getContext().startActivity(intent);
        }
    }
}