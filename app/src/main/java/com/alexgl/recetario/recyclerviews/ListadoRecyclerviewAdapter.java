package com.alexgl.recetario.recyclerviews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexgl.recetario.PortadaReceta;
import com.alexgl.recetario.R;
import com.alexgl.recetario.models.*;

import java.util.ArrayList;

public class ListadoRecyclerviewAdapter extends RecyclerView.Adapter<ListadoRecyclerviewAdapter.MyViewHolder>{

    private Context context;
    ArrayList<RecetaModel> recetaModels;
    SharedPreferences sp;

    public ListadoRecyclerviewAdapter(Context context, ArrayList<RecetaModel> recetaModels) {
        this.context = context;
        this.recetaModels = recetaModels;
        sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ListadoRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listado_recetas_recyclerview, parent, false);
        return new ListadoRecyclerviewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListadoRecyclerviewAdapter.MyViewHolder holder, int position) {
        String recetaElegida = recetaModels.get(position).getNombre();
        holder.nombre.setText(recetaElegida);

        //selección de tarjeta
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("nombre", recetaElegida);
                edit.apply();
                launchPortadaReceta(view);
            }
        });
    }

    private void launchPortadaReceta(View view) {
        Intent intent = new Intent(view.getContext(), PortadaReceta.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return recetaModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_receta_rv);
        }
    }
}
