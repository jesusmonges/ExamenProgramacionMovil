package com.android.consultaruc;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.consultaruc.basedatos.DbHistory;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegistro;
    private EditText etUsuario;
    private EditText etPassword;
    private TextView tvErrores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        tvErrores = findViewById(R.id.tvErrores);
        hideSystemUI();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String pass = etPassword.getText().toString();
                String ipLocal = ObtenerIP.getLocalIPAddress();

                verificar(usuario, pass, ipLocal);
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);

            }
        });
    }


    private void verificar(String usuario, String pass, String ipDispositivo) {

        String url = "https://pink-ferret-306887.hostingersite.com/vante/public/Usuarios/validar";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("exito")) {
                                Toast.makeText(LoginActivity.this, "Bienvenido "+jsonObject.getString("usuario"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("visible", false);
                                startActivity(intent);
                                finish();
                            } else {
                                tvErrores.setText(jsonObject.getString("mensaje"));
                                tvErrores.setVisibility(View.VISIBLE);
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
                params.put("usuario", usuario);
                params.put("password", pass);
                params.put("ipDispositivo", ipDispositivo);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);

    }

    private void insertarToken(String idUsuario, String token) {
        DbHistory tokenDb = new DbHistory(this);
        tokenDb.insertarToken(idUsuario, token);
        long idToken = tokenDb.insertarToken(idUsuario, token);
        Toast.makeText(this, "Registrando token..." + idToken, Toast.LENGTH_SHORT).show();

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