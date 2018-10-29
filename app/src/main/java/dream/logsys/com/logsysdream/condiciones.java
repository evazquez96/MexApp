package dream.logsys.com.logsysdream;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import logsys.dream.com.mx.activities.LoginActivity;

public class condiciones extends AppCompatActivity {
   public int condicion;
    Switch select;
    Button sig;
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = this;

        SharedPreferences sharedPrefe = getSharedPreferences("opcion",context.MODE_PRIVATE);

        setContentView(R.layout.activity_condiciones);
        sig=(Button)findViewById(R.id.segu);
        select=(Switch)findViewById(R.id.cambio);

        SharedPreferences sharedPrefer = getPreferences(context.MODE_PRIVATE);
        Boolean ne = sharedPrefer.getBoolean("opc",false);
        if(ne == true){
            Intent nuevo= new Intent(condiciones.this,LoginActivity.class);
            startActivity(nuevo);
        }

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean bandera;

                SharedPreferences sharedPrefer = getPreferences(context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefer.edit();

                if(select.isChecked()){
                    Intent nuevo= new Intent(condiciones.this,LoginActivity.class);
                    startActivity(nuevo);
                    bandera=true;
                    editor.putBoolean("opc",bandera);
                    editor.commit();

                }

                else {
                    Toast.makeText(condiciones.this,"No has aceptado los terminos y condiciones",Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}
