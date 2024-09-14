package com.android.consultaruc.basedatos;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.consultaruc.entidad.Historial;
import com.android.consultaruc.entidad.Tokens;
import com.android.consultaruc.models.Token;

import java.util.ArrayList;

public class DbHistory extends DbHelper {

    Context context;

    public DbHistory(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarConsulta(String documentoConsulta ,String razonSocial, String documento, String fecha) {

        long idConsulta = 0;

        try {
            DbHistory dbHelper = new DbHistory(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("documentoConsulta", documentoConsulta);
            values.put("fecha", fecha);
            values.put("razonSocialRes", razonSocial);
            values.put("latitud", "-25.46083083870929");
            values.put("longitud", "-56.005837054723926");
            values.put("documentoRes", documento);

            idConsulta = db.insert(TABLA_HISTORIAL, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return idConsulta;
    }

    public long insertarToken(String idUsuario ,String token) {

        long idToken = 0;

        try {
            DbHistory dbHelper = new DbHistory(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario", idUsuario);
            values.put("token", token);


            idToken = db.insert(TABLA_TOKENS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return idToken;
    }

    public ArrayList<Historial> mostrarHistorial() {
        DbHistory dbHistorial = new DbHistory(context);
        SQLiteDatabase db = dbHistorial.getWritableDatabase();

        ArrayList<Historial> arrayHistorial = new ArrayList<>();
        Historial consultas;
        Cursor cursorConsultas;

        cursorConsultas = db.rawQuery(
                "SELECT idConsulta,fecha, documentoConsulta, razonSocialRes, documentoRes, latitud,longitud FROM " + TABLA_HISTORIAL +
                        " ORDER BY idConsulta DESC", null);

        if (cursorConsultas != null) {
//            Log.d("DB_QUERY", "Número de registros encontrados: " + cursorConsultas.getCount());

            if (cursorConsultas.moveToFirst()) {
                do {
                    consultas = new Historial();
                    consultas.setIdConsulta(cursorConsultas.getInt(0));
                    consultas.setFecha(cursorConsultas.getString(1));
                    consultas.setDocumentoConsulta(cursorConsultas.getString(2));
                    consultas.setRazonSocialRes(cursorConsultas.getString(3));
                    consultas.setDocumentoRes(cursorConsultas.getString(4));
                    consultas.setLatitud(cursorConsultas.getString(5));
                    consultas.setLongitud(cursorConsultas.getString(6));
                    arrayHistorial.add(consultas);
                } while (cursorConsultas.moveToNext());
            } else {
                Log.d("DB_QUERY", "No se encontraron registros.");
            }

            cursorConsultas.close();
        } else {
            Log.d("DB_QUERY", "El cursor es nulo.");
        }

        return arrayHistorial;
    }

    @SuppressLint("Range")
    public Token verToken() {
        SQLiteDatabase db = this.getReadableDatabase();
        Token token = null;
        Cursor cursor = null;

        try {
            String query = "SELECT token FROM " + TABLA_TOKENS + " ORDER BY idToken DESC LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                String idUsuarioDB = cursor.getString(cursor.getColumnIndex("idUsuario"));
                String tokenDB = cursor.getString(cursor.getColumnIndex("token"));
                token = new Token(idUsuarioDB, tokenDB);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return token;
    }


    public Historial verConsulta(int id) {

        DbHistory dbHistorial = new DbHistory(context);
        SQLiteDatabase db = dbHistorial.getWritableDatabase();

        Historial consulta = null;
        Cursor cursorConsultas;

        cursorConsultas = db.rawQuery("SELECT * FROM " + TABLA_HISTORIAL
                + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorConsultas.moveToFirst()) {
            consulta = new Historial();
            consulta.setIdConsulta(cursorConsultas.getInt(0));
            consulta.setDocumentoConsulta(cursorConsultas.getString(1));
            consulta.setRazonSocialRes(cursorConsultas.getString(2));
            consulta.setDocumentoRes(cursorConsultas.getString(3));
        }

        cursorConsultas.close();

        return consulta;
    }

//    public boolean editarContacto(int id, String nombre, String telefono, String correo) {
//
//        boolean correcto = false;
//
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        try {
//            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET nombre = '" + nombre
//                    + "', telefono = '" + telefono + "', correo = '"
//                    + correo + "' WHERE id='" + id + "' ");
//            correcto = true;
//        } catch (Exception ex) {
//            ex.toString();
//            correcto = false;
//        } finally {
//            db.close();
//        }
//
//        return correcto;
//    }

    public boolean vaciarHistorial(){
        boolean exito = false;

        DbHistory dbHistorial = new DbHistory(context);
        SQLiteDatabase db = dbHistorial.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_HISTORIAL );
            exito = true;
        } catch (Exception ex) {
            ex.toString();
            exito = false;
        } finally {
            db.close();
        }

        return exito;
    }

    public boolean eliminarConsulta(int id) {

        boolean exito = false;

        DbHistory dbHistorial = new DbHistory(context);
        SQLiteDatabase db = dbHistorial.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_HISTORIAL + " WHERE idConsulta = '" + id + "'");
            exito = true;
        } catch (Exception ex) {
            ex.toString();
            exito = false;
        } finally {
            db.close();
        }

        return exito;
    }
}
