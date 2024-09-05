package com.example.appandroidmaps;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
private EditText et_User, et_Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        et_User=(EditText)findViewById(R.id.Txtuser);
        et_Pass=(EditText)findViewById(R.id.TxtPassword);
    }

    public void Registrar(View view) {
        Intent sRegistrar = new Intent(this, FrmRegistrar.class);
        startActivity(sRegistrar);
    }

    public void IniciarSession(View view) {
        Administrador admin = new Administrador(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String sUser = et_User.getText().toString();
        String sPass = et_Pass.getText().toString();

        if (!sUser.isEmpty() && !sPass.isEmpty()) {
            Cursor fila = bd.rawQuery(
                    "SELECT * FROM seg_usuarios WHERE User = ? AND Pass = ?", new String[]{sUser, sPass});

            if (fila.moveToFirst()) {
                bd.close();
                Toast.makeText(this, "Usuario encontrado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,buscadorActivity.class);
                startActivity(intent);
            } else {
                bd.close();
                Toast.makeText(this, "Usuario No Encontrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese su Usuario y Contraseña", Toast.LENGTH_SHORT).show();
        }
    }

}