package com.example.proyecto02.adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto02.R;
import com.example.proyecto02.modelo.Notas;
import com.example.proyecto02.modelo.Tarea;

import java.util.ArrayList;

public class AdaptadorNotas  extends BaseAdapter {

    private LayoutInflater inflater = null;
    Context context;
    ArrayList<Notas> lista = new ArrayList<Notas>();

    public AdaptadorNotas(Context context, ArrayList<Notas> lista) {
        this.context = context;
        this.lista =  lista;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final  View vista = inflater.inflate(R.layout.listadenotas, null);
        TextView descrip = (TextView) vista.findViewById(R.id.txtNotdescrip);
        TextView fecha = (TextView) vista.findViewById(R.id.txtNotfech);
        ImageView imag = (ImageView) vista.findViewById(R.id.imvNotimg);


        fecha.setText(lista.get(i).getNot_fech());
        descrip.setText(lista.get(i).getNot_descrip());
        imag.setImageBitmap(getImage(lista.get(i).getNot_foto()));

        return vista;

    }

    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
