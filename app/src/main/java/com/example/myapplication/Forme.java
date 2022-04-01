package com.example.myapplication;

import android.graphics.Paint;
import android.graphics.Path;

public class Forme extends Path {

    private int couleur;
    private int epaisseur;
    Paint crayon;

    public Forme(int couleur, int epaisseur) {
        this.couleur = couleur;
        this.epaisseur = epaisseur;

        crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
        crayon.setColor(couleur);
        crayon.setStrokeWidth(epaisseur);
        crayon.setStyle(Paint.Style.STROKE);
    }

    public Paint getCrayon() {
        return crayon;
    }
}
