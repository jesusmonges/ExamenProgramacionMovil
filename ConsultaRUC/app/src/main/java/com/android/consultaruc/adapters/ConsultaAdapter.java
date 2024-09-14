package com.android.consultaruc.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.consultaruc.models.ConsultaResponse;
import com.android.consultaruc.R;
import com.android.consultaruc.models.Datos;

import java.util.List;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.ViewHolder> {
    private List<Datos> datosList;

    public ConsultaAdapter(List<Datos> datosList) {
        this.datosList = datosList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_consulta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Datos datos = datosList.get(position);
        holder.documentoTextView.setText(datos.getDOCUMENTO());
        holder.razonSocialTextView.setText(datos.getRAZON_SOCIAL());
    }

    @Override
    public int getItemCount() {
        return datosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView documentoTextView;
        TextView razonSocialTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            documentoTextView = itemView.findViewById(R.id.tvDocumento);
            razonSocialTextView = itemView.findViewById(R.id.tvRazonSocial);
        }
    }
}

