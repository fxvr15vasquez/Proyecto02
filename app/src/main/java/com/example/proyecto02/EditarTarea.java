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

import com.example.proyecto02.modelo.Materia;
import com.example.proyecto02.modelo.Tarea;
import com.example.proyecto02.modeloDB.materiaDB;
import com.example.proyecto02.modeloDB.tareaDB;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditarTarea extends AppCompatActivity implements View.OnClickListener{

    EditText ident, fech, descrip;
    Button editar, elimn, cargarimg;
    ImageView imgtar;
    tareaDB taDB;
    int idTAR;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);
        taDB = new tareaDB();

        ident = (EditText) findViewById(R.id.etxtETident);
        fech = (EditText) findViewById(R.id.etxtETfech);
        descrip = (EditText) findViewById(R.id.etxtETdescrip);
        imgtar = (ImageView) findViewById(R.id.imvETimg);

        editar = (Button) findViewById(R.id.btnETedit);
        elimn = (Button) findViewById(R.id.btnETelimn);
        cargarimg = (Button) findViewById(R.id.btnETimagen);
        editar.setOnClickListener(this);
        elimn.setOnClickListener(this);
        cargarimg.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idTAR = extras.getInt("idTar");
        }

        mostrarDatos();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnETedit:
                boolean ingres;
                byte[] foto = null;
                try {
                    foto = imgtrans(bitmap);
                    ingres = true;
                }catch (Exception e){
                    System.out.println("error al transformar foto: "+e.getMessage());
                    ingres = false;
                }
                if (ingres){
                    Tarea ingresar = new Tarea();
                    ingresar.setTar_id(idTAR);
                    ingresar.setTar_nombre(ident.getText().toString());
                    ingresar.setTar_descrip(descrip.getText().toString());
                    ingresar.setTar_fech_entrega(fech.getText().toString());
                    ingresar.setTar_foto(foto);

                    if(taDB.editTars(ingresar,this)){
                        Toast.makeText(getApplicationContext(), "Tarea Editada" , Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"ERROR al editar" ,Toast.LENGTH_LONG).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(),"ERROR al editar" ,Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnETelimn:
                    if (taDB.elimTars(idTAR,this)){
                        Toast.makeText(getApplicationContext(), "Tarea eliminada" , Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR al eliminar", Toast.LENGTH_LONG).show();
                    }

                break;
            case R.id.btnETimagen:
                Intent intimg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intimg.setType("image/");
                startActivityForResult(intimg.createChooser(intimg,"Seleccione la Aplicación"),10);
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
                imgtar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void mostrarDatos(){
        Tarea datos = new Tarea();
        datos = taDB.selecTarMatBYID(idTAR,this);
        ident.setText(datos.getTar_nombre());
        descrip.setText(datos.getTar_descrip());
        fech.setText(datos.getTar_fech_entrega());
        bitmap = getImage(datos.getTar_foto());
        imgtar.setImageBitmap(bitmap);

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
