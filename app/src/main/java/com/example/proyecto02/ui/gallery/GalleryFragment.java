package com.example.proyecto02.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto02.EditarMateria;
import com.example.proyecto02.IngresoMateria;
import com.example.proyecto02.MainActivity;
import com.example.proyecto02.MateriaActivity;
import com.example.proyecto02.R;
import com.example.proyecto02.adaptador.AdaptadorMateria;
import com.example.proyecto02.modelo.Materia;
import com.example.proyecto02.modeloDB.materiaDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private ListView listaMaterias;
    private materiaDB conecta;
    final int id_usu = MainActivity.id_usuario;
    ArrayList<Materia> listMat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        conecta = new materiaDB();
        listaMaterias = (ListView) root.findViewById(R.id.listMaterias);
        listMat = new ArrayList<Materia>();
        if(conecta.listaMaterias(id_usu,getContext()) != null){
            listMat = conecta.listaMaterias(id_usu,getContext());
            listaMaterias.setAdapter(new AdaptadorMateria(getContext(),listMat));
            listaMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent inttarea  = new Intent(getContext(), MateriaActivity.class);
                    inttarea.putExtra("mat_id",listMat.get(position).getMat_id());
                    inttarea.putExtra("mat_nomb",listMat.get(position).getMat_nombre());
                    startActivity(inttarea);
                }
            });
            listaMaterias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent editmater = new Intent(getContext(), EditarMateria.class);
                    editmater.putExtra("matID", listMat.get(position).getMat_id());
                    startActivity(editmater);
                    return false;
                }
            });
        }
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Accion del boton", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent inlmateria = new Intent(getContext(), IngresoMateria.class);
                inlmateria.putExtra("id", id_usu);
                startActivity(inlmateria);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        if(conecta.listaMaterias(id_usu,getContext()) != null){
            listMat = conecta.listaMaterias(id_usu,getContext());
            listaMaterias.setAdapter(new AdaptadorMateria(getContext(),listMat));
        }
        super.onResume();
    }

}
