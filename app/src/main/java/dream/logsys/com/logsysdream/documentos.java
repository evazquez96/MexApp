package dream.logsys.com.logsysdream;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import dream.logsys.com.logsysdream.wsBitaciora.Wservice;
import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.contracts.Usuario;
import logsys.dream.com.mx.models.Bitacora;
import logsys.dream.com.mx.models.Dream;
import logsys.dream.com.mx.models.ServiceData;
import logsys.dream.com.mx.startup.FrescoApplication;
import utils.GsonHelper;


public class documentos extends Fragment {

    TextView userName;
    TextView curpUser;
    TextView userUnit;
    TextView userPlaca;
    TextView userLicencia;
    TextView userVigencia;
    TextView noss,sAs,poliza;

    Wservice service = new Wservice();
    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_documentos, container, false);

        noss=view.findViewById(R.id.NSS);
        userName = view.findViewById(R.id.nameUser);
        curpUser = view.findViewById(R.id.userCurp);
        userUnit = view.findViewById(R.id.unitUser);
        userLicencia = view.findViewById(R.id.licenciaUser);
        userPlaca = view.findViewById(R.id.placaUser);
        userVigencia = view.findViewById(R.id.vigenciaUser);
        sAs=view.findViewById(R.id.sua);
        poliza=view.findViewById(R.id.POLIZASEGURO);
        userLicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        noss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        sAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        poliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



        String resultado = service.consumirWS();

        try {
            JSONObject o=new JSONObject(resultado);
            JSONObject driver=o.getJSONObject("driver");
            JSONObject vehicle=o.getJSONObject("vehicle");

            userName.setText(globalVariable.getUsuario().getNombre());
            userUnit.setText(globalVariable.getUsuario().getUnidad());
            getDriver(driver);
            getCar(vehicle);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void getDriver(JSONObject dri){
        try {
            String str = dri.getString("vigencia");
            long time = Long.parseLong( str.substring(6, str.length() - 2 ));

            Date date = new Date(time);
            String format = new SimpleDateFormat("dd/MM/yyyy").format(date);
            userLicencia.setText(dri.getString("License"));
            userVigencia.setText(format);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Driver_error",e.getMessage());
        }

    }

    private void getCar(JSONObject car){
        try {

            userPlaca.setText(car.getString("tag"));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Car_error",e.getMessage());
        }

    }

}
