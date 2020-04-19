package com.example.proyecto02.ui.gallery;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto02.MainActivity;
import com.example.proyecto02.R;
import com.example.proyecto02.adaptador.AdaptadorMateria;
import com.example.proyecto02.modeloDB.materiaDB;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private ListView listaMaterias;
    private materiaDB conecta;
    final int id_usu = MainActivity.id_usuario;

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
        if(conecta.listaMaterias(id_usu,getContext()) != null){
            listaMaterias.setAdapter(new AdaptadorMateria(getContext(),conecta.listaMaterias(id_usu,getContext())));
        }
        return root;
    }

}
