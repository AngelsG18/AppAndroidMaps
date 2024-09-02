package com.example.appandroidmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FrmRegistrar extends AppCompatActivity {
    public EditText  et_Nom, et_Apellido, et_User01, et_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_registrar);
        et_Nom = (EditText)findViewById(R.id.TxtNombre);
        et_Apellido = (EditText)findViewById(R.id.TxtApellido);
        et_User01 = (EditText)findViewById(R.id.Txtuser01);
        et_pass = (EditText)findViewById(R.id.TxtPass);

    }

    public void sRegistrar(View view) {
        String sNombre   = et_Nom.getText().toString();
        String sApellido = et_Apellido.getText().toString();
        String sUsuario  = et_User01.getText().toString();
        String sPassword = et_pass.getText().toString();

        if(!sNombre.isEmpty() && !sApellido.isEmpty() && !sUsuario.isEmpty() && !sPassword.isEmpty()){
            Intent sRegistrar = new Intent(this, MainActivity.class);
            startActivity(sRegistrar);
        }else{
            Toast.makeText(this ,"Debe Llenar Todos los Datos", Toast.LENGTH_SHORT).show();
        }

    }

}