package com.android.consultaruc;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.consultaruc.adapters.AdapterDetallado;
import com.android.consultaruc.adapters.ConsultaAdapter;
import com.android.consultaruc.adapters.HistorialAdapter;
import com.android.consultaruc.basedatos.DbHistory;
import com.android.consultaruc.entidad.Historial;
import com.android.consultaruc.interfaces.InsertarConsulta;
import com.android.consultaruc.models.Datos;
import com.android.consultaruc.models.Token;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private EditText etDocumento;
    private TextView errorTextView;
    private ImageButton btnBuscar;
    private RecyclerView listaResultados;
    private RecyclerView vistaHistorial;
    private RecyclerView vistaHDetallado;
    private ArrayList<Historial> listaHistorial;
    private ConsultaAdapter consultaAdapter;
    private HistorialAdapter historialAdapter;
    private AdapterDetallado adapterDetallado;
    private ArrayList<Datos> consultaList = new ArrayList<>();
    private InsertarConsulta insertar;
    private String ipLocal;
    private FusedLocationProviderClient fusedLocationClient;
    private DbHistory dbToken;
    private Token token;
    private Boolean visible = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbToken = new DbHistory(this);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        visible = intent.getBooleanExtra("visible", true);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etDocumento = findViewById(R.id.etDocumento);
        btnBuscar = findViewById(R.id.btnBuscar);
        listaResultados = findViewById(R.id.listaResultados);
        vistaHistorial = findViewById(R.id.vistaHistorial);
        errorTextView = findViewById(R.id.errorTextView);

        vistaHistorial.setLayoutManager(new LinearLayoutManager(this));


        // Configura el RecyclerView para los resultados de consulta
        consultaAdapter = new ConsultaAdapter(consultaList);
        listaResultados.setLayoutManager(new LinearLayoutManager(this));
        listaResultados.setAdapter(consultaAdapter);
        mostrarHistorial();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                verificarConsultas("");
                errorTextView.setVisibility(View.GONE);
                String query = etDocumento.getText().toString();
                if (query.isEmpty()) {
                    errorTextView.setText("El campo es obligatorio");
                    errorTextView.setVisibility(View.VISIBLE);
                    etDocumento.requestFocus();
                } else {
//                    buscar(query);
                    String idUsuario = "4";
                    String ipLocal = ObtenerIP.getLocalIPAddress();
                    Date fechaActual = new Date();

                    // Formatear la fecha en el formato 'yyyy-MM-dd'
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String fecha = formato.format(fechaActual).toString();
                    verificarConsultas(query, fecha, ipLocal);
                }


            }
        });

    }

    private void buscar(String documento, String fecha, String ipLocal) {
        String url = "https://tajysoftware.com.py/apiRUC/public/consulta/7788316Me0YD5d/" + documento;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("exito")) {
                                //Encontro
                                String razon = jsonObject.getJSONObject("datos").getString("RAZON_SOCIAL");
                                String doc = jsonObject.getJSONObject("datos").getString("DOCUMENTO");
                                int tipo = jsonObject.getJSONObject("datos").getInt("TIPO");
                                Datos aux = new Datos(tipo + "", doc, razon);
                                consultaList.clear();
                                consultaList.add(aux);
                                consultaAdapter.notifyDataSetChanged();
                                insertarConsulta("4", ipLocal, fecha);
                                insertarHistorial(documento, razon, documento, fecha);
                            } else {
                                Toast.makeText(MainActivity.this, "No encontro", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error 2", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error 3", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void insertarHistorial(String documentoConsulta, String razonSocial, String documento, String fecha) {
        DbHistory dbConsultas = new DbHistory(this);
        dbConsultas.insertarConsulta(documentoConsulta, razonSocial, documento, fecha);
        Toast.makeText(this, "Registrando historial...", Toast.LENGTH_SHORT).show();
        mostrarHistorial();
    }

    private void vaciarHistorial() {
        DbHistory vaciar = new DbHistory(this);
        vaciar.vaciarHistorial();
        Toast.makeText(this, "Vaciando historial...", Toast.LENGTH_SHORT).show();
        mostrarHistorial();
    }

    private void mostrarHistorial() {
        DbHistory dbHistory = new DbHistory(MainActivity.this);
        listaHistorial = dbHistory.mostrarHistorial();
        historialAdapter = new HistorialAdapter(listaHistorial);
        vistaHistorial.setAdapter(historialAdapter);

    }

    private void insertarConsulta(String idUsuario, String ipDispositivo, String fecha) {

        String url = "https://pink-ferret-306887.hostingersite.com/vante/public/Consultas/guardar";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(MainActivity.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Error en la inserción", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", idUsuario);
                params.put("ipDispositivo", ipDispositivo);
                params.put("fecha", fecha);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);

    }

    private void verificarToken(String idUsuario, String token) {

        String url = "https://pink-ferret-306887.hostingersite.com/vante/public/Consultas/guardar";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(MainActivity.this, "Hay token", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No hay", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", idUsuario);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);

    }

    private void verificarConsultas(String query, String fecha, String ipLocal) {
        String url = "https://pink-ferret-306887.hostingersite.com/vante/public/Consultas/verificar/" + ipLocal + "/" + fecha;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String cantidad = jsonObject.getString("cantidad");
                           if(visible){
                               if (parseInt(cantidad) >= 5) {
                                   errorTextView.setText("Limite diario superado");
                                   errorTextView.setVisibility(View.VISIBLE);
                                   etDocumento.requestFocus();
                               } else {
                                   errorTextView.setText("Consultas restantes: " + (5 - (parseInt(cantidad)+1)));
                                   errorTextView.setVisibility(View.VISIBLE);
                                   buscar(query, fecha, ipLocal);
                               }
                           } else {
                               if (parseInt(cantidad) >= 10) {
                                   errorTextView.setText("Limite diario superado");
                                   errorTextView.setVisibility(View.VISIBLE);
                                   etDocumento.requestFocus();
                               } else {
                                   errorTextView.setText("Consultas restantes: " + (10 - (parseInt(cantidad)+1)));
                                   errorTextView.setVisibility(View.VISIBLE);
                                   buscar(query, fecha, ipLocal);
                               }
                           }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error 2", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error 3", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuHistorial) {
            verHistorial();
            return true;
        } else if (item.getItemId() == R.id.menuLogin){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menuVaciar){
            new AlertDialog.Builder(this)
                    .setTitle("Vaciar historial")
                    .setMessage("¿Desea vaciar el historial?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            vaciarHistorial();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    })
                    .show();

        } else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }

    private void verHistorial() {
        Intent intent = new Intent(this, HistorialDetActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        MenuItem menuLogin = menu.findItem(R.id.menuLogin);
        menuLogin.setVisible(visible);
        return true;
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Obtén la latitud y longitud
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Toast.makeText(MainActivity.this, "Latitud: " + latitude + " Longitud: " + longitude, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }


}
