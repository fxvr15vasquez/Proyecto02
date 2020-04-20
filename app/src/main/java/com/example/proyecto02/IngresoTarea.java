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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto02.modelo.Tarea;
import com.example.proyecto02.modeloDB.tareaDB;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class IngresoTarea extends AppCompatActivity implements View.OnClickListener{

    TextView txtiden, txtfeh, txtDescrip;
    ImageView imgtarea;
    Button btningre_img, btnTarg, btnTarc;
    tareaDB tarDB;
    int id_mat;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_tarea);

        tarDB = new tareaDB();
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_mat = extras.getInt("idM");
        }
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTingreImg:
                Intent intimg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intimg.setType("image/");
                startActivityForResult(intimg.createChooser(intimg,"Seleccione la Aplicación"),10);
                break;
            case R.id.btnTguar:
                boolean ingresa;
                byte[] foto = null;
                try {
                     foto = imgtrans(bitmap);
                    ingresa = true;
                }catch (Exception e){
                    System.out.println("error al transformar foto: "+e.getMessage());
                    ingresa = false;
                }
                if (txtiden.getText().toString().equals("") || txtDescrip.getText().toString().equals("") || txtfeh.getText().toString().equals("") || !ingresa){
                    Toast.makeText(this,"Error:  campos no ingreados",Toast.LENGTH_SHORT).show();
                }else{
                    Tarea ta =new Tarea();
                    ta.setTar_id(tarDB.maxTars(this));
                    ta.setTar_nombre(txtiden.getText().toString());
                    ta.setTar_descrip(txtDescrip.getText().toString());
                    ta.setTar_fech_entrega(txtfeh.getText().toString());
                    ta.setTar_foto(foto);
                    ta.setMat_id(id_mat);
                    if (ta.isNull()){
                        if (tarDB.insertTars(ta,this)){
                            Toast.makeText(this,"Datos Ingresados",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(this,"Error al ingresar",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this,"Error:  campos bacios",Toast.LENGTH_SHORT).show();
                    }
                }
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
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                imgtarea.setImageBitmap(bitmap);
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
