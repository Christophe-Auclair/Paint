package com.example.myapplication;

import android.graphics.Path;

public class Forme extends Path {
    private int x;
    private int y;
    private int couleur;
    private int epaisseur;
    private String sorte;

    public Forme(int couleur, int epaisseur, String sorte, int x, int y) {
        this.couleur = couleur;
        this.epaisseur = epaisseur;
        this.sorte = sorte;
        this.x = x;
        this.y = y;
    }
}
