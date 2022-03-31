package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
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
    Vector<Path> forme;
    String formeCourante = "";
    Trace trace;
    Button couleurTempButton;
    ColorDrawable couleurTemp;
    int couleurCourante;
    int x = 0;
    int y = 0;
    int epaisseur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutDessin = findViewById(R.id.layoutDessin);
        layoutCouleurs = findViewById(R.id.layoutCouleurs);
        layoutButtons = findViewById(R.id.layoutButtons);

        surf = new SurfaceDessin(this);
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


            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                if (formeCourante.equals("crayon")) {
                    x = (int)event.getX();
                    y = (int)event.getY();
                    trace = new Trace(couleurCourante, epaisseur, formeCourante, x, y);
                    trace.moveTo(x, y);
                    forme.add(trace);
                }
                }


//            else if (event.getAction() == MotionEvent.ACTION_MOVE)
//                path.lineTo(x, y);

            surf.invalidate();
            return true;
        }

        @Override
        public void onClick(View source) {

            if (source instanceof ImageView) {
                formeCourante = (String) source.getTag();




            }

            else if (source instanceof Button) {
                couleurTempButton = (Button)source;
                couleurTemp = (ColorDrawable)couleurTempButton.getBackground();
                couleurCourante = couleurTemp.getColor();
            }
        }
    }

    private class SurfaceDessin extends View {

        Paint crayon;

        public SurfaceDessin(Context context) {
            super(context);
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon.setColor(Color.RED);
            crayon.setStrokeWidth(10);
            crayon.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!forme.isEmpty()) {
                for (int i = 0; i < forme.size(); i++) {
                    canvas.drawPath(forme.get(i), crayon);
                }
            }
        }
    }
}