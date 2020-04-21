package com.example.proyecto02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto02.modelo.Materia;
import com.example.proyecto02.modeloDB.materiaDB;
import com.example.proyecto02.modeloDB.tareaDB;

import java.sql.DataTruncation;
import java.util.ArrayList;

public class EditarMateria extends AppCompatActivity implements View.OnClickListener{

    EditText nombre, descrip, nivel, profs;
    Button editar, elimn;
    materiaDB mtDB;
    tareaDB taDB;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_materia);

        mtDB = new materiaDB();
        taDB = new tareaDB();
        nombre = (EditText) findViewById(R.id.etxtEMnomb);
        descrip = (EditText) findViewById(R.id.etxtEMdescrip);
        nivel = (EditText) findViewById(R.id.etxtEMnivel);
        profs = (EditText) findViewById(R.id.etxtEMprofs);

        editar = (Button) findViewById(R.id.btnEMeditar);
        elimn = (Button) findViewById(R.id.btnEMeliminar);

        editar.setOnClickListener(this);
        elimn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("matID");
        }

        mostrarDatos();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEMeditar:

                                Materia ingresar = new Materia();
                                ingresar.setMat_id(id);
                                ingresar.setMat_nombre(nombre.getText().toString());
                                ingresar.setMat_descrip(descrip.getText().toString());
                                ingresar.setMat_nivel(nivel.getText().toString());
                                ingresar.setMat_profesor(profs.getText().toString());

                                if(mtDB.editMateria(ingresar,this)){
                                    Toast.makeText(getApplicationContext(), "Materia Editada" , Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"ERROR al editar" ,Toast.LENGTH_LONG).show();
                                }

                break;
            case R.id.btnEMeliminar:

                                if(mtDB.elimMatr(id,this)){
                                    if (taDB.elimTarsBYMAT(id,this)){
                                        Toast.makeText(getApplicationContext(), "Materia eliminada" , Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"ERROR al eliminar" ,Toast.LENGTH_LONG).show();
                                }

                break;
            case R.id.btnSca:

                break;
        }
    }

    public void mostrarDatos(){
        Materia datos = new Materia();
        datos = mtDB.materiasBYID(id,this);
        nombre.setText(datos.getMat_nombre());
        descrip.setText(datos.getMat_descrip());
        nivel.setText(datos.getMat_nivel());
        profs.setText(datos.getMat_profesor());
    }
}
