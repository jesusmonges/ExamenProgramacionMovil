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

public class AdapterDetallado extends RecyclerView.Adapter<AdapterDetallado.NuevoItemViewHolder> {

    private final List<Historial> historialList;

    // Constructor para recibir la lista de objetos Historial
    public AdapterDetallado(List<Historial> historialList) {
        this.historialList = historialList;
    }

    @NonNull
    @Override
    public NuevoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_historial_detallado, parent, false);
        return new NuevoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NuevoItemViewHolder holder, int position) {
        Historial historial = historialList.get(position);
        holder.tvDocumento.setText(historial.getDocumentoRes());
        holder.tvRazonSocial.setText(historial.getRazonSocialRes());
        holder.tvFecha.setText(historial.getFecha());
        holder.tvCoordenadas.setText(String.format("%s%s", historial.getLatitud(), historial.getLongitud()));
    }

    @Override
    public int getItemCount() {
        return historialList.size();
    }

    // ViewHolder para mantener las vistas de los ítems
    public static class NuevoItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDocumento;
        public TextView tvRazonSocial;
        public TextView tvFecha;
        public TextView tvCoordenadas;

        public NuevoItemViewHolder(View itemView) {
            super(itemView);
            tvDocumento = itemView.findViewById(R.id.tvHDocumento);
            tvRazonSocial = itemView.findViewById(R.id.tvHRazonSocial);
            tvFecha = itemView.findViewById(R.id.tvHFecha);
            tvCoordenadas = itemView.findViewById(R.id.tvHCoordenadas);
        }
    }
}
