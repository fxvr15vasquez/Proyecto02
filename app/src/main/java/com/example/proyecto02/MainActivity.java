package com.example.proyecto02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto02.modeloDB.Conexion;
import com.example.proyecto02.modeloDB.usuarioDB;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ZXingScannerView.ResultHandler{

    EditText lguser, lgpass;
    Button btningre, btnregis;
    private ZXingScannerView mScanner;

    Conexion con;
    usuarioDB usuDB;
    public static int id_usuario;
    private View v;
    public Button btnIngCamara;

    ArrayList<String> datos;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //con = new Conexion(this);
        usuDB = new usuarioDB();

        btningre= (Button) findViewById(R.id.btningre);
        btnregis= (Button) findViewById(R.id.btnregis);

        lguser=(EditText) findViewById(R.id.etxtluser);
        lgpass=(EditText) findViewById(R.id.etxtlpass);

        btningre.setOnClickListener(this);
        btnregis.setOnClickListener(this);

        btnIngCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Escanear(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        String user = lguser.getText().toString();
        String pass = lgpass.getText().toString();

        switch (v.getId()){
            case R.id.btningre:
                int id_usu = usuDB.login(user, pass,this);
                if (user.equals("") && pass.equals("")){
                    Toast.makeText(this,"Campos vacios",Toast.LENGTH_SHORT).show();
                }else if(id_usu !=0){
                    Intent inlogin = new Intent(MainActivity.this,InicioActivity.class);
                    datos = usuDB.selecUsersbyID(id_usu,this);
                    if (datos.size()!=0){
                        id_usuario =id_usu;
                        inlogin.putExtra("user",datos.get(0));
                        inlogin.putExtra("correo",datos.get(2));
                    }
                    startActivity(inlogin);
                }else{
                    Toast.makeText(this,"Usuario o contraseña incorrectas",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnregis:
                Intent inregis = new Intent(MainActivity.this,Registro.class);
                startActivity(inregis);
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public void Escanear(View.OnClickListener v){
        mScanner = new ZXingScannerView(this);
        setContentView(mScanner);
        mScanner.setResultHandler(this);
        mScanner.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.v("HandleResult",result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado del Escaner: Desea ir a la página Web");
        //.setCancelable(false); //true para tocar fuera del dialog y desaparecer

        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        mScanner.resumeCameraPreview(this);
    }
}
