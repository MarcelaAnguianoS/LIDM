package com.example.calcimetro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensaje> {

    private List<itemActivity> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes( Context c) {
        this.c = c;
    }

    public void  addMensaje (itemActivity m){
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }
    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.activity_item,parent,false);
        return new HolderMensaje(v);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getHora().setText(listMensaje.get(position).getHora());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());

    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
