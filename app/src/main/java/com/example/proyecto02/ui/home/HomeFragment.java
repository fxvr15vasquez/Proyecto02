package com.example.proyecto02.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto02.MainActivity;
import com.example.proyecto02.R;
import com.example.proyecto02.adaptador.AdaptadorTarea;
import com.example.proyecto02.modelo.Tarea;
import com.example.proyecto02.modeloDB.tareaDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listatareas;
    tareaDB tarDB;
    final int id_usu = MainActivity.id_usuario;
    ArrayList<Tarea> listTar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        listTar = new ArrayList<Tarea>();
        tarDB = new tareaDB();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);

        listatareas = (ListView) root.findViewById(R.id.lstVtarh);
        if(tarDB.selecTars(id_usu,getContext()) != null){
            listTar= tarDB.selecTarFech(id_usu,fecha,getContext());
            listatareas.setAdapter(new AdaptadorTarea(getContext(),listTar));
            listatareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }

        return root;
    }
}
