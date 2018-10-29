package logsys.dream.com.mx.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.db.ViajesDB;

public class ConsultaViajeActivity extends AppCompatActivity {

    public ImageView img_navegar;
    public TextView tvsolicitud;
    public TextView tvcliente;
    public TextView tvcitacarga;
    public TextView tvcitadescarga;
    public TextView tvcp;
    public TextView tvheader;
    public TextView tvdestino;
    public TextView tvdc;
    public TextView tvdd;
    public TextView tvorigen;
    public TextView tvshipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_viaje);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int viajeId = extras.getInt("id");
            Log.d(":::::::::::::::::","Viaje id: " + viajeId + "");
            ViajesDB db = new ViajesDB(LoginActivity.getAppContext());
            viajeTO viaje = db.obtenerViaje(viajeId);
            Log.d("::::::::::::::::::::",viaje.toString());
            this.setTitle("Viaje");


            img_navegar = findViewById(R.id.img_navegar);
            tvsolicitud = findViewById(R.id.tvheader1);
            tvcitacarga = findViewById(R.id.tvcitacarga1);
            tvcitadescarga = findViewById(R.id.tvcitadescarga1);
            tvcp = findViewById(R.id.tvcp1);
            tvheader = findViewById(R.id.tvheader);
            tvdestino = findViewById(R.id.tvorigentmp2);
            tvdc = findViewById(R.id.tvdc1);
            tvdd = findViewById(R.id.tvdd1);
            tvorigen = findViewById(R.id.tvorigentmp1);
            tvshipment = findViewById(R.id.tvshipment1);
            tvcliente = findViewById(R.id.tvcliente1);


            tvsolicitud.setText("Solicitud: "+ viaje.getNumeroSolicitud());
            tvcitacarga.setText(viaje.getCitaCarga());
            tvcitadescarga.setText(viaje.getCitaDescarga());
            tvcp.setText(viaje.getCp());
            tvheader.setText(viaje.getOrigen() + " - " + viaje.getDestino());
            tvdestino.setText(viaje.getDestino());
            tvdc.setText(viaje.getDireccionCarga());
            tvdd.setText(viaje.getDireccionDescarga());
            tvorigen.setText(viaje.getOrigen());
            tvshipment.setText(viaje.getShipment());
            tvcliente.setText(viaje.getCliente());

        }
    }
}
