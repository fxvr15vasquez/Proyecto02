package com.example.proyecto02.ui.slideshow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto02.MainActivity;
import com.example.proyecto02.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    final private int REQUEST_CODE_ASK_PERMISSION=111;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        solicitarPermisoCamara();
        return root;
    }

    public void iniciaCamara(View view){
        MainActivity ini = new MainActivity();
        ini.Escanear((View.OnClickListener) this);
    }

    public void solicitarPermisoCamara(){
        int permisoCamara = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (permisoCamara!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.CAMERA},REQUEST_CODE_ASK_PERMISSION);
            }
        }
    }

}
