package com.example.proyecto02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyecto02.modelo.Notas;
import com.example.proyecto02.modelo.Tarea;
import com.example.proyecto02.modeloDB.notasDB;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Ingreso_Notas extends AppCompatActivity implements View.OnClickListener{

    EditText fecha, descrip;
    ImageView not_img;
    Button guard, cancelar, imagen;
    int id_mat;
    Bitmap bitmap;

    notasDB  ntDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso__notas);

        ntDB= new notasDB();

        fecha = (EditText) findViewById(R.id.etxtINfech);
        descrip = (EditText) findViewById(R.id.etxtINdescrip);

        not_img = (ImageView) findViewById(R.id.imvINimagen);

        guard = (Button) findViewById(R.id.btnINguard);
        cancelar = (Button) findViewById(R.id.btnINcanc);
        imagen= (Button) findViewById(R.id.btnINingreimg);

        guard.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        imagen.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_mat = extras.getInt("idN");
        }

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnINingreimg:
                Intent intimg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intimg.setType("image/");
                startActivityForResult(intimg.createChooser(intimg,"Seleccione la Aplicación"),10);
                break;
            case R.id.btnINguard:
                boolean ingresa;
                byte[] foto = null;
                try {
                    foto = imgtrans(bitmap);
                    ingresa = true;
                }catch (Exception e){
                    System.out.println("error al transformar foto: "+e.getMessage());
                    ingresa = false;
                }
                if (fecha.getText().toString().equals("") || descrip.getText().toString().equals("")){
                    Toast.makeText(this,"Error:  campos no ingreados",Toast.LENGTH_SHORT).show();
                }else{
                    Notas n = new Notas();
                    n.setNot_id(ntDB.maxNot(this));
                    n.setNot_descrip(descrip.getText().toString());
                    n.setNot_fech(fecha.getText().toString());
                    n.setNot_foto(foto);
                    n.setMat_id(id_mat);
                        if (ntDB.insertNota(n,this)){
                            Toast.makeText(this,"Datos Ingresados",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(this,"Error al ingresar",Toast.LENGTH_SHORT).show();
                        }
                }
                break;
            case R.id.btnINcanc:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                not_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public byte[] imgtrans(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
