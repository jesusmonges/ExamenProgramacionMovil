package com.android.consultaruc.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "consultaruc.db";
    public static final String TABLA_HISTORIAL = "historial";
    public static final String TABLA_TOKENS = "tokens";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_HISTORIAL + "(" +
                "idConsulta INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT NOT NULL," +
                "documentoConsulta TEXT NOT NULL," +
                "razonSocialRes TEXT NOT NULL," +
                "latitud TEXT NOT NULL," +
                "longitud TEXT NOT NULL," +
                "documentoRes TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_TOKENS + "(" +
                "idToken INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUsuario TEXT NOT NULL," +
                "token TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_HISTORIAL);
        onCreate(sqLiteDatabase);

    }
}
