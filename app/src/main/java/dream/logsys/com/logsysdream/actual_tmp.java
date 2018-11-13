package dream.logsys.com.logsysdream;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.Date;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.startup.FrescoApplication;


public class actual_tmp extends Fragment {
    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();

    // Metodo que queremos ejecutar en el servicio web
    private static final String Metodo = "GetViajesActual";
    // Namespace definido en el servicio web
    private static final String namespace = "http://tempuri.org/";
    // namespace + metodo
    private static final String accionSoap = "http://tempuri.org/GetViajesActual";
    // Fichero de definicion del servcio web
    private static final String url = "https://app.mexamerik.com/Mexapp_viajes/Viajes.asmx?";
    public SoapPrimitive resultado;
    Context context = null;
    ProgressDialog pdialog = null;
    int pru;
TextView header,solicitud,shitment,cpo,cliente,origin,destino,dir,ocita,dirdest,citdes;
ImageView gmap1,gmaps2;
String fecha,ssolic,sclient,ssshitmen,scpo,sorigen,sdeestino,sdirori,sccarga,sdirdesty,scitcar,sconve,inter,olat,olog,dlat,dlog,dinter;
    private static MainActivity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_actual_tmp, container, false);
        mActivity = (MainActivity) getActivity();

        header=view.findViewById(R.id.tvheader);
        solicitud=view.findViewById(R.id.tvheader1);
        shitment=view.findViewById(R.id.tvshipment1);
        cpo=view.findViewById(R.id.tvcp1);
        cliente=view.findViewById(R.id.tvcliente1);
        origin=view.findViewById(R.id.tvorigentmp1);
        dir=view.findViewById(R.id.tvdc1);
        ocita=view.findViewById(R.id.tvcitacarga1);
        destino=view.findViewById(R.id.tvorigentmp2);
        dirdest=view.findViewById(R.id.tvdd1);
        citdes=view.findViewById(R.id.tvcitadescarga1);
        gmap1=view.findViewById(R.id.img_navegar);
        gmaps2=view.findViewById(R.id.img_navegar1);
       asyncdocuments b = new asyncdocuments();
        b.execute();

       gmap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String intermedios = inter;
                if(intermedios!=null && intermedios.length()>0)
                {
                    intermedios = "&" + intermedios;
                }
                else
                    intermedios="";
                //String uri = "https://www.google.com/maps/dir/?api=1&origin=" + viaje.getoLatitud() +"," + viaje.getoLongitud() + "&destination=" + viaje.getdLatitud()+ "," + viaje.getdLongitud() + intermedios + "&travelmode=driving&dir_action=navigate";
                String uri = "https://www.google.com/maps/dir/?api=1&destination=" + olat+ "," + olog + intermedios + "&travelmode=driving&dir_action=navigate";
                Log.d(":::::::::::::::::",uri);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                mActivity.startActivity(intent);

            }
        });


       gmaps2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String intermedios = dinter;
               if(intermedios!=null && intermedios.length()>0)
               {
                   intermedios = "&" + intermedios;
               }
               else
                   intermedios="";
               //String uri = "https://www.google.com/maps/dir/?api=1&origin=" + viaje.getDoLatitud() +"," + viaje.getDoLongitud() + "&destination=" + viaje.getDdLatitud()+ "," + viaje.getDdLongitud() + intermedios + "&travelmode=driving&dir_action=navigate";
               String uri = "https://www.google.com/maps/dir/?api=1&destination=" + dlat+ "," + dlog + intermedios + "&travelmode=driving&dir_action=navigate";
               Log.d(":::::::::::::::::",uri);
               Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
               intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
               mActivity.startActivity(intent);
           }});

        return view;
    }

    public boolean consumirWS(){
        mandar();
        int tam=100136;
        Boolean bandera=true;
        try {

            String s=new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date());
            fecha=s;

            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("fecha", fecha);
            request.addProperty("ID_Usuario", pru);


            // Modelo el Sobre
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            sobre.dotNet = true;

            sobre.setOutputSoapObject(request);

            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(url);

            // Llamada
            transporte.call(accionSoap, sobre);

            // Resultado
            resultado= (SoapPrimitive) sobre.getResponse();
            int a=4+4;


        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            bandera=false;
        }finally {

            return bandera;

        }



    }
    private class asyncdocuments extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            if (consumirWS()) {
                return "ok";
            } else
                return "error";
        }


        private void getCar(JSONObject car){
String format,form2,for3;
           // scpo TextView header,solicitud,shitment,cpo,cliente,origin,destino,dir,ocita,dirdest,citdes;

            try {
                ssolic=car.getString("Id");
             sconve=car.getString("Convenio");
           sclient=car.getString("Cliente");
           sorigen=car.getString("Origen");
            sdeestino=car.getString("Destino");
              sdirori=car.getString("DireccionCarga");
            sccarga=car.getString("CitaCarga");
                sdirdesty=car.getString("DireccionDescarga");
                scitcar=car.getString("CitaDescarga");
                olat=car.getString("O_Latitud");
                olog=car.getString("O_Longitud");
                dlat=car.getString("D_Latitud");
                dlog=car.getString("D_Longitud");
                dinter=car.getString("D_Intermedios");

                long time = Long.parseLong( sccarga.substring(6, sccarga.length() - 2 ));
                Date date = new Date(time);
                format = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date);

                long time1 = Long.parseLong( scitcar.substring(6, scitcar.length() - 2 ));
                Date date1 = new Date(time1);
                form2 = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date1);

              header.setText(sconve);
                solicitud.setText("Solicitud "+ssolic);
             shitment.setText(""+car.getString("Shipment"));
         cliente.setText(car.getString("Cliente"));
          origin.setText(sorigen);
          cpo.setText(car.getString("CartaPorte"));
                destino.setText(sdeestino);
                dir.setText(sdirori);
         ocita.setText(""+format);
           dirdest.setText(sdirdesty);
        citdes.setText(form2);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Car_error",e.getMessage());
            }

        }




        protected void onPostExecute(String result){
            if(result.equals("ok")){
                String res,res2;

                    try {
                        int sum=2+2;
                        res=resultado.toString().replace("[","");
                        res2=res.replace("]","");
                        JSONObject o=new JSONObject(res2);
                        getCar(o);
                        int sum12=2+2;




                    }
                    catch(Exception e){

                        Log.e("JSONArrayError",e.getMessage());
                    }
            }
            else{
                Log.e("ERROR", "Error al consumir el webService");
            }
        }




    }
    public  void mandar(){
        pru = globalVariable.getUsuario().getId();


    }



}
