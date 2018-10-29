package dream.logsys.com.logsysdream;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class grafica extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

      generar();




    }
    public void generar(){

        TableLayout lista=(TableLayout)findViewById(R.id.tablas);
        String[]cadena={"13:00","14:00","15:00","16:00","13:00","14:00","15:00","16:00","13:00","14:00","15:00","16:00"};
        String[]validar={"inactivo","activo","inactivo","activo","inactivo","activo","inactivo","activo","inactivo","activo","inactivo",""};
        TableRow row = new TableRow(getBaseContext());
        TableRow row1=new TableRow(getBaseContext());
        TextView textView1;
        TextView textView;
        for(int i=0;i<cadena.length;i++){
            textView=new TextView(getBaseContext());
            textView1=new TextView(getBaseContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15,15,15,15);
            textView.setBackgroundResource(R.color.azul);
            textView.setTextColor(Color.WHITE);

            textView1.setGravity(Gravity.CENTER_VERTICAL);
            textView1.setPadding(15,15,15,15);

            textView1.setBackgroundResource(R.color.amarillo);
            textView1.setTextColor(Color.WHITE);

            textView.setText(cadena[i]);
            textView1.setText(validar[i]);
            row.addView(textView);
            row1.addView(textView1);

        }
        lista.addView(row);
        lista.addView(row1);


    }
}
