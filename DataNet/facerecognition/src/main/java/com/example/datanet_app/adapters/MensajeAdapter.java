package com.example.datanet_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datanet_app.Models.Name;


import org.opencv.cultoftheunicorn.marvel.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder>{
    private int resource;
    private ArrayList<Name> nombresList;
    Locale id = new Locale("es", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-YYYY", id);

    public MensajeAdapter(ArrayList<Name> nombresList, int resource){
        this.nombresList = nombresList;
        this.resource = resource;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {


        Name nombre = nombresList.get(index);

        viewHolder.txtViewMensaje.setText(nombre.getName());
        viewHolder.txtSesion1.setText("Fecha:"+simpleDateFormat.format(nombre.getDate()));



    }



    @Override
    public int getItemCount() {
        return nombresList.size();
    }







    public class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView txtViewMensaje,txtSesion1;
        public View view;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.txtViewMensaje = (TextView) view.findViewById(R.id.txtMensaje10);
            this.txtSesion1 = (TextView) view.findViewById(R.id.txtMensaje12);
        }

    }



}


