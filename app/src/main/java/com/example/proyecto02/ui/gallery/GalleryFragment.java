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
import com.example.proyecto02.modeloDB.materiaDB;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private ListView listaMaterias;
    private SimpleCursorAdapter cursorAdapter;
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

        muestraMateria(root);
        return root;
    }
    @Override
    public void onResume() {
        cursorAdapter.swapCursor(conecta.listaMaterias(id_usu,getContext()));
        listaMaterias.setAdapter(cursorAdapter);
        super.onResume();
    }

    private void muestraMateria(final View root){
        final Cursor cursorp = conecta.listaMaterias(id_usu,getContext());
        String[] desde = new String[]{"mat_id","mat_nombre","mat_descrip"};
        int[] hasta=new int[]{R.id.txtLMid,R.id.txtLMnomb,R.id.txtLMdescrip};
        cursorAdapter = new SimpleCursorAdapter(getContext(),
                R.layout.listadematerias ,cursorp,desde,hasta,0);
        listaMaterias = (ListView)root.findViewById(R.id.listMaterias);
        listaMaterias.setAdapter(cursorAdapter);
        //Implentacion de long click en lista
        listaMaterias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                Cursor itemp = (Cursor) listaMaterias.getItemAtPosition(position);
                String Ruc = itemp.getString(1);

                return false;
            }

        });
    }
}
