package com.alexgl.recetario.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alexgl.recetario.models.IngredienteModel;
import com.alexgl.recetario.models.RecetaModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //CREA LAS TABLAS
        sqLiteDatabase.execSQL("CREATE TABLE 'RECETA' (\n" +
                "\t'nombre'\tTEXT,\n" +
                "\t'epoca_tipico'\tTEXT,\n" +
                "\t'de_serie'\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY('nombre')\n" +
                ");");
        sqLiteDatabase.execSQL("CREATE TABLE 'PASO' (\n" +
                "\t'nombre'\tTEXT,\n" +
                "\t'numero'\tINTEGER,\n" +
                "\t'descripcion'\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY('nombre','numero'),\n" +
                "\tFOREIGN KEY('nombre') REFERENCES 'RECETA'('nombre')\n" +
                ");");
        sqLiteDatabase.execSQL("CREATE TABLE 'INGREDIENTE' (\n" +
                "\t'nombre'\tTEXT,\n" +
                "\t'tipo'\tTEXT,\n" +
                "\tPRIMARY KEY('nombre')\n" +
                ");\n");
        sqLiteDatabase.execSQL("CREATE TABLE 'INGREDIENTES_RECETA' (\n" +
                "\t'nombre_ingrediente'\tTEXT,\n" +
                "\t'nombre_receta'\tTEXT,\n" +
                "\t'cantidad'\tNUMERIC NOT NULL,\n" +
                "\t'unidad_medida'\tTEXT NOT NULL,\n" +
                "\tFOREIGN KEY('nombre_ingrediente') REFERENCES 'INGREDIENTE'('nombre'),\n" +
                "\tFOREIGN KEY('nombre_receta') REFERENCES 'RECETA'('nombre'),\n" +
                "\tPRIMARY KEY('nombre_ingrediente','nombre_receta')\n" +
                ");");

        //RELLENA LAS TABLAS
        rellenaTablas(sqLiteDatabase);

    }

    private void rellenaTablas(SQLiteDatabase sqLiteDatabase) {
        String[] querys = {"INSERT INTO \"RECETA\" (nombre, epoca_tipico, de_serie) VALUES ('Paella', 'Verano', 1);" ,
                "INSERT INTO \"RECETA\" (nombre, epoca_tipico, de_serie) VALUES ('Cocido Madrileño', 'Invierno', 2);" ,

                "INSERT INTO \"PASO\" (nombre, numero, descripcion) VALUES ('Paella', 1, 'Sofría el pollo y el conejo en aceite de oliva');" ,
                "INSERT INTO \"PASO\" (nombre, numero, descripcion) VALUES ('Paella', 2, 'Añada los vegetales y sofría hasta que estén tiernos');" ,
                "INSERT INTO \"PASO\" (nombre, numero, descripcion) VALUES ('Cocido Madrileño', 1, 'En una olla grande, cueza el tocino, el jamón y el pollo');" ,
                "INSERT INTO \"PASO\" (nombre, numero, descripcion) VALUES ('Cocido Madrileño', 2, 'Añada los garbanzos y las verduras, y cocine hasta que estén tiernos');" ,
                "INSERT INTO \"PASO\" (nombre, numero, descripcion) VALUES ('Cocido Madrileño', 3, 'Rellenando el tercer paso.');" ,
                "INSERT INTO \"PASO\" (nombre, numero, descripcion) VALUES ('Cocido Madrileño', 4, 'Rellenando el cuarto paso.');" ,
                "INSERT INTO \"PASO\" (nombre, numero, descripcion) VALUES ('Cocido Madrileño', 5, 'Rellenando el quinto paso.');" ,

                "INSERT INTO \"INGREDIENTE\" (nombre, tipo) VALUES ('Pollo', 'Carne');" ,
                "INSERT INTO \"INGREDIENTE\" (nombre, tipo) VALUES ('Conejo', 'Carne');" ,
                "INSERT INTO \"INGREDIENTE\" (nombre, tipo) VALUES ('Aceite de Oliva', 'Aceite');" ,
                "INSERT INTO \"INGREDIENTE\" (nombre, tipo) VALUES ('Tocino', 'Carne');" ,
                "INSERT INTO \"INGREDIENTE\" (nombre, tipo) VALUES ('Jamón', 'Carne');" ,
                "INSERT INTO \"INGREDIENTE\" (nombre, tipo) VALUES ('Garbanzos', 'Legumbre');" ,

                "INSERT INTO \"INGREDIENTES_RECETA\" (nombre_ingrediente, nombre_receta, cantidad, unidad_medida) VALUES ('Pollo', 'Paella', 500, 'g');" ,
                "INSERT INTO \"INGREDIENTES_RECETA\" (nombre_ingrediente, nombre_receta, cantidad, unidad_medida) VALUES ('Conejo', 'Paella', 500, 'g');" ,
                "INSERT INTO \"INGREDIENTES_RECETA\" (nombre_ingrediente, nombre_receta, cantidad, unidad_medida) VALUES ('Aceite de Oliva', 'Paella', 100, 'ml');" ,
                "INSERT INTO \"INGREDIENTES_RECETA\" (nombre_ingrediente, nombre_receta, cantidad, unidad_medida) VALUES ('Tocino', 'Cocido Madrileño', 200, 'g');" ,
                "INSERT INTO \"INGREDIENTES_RECETA\" (nombre_ingrediente, nombre_receta, cantidad, unidad_medida) VALUES ('Jamón', 'Cocido Madrileño', 200, 'g');" ,
                "INSERT INTO \"INGREDIENTES_RECETA\" (nombre_ingrediente, nombre_receta, cantidad, unidad_medida) VALUES ('Garbanzos', 'Cocido Madrileño', 500, 'g');"};
        int posicion = 1;
        for (String query : querys){
            sqLiteDatabase.execSQL(query);
            Log.d("DATOS " + posicion, query);
            posicion++;

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS RECETA");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PASO");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS INGREDIENTE");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS INGREDIENTES_RECETA");
        onCreate(sqLiteDatabase);
    }

    /**
     * Obtiene todas las recetas de la base de datos
     * @return cursor con todas las recetas
     */

    public Cursor getTodasRecetas(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT nombre FROM RECETA", null);

        return cur;
    }

    /**
     * Se le pasa el nombre de una receta y obtiene un objeto RecetaModel
     * @param nombreReceta
     * @return un objeto RecetaModel
     */
    public RecetaModel getReceta(String nombreReceta){
        RecetaModel receta = null;
        ArrayList<IngredienteModel> ingredientes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //Se obtiene un objeto RecetaModel
        Cursor cursorReceta = db.rawQuery("SELECT * FROM RECETA\n" +
                                                 "WHERE nombre = " + "'" + nombreReceta + "'", null);
        if(cursorReceta.moveToFirst()){
            do{
                String nombre = cursorReceta.getString(0);
                String epoca = cursorReceta.getString(1);
                Integer deSerie = cursorReceta.getInt(2);
                receta = new RecetaModel(nombre, epoca, deSerie);
            } while (cursorReceta.moveToNext());
        }
        cursorReceta.close();

        //Se obtienen los ingredientes
        Cursor cursorIngredientes = db.rawQuery("SELECT INGREDIENTES_RECETA.nombre_ingrediente, INGREDIENTES_RECETA.cantidad, INGREDIENTES_RECETA.unidad_medida FROM RECETA, INGREDIENTE, INGREDIENTES_RECETA\n" +
                                        "WHERE RECETA.nombre = INGREDIENTES_RECETA.nombre_receta\n" +
                                        "AND INGREDIENTE.nombre = INGREDIENTES_RECETA.nombre_ingrediente\n" +
                                        "AND RECETA.nombre = " + "'" + nombreReceta + "'", null);

        if(cursorIngredientes.moveToFirst()){
            do{
                String nombre = cursorIngredientes.getString(0);
                Integer cantidad = cursorIngredientes.getInt(1);
                String unidadMedida = cursorIngredientes.getString(2);
                IngredienteModel ingrediente = new IngredienteModel(nombre, cantidad, unidadMedida);
                ingredientes.add(ingrediente);
            } while (cursorIngredientes.moveToNext());
        }
        cursorIngredientes.close();

        //se le añaden los ingredientes al objeto que contiene la receta
        receta.setIngredientes(ingredientes);

        //Se obtienen los pasos
        Cursor cursorPasos = db.rawQuery("SELECT PASO.numero, PASO.descripcion FROM PASO, RECETA\n" +
                "WHERE PASO.nombre = RECETA.nombre\n" +
                "AND RECETA.nombre = " + "'" + nombreReceta + "'", null);

        if(cursorPasos.moveToFirst()){
            Map<Integer, String> pasos = new TreeMap<>();;
            do{
                Integer numPaso = cursorPasos.getInt(0);
                String instrucciones = cursorPasos.getString(1);
                pasos.put(numPaso, instrucciones);
            } while (cursorPasos.moveToNext());
            receta.setPasos(pasos); //se le añaden los pasos al objeto de la receta
        }
        cursorPasos.close();

        return receta;
    }

}
