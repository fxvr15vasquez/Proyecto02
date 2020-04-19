package com.example.proyecto02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto02.modelo.Estudiante;
import com.example.proyecto02.modelo.Persona;
import com.example.proyecto02.modelo.Usuario;
import com.example.proyecto02.modeloDB.estudianteDB;
import com.example.proyecto02.modeloDB.personaDB;
import com.example.proyecto02.modeloDB.usuarioDB;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    EditText nomb, apell, correo, telef, user, pass, pass2;
    Button btnguar, btncanc;

    personaDB perDB;
    usuarioDB usuDB;
    estudianteDB estDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        usuDB = new usuarioDB();
        perDB = new personaDB();
        estDB = new estudianteDB();

        nomb=(EditText) findViewById(R.id.etxtRnomb);
        apell=(EditText) findViewById(R.id.etxtRapell);
        correo=(EditText) findViewById(R.id.etxtRcorelec);
        telef=(EditText) findViewById(R.id.etxtRtelef);
        user=(EditText) findViewById(R.id.etxtRuser);
        pass=(EditText) findViewById(R.id.edtxtRpass);
        pass2=(EditText) findViewById(R.id.edtxtRpasscnf);
        btnguar= (Button) findViewById(R.id.btnRguard);
        btncanc= (Button) findViewById(R.id.btnRcanc);

        btnguar.setOnClickListener(this);
        btncanc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnRguard:
                if (pass.getText().toString().equals(pass2.getText().toString())){
                    Persona pe = new Persona();
                    pe.setPer_id(perDB.maxPers(this));
                    pe.setPer_nombre(nomb.getText().toString());
                    pe.setPer_apellido(apell.getText().toString());
                    pe.setPer_corr_elec(correo.getText().toString());
                    pe.setPer_celular(telef.getText().toString());

                    Usuario us = new Usuario();
                    us.setUsu_id(usuDB.maxUser(this));
                    us.setUsu_nomb(user.getText().toString());
                    us.setUsu_pass(pass.getText().toString());
                    us.setPer_id(pe.getPer_id());

                    Estudiante est = new Estudiante();
                    est.setEst_id(estDB.maxEst(this));
                    est.setUsu_id(us.getUsu_id());

                    if (!us.isNull()){
                        Toast.makeText(this,"Error:  campos bacios",Toast.LENGTH_SHORT).show();
                    }else if(perDB.insertPers(pe,this) && usuDB.insertUser(us,this) && estDB.insertEst(est,this)){
                        Toast.makeText(this,"Usuario ingresado",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(this,"Usuario no ingresado",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this,"Error:  las contraseñas no son inguales",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnRcanc:
                finish();
                break;
        }
    }
}
