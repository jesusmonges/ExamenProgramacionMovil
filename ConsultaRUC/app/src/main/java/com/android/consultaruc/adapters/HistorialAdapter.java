package com.android.consultaruc.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.consultaruc.R;
import com.android.consultaruc.entidad.Historial;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private final List<Historial> historialList;

    // Constructor para recibir la lista de objetos Historial
    public HistorialAdapter(List<Historial> historialList) {
        this.historialList = historialList;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_historial, parent, false);
        return new HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        Historial historial = historialList.get(position);
        holder.tvDocumento.setText(historial.getDocumentoRes());
        holder.tvFecha.setText(historial.getFecha());
    }

    @Override
    public int getItemCount() {
        return historialList.size();
    }

    // ViewHolder para mantener las vistas de los ítems
    public static class HistorialViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDocumento;
        public TextView tvFecha;

        public HistorialViewHolder(View itemView) {
            super(itemView);
            tvDocumento = itemView.findViewById(R.id.tvHDocumento);
            tvFecha = itemView.findViewById(R.id.tvHFecha);

        }
    }
}
