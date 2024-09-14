package com.android.consultaruc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    private Button btnSignIn;
    private  EditText etNombre;
    private EditText etUsuario;
    private EditText etPassword;
    private TextView tvErroresRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_signin);
        btnSignIn = findViewById(R.id.btnRegistro);
        etNombre = findViewById(R.id.etNombre);
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        tvErroresRegistro = findViewById(R.id.tvErroresRegistro);
        hideSystemUI();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String usuario = etUsuario.getText().toString();
                String pass = etPassword.getText().toString();

                registrar(nombre, usuario, pass);
            }
        });
    }

    private void registrar(String nombre, String alias, String pass) {

        String url = "https://pink-ferret-306887.hostingersite.com/vante/public/Usuarios/guardar";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("exito")) {
                                Toast.makeText(SignInActivity.this, "Registrado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                tvErroresRegistro.setText(jsonObject.getString("mensaje"));
                                tvErroresRegistro.setVisibility(View.VISIBLE);
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
                params.put("nombreCompleto", nombre);
                params.put("alias", alias);
                params.put("password", pass);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SignInActivity.this);
        requestQueue.add(request);

    }
    private void hideSystemUI() {
        // Hacer la actividad a pantalla completa
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);   // Oculta la barra de estado
    }

}
