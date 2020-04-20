package com.example.proyecto02.adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyecto02.R;
import com.example.proyecto02.modelo.Tarea;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdaptadorTarea extends BaseAdapter {

    private LayoutInflater inflater = null;
    Context context;
    ArrayList<Tarea>listaTarea = new ArrayList<Tarea>();

    public AdaptadorTarea(Context context, ArrayList<Tarea> listaTarea) {
        this.context = context;
        this.listaTarea = listaTarea;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaTarea.size();
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
        final  View vista = inflater.inflate(R.layout.listadetareas, null);
        TextView tarID = (TextView) vista.findViewById(R.id.txtLTid);
        TextView tarFech = (TextView) vista.findViewById(R.id.txtLTfech);
        TextView tarDesc = (TextView) vista.findViewById(R.id.txtLTdesc);
        ImageView tarIMG = (ImageView) vista.findViewById(R.id.imvLTimg);


            tarID.setText(listaTarea.get(i).getTar_nombre()+"");
            tarFech.setText(listaTarea.get(i).getTar_fech_entrega());
            tarDesc.setText(listaTarea.get(i).getTar_descrip());
            tarIMG.setImageBitmap(getImage(listaTarea.get(i).getTar_foto()));

        return vista;

    }

    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
