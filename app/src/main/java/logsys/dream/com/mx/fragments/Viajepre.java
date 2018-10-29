package logsys.dream.com.mx.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.models.ViajesPre;

/**
 * A simple {@link Fragment} subclass.
 */
public class Viajepre extends Fragment {
TextView header;
    TextView shipment;
    TextView cp;
    TextView Cliente;
    TextView origen;
    TextView dorig;
    TextView solicitud;
    TextView citacarga;
    TextView dest;
    TextView dd1;
    TextView citadescarga;

    private static MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_viajepre, container, false);
        header=(TextView)view.findViewById(R.id.tvheader);
        solicitud=(TextView)view.findViewById(R.id.tvheader1);
        shipment=(TextView)view.findViewById(R.id.tvshipment1);
        cp=(TextView)view.findViewById(R.id.tvcp1);
        Cliente=(TextView)view.findViewById(R.id.tvcliente1);
        origen =(TextView)view.findViewById(R.id.tvorigentmp1);
        dorig=(TextView)view.findViewById(R.id.tvdc1);
        citacarga=(TextView)view.findViewById(R.id.tvcitacarga1);
        dest=(TextView)view.findViewById(R.id.tvorigentmp2);
        dd1=(TextView)view.findViewById(R.id.tvdd1);
        citadescarga=(TextView)view.findViewById(R.id.tvcitadescarga1);

//getviajepre();

        return view;

    }
    public void getviajepre(){

        ViajesPre viaje=new ViajesPre();
        int solicitudes= viaje.getSolicitud();
        int ship= viaje.getShipment();
        String cp1=viaje.getCp();
        String cliente=viaje.getCliente();
        String origens=viaje.getOrigen();
        String dirorige=viaje.getDireccionOrigen();
        String citac=viaje.getCitaCarga();
        String destino=viaje.getDestino();
        String dirdestino=viaje.getDireccionDestino();
        String citad=viaje.getCitaDescarga();

        header.setText(origens+"/n"+destino);
        solicitud.setText("Solicitud:  "+solicitudes);
        shipment.setText(ship);
        cp.setText(cp1);
        Cliente.setText(cliente);
        origen.setText(origens);
        dorig.setText(dirorige);
        citacarga.setText(citac);
        dest.setText(destino);
        dd1.setText(dirdestino);
        citadescarga.setText(citad);

    }

}
