package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.media.effect.EffectFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    LinearLayout layoutDessin, layoutCouleurs, layoutButtons;
    SurfaceDessin surf;
    Vector<Forme> forme;
    String formeCourante = "trace";
    Trace trace;
    Efface efface;
    Button couleurTempButton;
    int couleurTemp;
    int couleurCourante = -1243642;
    int couleurBackground = -1;
    int x = 0;
    int y = 0;
    int epaisseur = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutDessin = findViewById(R.id.layoutDessin);
        layoutCouleurs = findViewById(R.id.layoutCouleurs);
        layoutButtons = findViewById(R.id.layoutButtons);

        surf = new SurfaceDessin(this);
        surf.setBackgroundColor(-1);
        Ecouteur ec = new Ecouteur();
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1));
        surf.setOnTouchListener(ec);

        for(int i = 0; i < layoutCouleurs.getChildCount(); i++) {
            if (layoutCouleurs.getChildAt(i) instanceof Button)
                layoutCouleurs.getChildAt(i).setOnClickListener(ec);
        }
        for(int i = 0; i < layoutButtons.getChildCount(); i++) {
            if (layoutButtons.getChildAt(i) instanceof ImageView)
                layoutButtons.getChildAt(i).setOnClickListener(ec);
        }

        forme = new Vector();
        layoutDessin.addView(surf);
    }

    private class Ecouteur implements View.OnTouchListener, View.OnClickListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            x = (int)event.getX();
            y = (int)event.getY();

            //ACTION DOWN
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (formeCourante.equals("trace")) {
                    trace = new Trace(couleurCourante, epaisseur);
                    trace.moveTo(x, y);
                }
                else if (formeCourante.equals("efface")) {
                    couleurTemp = couleurCourante;
                    couleurCourante = couleurBackground;
                    efface = new Efface(couleurCourante, epaisseur);
                    efface.moveTo(x, y);
                }
            }

            //ACTION MOVE
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (formeCourante.equals("trace"))
                    trace.lineTo(x, y);
                else if (formeCourante.equals("efface")) {
                    efface.lineTo(x, y);
                }
            }

            //ACTION UP
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (formeCourante.equals("trace")) {
                    forme.add(trace);
                    trace = null;
                }
                if (formeCourante.equals("efface")) {
                    forme.add(efface);
                    couleurCourante = couleurTemp;
                    efface = null;
                }
            }

            surf.invalidate();
            return true;
        }

        @Override
        public void onClick(View source) {

            if (source instanceof ImageView) {
                formeCourante = (String)source.getTag();
            }

            else if (source instanceof Button) {
                couleurTempButton = (Button)source;
                couleurCourante = ((ColorDrawable)couleurTempButton.getBackground()).getColor();
            }
        }
    }

    private class SurfaceDessin extends View {

        Paint crayonCourant, crayon;


        public SurfaceDessin(Context context) {
            super(context);
            crayonCourant = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayonCourant.setColor(couleurCourante);
            crayonCourant.setStrokeWidth(epaisseur);
            crayonCourant.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            crayonCourant.setColor(couleurCourante);

            if (!forme.isEmpty()) {
                for (int i = 0; i < forme.size(); i++) {
                    crayon = forme.get(i).getCrayon();
                    canvas.drawPath(forme.get(i), crayon);
                }
            }

            if (formeCourante.equals("trace"))
                if (trace != null)
                    canvas.drawPath(trace, crayonCourant);

            if (formeCourante.equals("efface"))
                if (efface != null)
                    canvas.drawPath(efface, crayonCourant);


        }
    }
}