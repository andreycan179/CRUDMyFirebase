package com.aplicaciones.destroyer.myfirebase;


import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aplicaciones.destroyer.myfirebase.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private User usuario;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        usuario = new User(this);
        binding.setUsuario(usuario);

    }



}
