package com.example.proyecto02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class IngresoTarea extends AppCompatActivity implements View.OnClickListener{

    TextView txtiden, txtfeh, txtDescrip;
    ImageView imgtarea;
    Button btningre_img, btnTarg, btnTarc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_tarea);

        txtiden = (TextView) findViewById(R.id.etxtTnomb);
        txtfeh = (TextView) findViewById(R.id.etxtFecha);
        txtDescrip = (TextView) findViewById(R.id.etxtdescri);
        imgtarea = (ImageView) findViewById(R.id.imvtarea);
        btningre_img = (Button) findViewById(R.id.btnTingreImg);
        btnTarg= (Button) findViewById(R.id.btnTguar);
        btnTarc= (Button) findViewById(R.id.btnTcanc);

        btningre_img.setOnClickListener(this);
        btnTarc.setOnClickListener(this);
        btnTarg.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTingreImg:
                Intent intimg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intimg.setType("image/");
                startActivityForResult(intimg.createChooser(intimg,"Seleccione la Aplicación"),10);
                break;
            case R.id.btnTguar:
                
                finish();
                break;
            case R.id.btnTcanc:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path = data.getData();
            imgtarea.setImageURI(path);
        }
    }
}
