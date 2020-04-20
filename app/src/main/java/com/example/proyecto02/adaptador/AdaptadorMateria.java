package com.example.proyecto02.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto02.modelo.Materia;
import com.example.proyecto02.R;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class AdaptadorMateria extends BaseAdapter {
    private LayoutInflater inflater = null;
    Context context;
    ArrayList<Materia> listaMateria = new ArrayList<Materia>();

    public AdaptadorMateria(Context context, ArrayList<Materia> listaMateria) {
        this.context = context;
        this.listaMateria = listaMateria;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
            return listaMateria.size();
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

        final  View vista = inflater.inflate(R.layout.listadematerias, null);
        TextView matNomb = (TextView) vista.findViewById(R.id.txtLMnomb);
        TextView matDesc = (TextView) vista.findViewById(R.id.txtLMdecip);
        TextView matProf = (TextView) vista.findViewById(R.id.txtLMProf);
        matNomb.setText(listaMateria.get(i).getMat_nombre());
        matDesc.setText(listaMateria.get(i).getMat_descrip());
        matProf.setText(listaMateria.get(i).getMat_profesor());
        return vista;

    }
}
