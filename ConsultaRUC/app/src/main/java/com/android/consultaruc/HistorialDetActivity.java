package com.android.consultaruc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.consultaruc.adapters.AdapterDetallado;
import com.android.consultaruc.basedatos.DbHistory;
import com.android.consultaruc.entidad.Historial;

import java.util.ArrayList;
import java.util.List;

public class HistorialDetActivity extends AppCompatActivity {

    private RecyclerView vistaHistorialDet;
    private ArrayList<Historial> listaHistorialDet;
    private AdapterDetallado historialDetAdapter;

    EditText etNombre, etTelefono, etCorreo;
    Button btnGuardar;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);


        // Inicializa el RecyclerView
        vistaHistorialDet = findViewById(R.id.vistaHistorialDet);

        // Configura el LayoutManager
        vistaHistorialDet.setLayoutManager(new LinearLayoutManager(this));

        // Obtener los datos
        DbHistory dbHistory = new DbHistory(this);
        listaHistorialDet = dbHistory.mostrarHistorial();

        // Inicializa el adaptador y asigna la lista de datos
        historialDetAdapter = new AdapterDetallado(listaHistorialDet);

        // Asigna el adaptador al RecyclerView
        vistaHistorialDet.setAdapter(historialDetAdapter);
    }
}
