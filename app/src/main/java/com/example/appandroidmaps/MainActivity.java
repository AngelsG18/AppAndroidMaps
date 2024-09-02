package com.example.appandroidmaps;

import android.content.Intent;
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
        setContentView(R.layout.activity_main);
        et_User=(EditText)findViewById(R.id.Txtuser);
        et_Pass=(EditText)findViewById(R.id.TxtPassword);
    }

    public void Registrar(View view) {
        Intent sRegistrar = new Intent(this, FrmRegistrar.class);
        startActivity(sRegistrar);
    }

    public void  IniciarSession (View view){
            String sUser = et_User.getText().toString();
            String sPass = et_Pass.getText().toString();

            if(!sUser.isEmpty() && !sPass.isEmpty()){
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this ,"Ingrese su Usuario y Contraseña", Toast.LENGTH_SHORT).show();
            }


    }


}