package com.example.proyecto02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyecto02.modelo.Materia;
import com.example.proyecto02.modeloDB.estudianteDB;
import com.example.proyecto02.modeloDB.materiaDB;

import java.util.ArrayList;

public class IngresoMateria extends AppCompatActivity implements View.OnClickListener{
    private EditText txtNomMat;
    private EditText txtDes;
    private Spinner spnNiv;
    private EditText txtNomProf;
    private Button btnGuardar;
    private SimpleCursorAdapter cursorAdapter;
    int user_id;
    materiaDB materiadb;
    estudianteDB estDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_materia);

        //Casting de los componenetes pru
        txtNomMat = (EditText) findViewById(R.id.etxtMnomb);
        txtDes = (EditText) findViewById(R.id.edtTxtDes);
        spnNiv = (Spinner) findViewById(R.id.spnNiv);
        txtNomProf = (EditText) findViewById(R.id.txtNomProf);
        btnGuardar = (Button) findViewById(R.id.btnMGuardar);
        spnNiv = (Spinner) findViewById(R.id.spnNiv);
        materiadb = new materiaDB();
        estDB = new estudianteDB();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.niveles,android.R.layout.simple_spinner_item);
        spnNiv.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            user_id =extras.getInt("id");
        }


        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMGuardar:
                if (!txtNomMat.getText().toString().isEmpty() && !txtDes.getText().toString().isEmpty() && !txtNomProf.getText().toString().isEmpty()) {
                    Materia materia = new Materia(materiadb.maxUser(this),txtNomMat.getText().toString(), spnNiv.getSelectedItem().toString(), txtDes.getText().toString(), txtNomProf.getText().toString(),estDB.selecEst(user_id,this));
                    if (materiadb.insertaMateria(materia,this)) {
                        Toast.makeText(this, "MateriaActivity ingresada", Toast.LENGTH_LONG).show();
                        txtNomMat.setText("");
                        txtDes.setText("");
                        txtNomProf.setText("");
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR al ingresar materia" , Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Debe llenar todos los datos", Toast.LENGTH_LONG).show();
                    //finish();
                }

                break;
            case R.id.btnregis:
                //Intent inregis = new Intent(MainActivity.this,Registro.class);
                //startActivity(inregis);
                break;
        }
    }
}
