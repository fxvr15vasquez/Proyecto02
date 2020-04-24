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
import java.io.IOException;

public class EditarNotas extends AppCompatActivity implements View.OnClickListener{

    EditText fecha, descrip;
    ImageView not_img;
    Button guard, cancelar, imagen;
    int id;
    Bitmap bitmap;
    notasDB  ntDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_notas);
        ntDB= new notasDB();

        fecha = (EditText) findViewById(R.id.etxtENfech);
        descrip = (EditText) findViewById(R.id.etxtENdescrip);

        not_img = (ImageView) findViewById(R.id.imvENimag);

        guard = (Button) findViewById(R.id.btnENeditar);
        cancelar = (Button) findViewById(R.id.btnENelim);
        imagen= (Button) findViewById(R.id.btnENingeimg);

        guard.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        imagen.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("idNot");
        }

        mostrarDatos();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnENeditar:
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
                    Notas n = new Notas();
                    n.setNot_id(id);
                    n.setNot_descrip(descrip.getText().toString());
                    n.setNot_fech(fecha.getText().toString());
                    n.setNot_foto(foto);

                    if(ntDB.editNot(n,this)){
                        Toast.makeText(getApplicationContext(), "Nota Editada" , Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"ERROR al editar" ,Toast.LENGTH_LONG).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(),"ERROR al editar" ,Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnENelim:
                if (ntDB.elimNot(id,this)){
                    Toast.makeText(getApplicationContext(), "Nota eliminada" , Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR al eliminar", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnENingeimg:
                Intent intimg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intimg.setType("image/");
                startActivityForResult(intimg.createChooser(intimg,"Seleccione la Aplicación"),10);
                break;
        }
    }
    public void mostrarDatos(){
        Notas datos = new Notas();
        datos = ntDB.selecTarNotbyID(id,this);
        descrip.setText(datos.getNot_descrip());
        fecha.setText(datos.getNot_fech());
        bitmap = getImage(datos.getNot_foto());
        not_img.setImageBitmap(bitmap);

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
