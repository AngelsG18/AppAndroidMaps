package com.example.appandroidmaps;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
        Administrador admin = new Administrador(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String sNombre   = et_Nom.getText().toString();
        String sApellido = et_Apellido.getText().toString();
        String sUsuario  = et_User01.getText().toString();
        String sPassword = et_pass.getText().toString();

        if(!sNombre.isEmpty() && !sApellido.isEmpty() && !sUsuario.isEmpty() && !sPassword.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("nombre", sNombre); registro.put("Apellido", sApellido);
            registro.put("User", sUsuario); registro.put("Pass", sPassword);
            bd.insert("seg_usuarios", null, registro);
            bd.close(); et_Nom.setText(""); et_Apellido.setText(""); et_User01.setText(""); et_pass.setText("");
            Toast.makeText(this ,"Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();
            Intent sRegistrar = new Intent(this, MainActivity.class);
            startActivity(sRegistrar);
        }else{
            Toast.makeText(this ,"Debe Llenar Todos los Datos", Toast.LENGTH_SHORT).show();
        }

    }

}