package com.example.proyecto02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyecto02.modelo.Materia;
import com.example.proyecto02.modeloDB.materiaDB;

public class IngresoMateria extends AppCompatActivity implements View.OnClickListener{
    private EditText txtNomMat;
    private EditText txtDes;
    private Spinner spnNiv;
    private EditText txtNomProf;
    private Button btnGuardar;
    private materiaDB matDb;
    private SimpleCursorAdapter cursorAdapter;
    materiaDB materiadb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_materia);

        //Casting de los componenetes pru
        txtNomMat = (EditText) findViewById(R.id.etxtMnomb);
        txtDes = (EditText) findViewById(R.id.edtTxtDes);
        spnNiv = (Spinner) findViewById(R.id.spnNiv);
        txtNomProf = (EditText) findViewById(R.id.txtNomProf);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        spnNiv = (Spinner) findViewById(R.id.spnNiv);
        materiadb = new materiaDB();
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nivel,android.R.layout.simple_spinner_item);
        //spnNiv.setAdapter(adapter);


        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar:
                if (!txtNomMat.getText().toString().isEmpty() && !txtDes.getText().toString().isEmpty() && !txtNomProf.getText().toString().isEmpty()) {

                    Materia materia = new Materia(txtNomMat.getText().toString(), txtDes.getText().toString(), spnNiv.getSelectedItem().toString(), txtNomProf.getText().toString());
                    String sentencia = materiadb.insertaMateria(materia,this);
                    if (sentencia == null) {
                        Toast.makeText(getApplicationContext(), txtNomMat.getText().toString() + "des" + txtDes.getText().toString() + "" + spnNiv.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                        txtNomMat.setText("");
                        txtDes.setText("");
                        txtNomProf.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR " + sentencia, Toast.LENGTH_LONG).show();
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
