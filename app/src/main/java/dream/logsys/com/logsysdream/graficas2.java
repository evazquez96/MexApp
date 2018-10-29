package dream.logsys.com.logsysdream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.ZoomControls;

import org.achartengine.tools.ZoomEvent;

import java.util.ArrayList;
import java.util.LinkedList;

import logsys.dream.com.mx.models.*;

public class graficas2 extends AppCompatActivity {

    EjemploView ejemploView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ejemploView = new EjemploView(this);
        ejemploView.setBackgroundColor(Color.WHITE);
        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        scrollView.addView(ejemploView);
        setContentView(scrollView);

    }

    public class EjemploView extends View {

        int anchotab = 85;
        int textot = 85;
        float x;
        float y;
        float actvos = 100;
        int suma1;
        int contador;

        public EjemploView (Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = 3200;
            int height = 1440; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
            setMeasuredDimension(width, height);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onDraw(Canvas canvas) {
            //Dibujar aquí

            //Aqui estan las variables de la clase bitacora2 las cuales se utilizaran para la grafica
            int[] evento = getIntent().getIntArrayExtra("numeros");
            String[] horas = getIntent().getStringArrayExtra("hor");
            int[] rang = getIntent().getIntArrayExtra("rango");

            //1 = activo
            //0 = inactivo

            Paint pincel = new Paint();
            Paint texto = new Paint();
            textot =200;

            texto.setColor(Color.BLACK);
            texto.setTextAlign(Paint.Align.CENTER);
            texto.setTextSize(32);
            canvas.drawText("Descanso:", actvos, 260, texto);

            texto.setColor(Color.BLACK);
            texto.setTextAlign(Paint.Align.CENTER);
            texto.setTextSize(32);
            canvas.drawText("Activo:", actvos, 160, texto);

            anchotab = 200;
            int textot2=0;

            for (int i = 0; i < horas.length; i++) {

                texto.setColor(Color.BLACK);
                texto.setTextAlign(Paint.Align.CENTER);
                texto.setTextSize(18);
                canvas.rotate(90,textot,0);
                if (i == horas.length-1) {
                    canvas.drawText(horas[i], textot + 50, 0, texto);//aqui se muevan las horas del canvas.
                } else {
                    if (i % 2 == 0) {
                        canvas.drawText(horas[i], textot + 50, 0, texto);//aqui se muevan las horas del canvas.
                    } else {
                        canvas.drawText(horas[i], textot + 350, 0, texto);//aqui se muevan las horas del canvas.
                    }
                }
                //canvas.drawText(horas[i]+"____________________", textot +160, 0, texto);
                canvas.rotate(-90,textot,0);
                if (i < horas.length-1) {
                    textot = textot + (rang[i] * 2);
                    textot2 = textot2 + (rang[i] * 2);
                }

            }

                for (int i = 0; i < evento.length; i++) {

                    if (evento[i] == 1) {

                    pincel.setColor(Color.BLUE);
                    pincel.setStrokeWidth(1);
                    pincel.setStyle(Paint.Style.FILL_AND_STROKE);

                    canvas.drawRect(anchotab, 110, anchotab + (rang[i]*2), 200, pincel);
                    anchotab = anchotab + (rang[i]*2);

                } else {

                    pincel.setColor(Color.GRAY);
                    pincel.setStrokeWidth(1);
                    pincel.setStyle(Paint.Style.FILL_AND_STROKE);

                    canvas.drawRect(anchotab, 210, anchotab + (rang[i]*2), 300, pincel);
                    anchotab = anchotab + (rang[i]*2);

                }

            }

            int a = canvas.getWidth();

            System.out.println("Aqui esta el dato de la anchura: " + a);
            System.out.println("Esta es anchotab: " + anchotab);


        }

    }

}