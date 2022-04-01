package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.SeekBar;

public class Epaisseur extends Dialog {

    MainActivity parent;
    SeekBar epaisseur;

    public Epaisseur(@NonNull Context context) {
        super(context);
        this.parent = (MainActivity)context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epaisseur);

        epaisseur = findViewById(R.id.epaisseurTrait);
    }
}