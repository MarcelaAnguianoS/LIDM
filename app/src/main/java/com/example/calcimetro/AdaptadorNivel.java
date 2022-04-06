package com.example.calcimetro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorNivel extends RecyclerView.Adapter<AdaptadorNivel.ViewHolderCalcio> {

    ArrayList<ItemVo> ListaNivel;

    public AdaptadorNivel(ArrayList<ItemVo> listaNivel) {
        ListaNivel = listaNivel;
    }

    @NonNull
    @Override
    public ViewHolderCalcio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,null,false);
        return new ViewHolderCalcio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCalcio holder, int position) {


    }

    @Override
    public int getItemCount() {
        return ListaNivel.size();
    }

    public class ViewHolderCalcio extends RecyclerView.ViewHolder {
        TextView nivelGlucosa,nota1,nota2,hora;
        public ViewHolderCalcio(@NonNull View itemView) {
            super(itemView);


        }
    }
}
