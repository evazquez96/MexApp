package dream.logsys.com.logsysdream;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
import logsys.dream.com.mx.helpers.Viajes;
import logsys.dream.com.mx.models.Bitacora;
import logsys.dream.com.mx.models.Dream;
import logsys.dream.com.mx.models.Registro;
import logsys.dream.com.mx.models.ServiceData;
import logsys.dream.com.mx.startup.FrescoApplication;
import utils.GsonHelper;

import static android.widget.Toast.LENGTH_LONG;


public class documentos extends Fragment {

    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();


    // Metodo que queremos ejecutar en el servicio web
    private static final String Metodo = "getInfoGeneral";
    // Namespace definido en el servicio web
    private static final String namespace = "http://tempuri.org/";
    // namespace + metodo
    private static final String accionSoap = "http://tempuri.org/getInfoGeneral";
    // Fichero de definicion del servcio web
    private static final String url = "https://app.mexamerik.com/mexapp/MexAppws.asmx?";
    public  SoapPrimitive resultado;
    Context context = null;
    ProgressDialog pdialog = null;
    int pru;
    String alia, nombre,no,ap,am,imgemp,tag,seguro,imgseg,Svic,imgliv,imgsua,imgpoliza,imgcurp,nocont,antuguedad,telcontac,srfc,snimss,scurp,clic;
    TextView userName;
    TextView curpUser;
    TextView userUnit;
    TextView userPlaca;
    TextView userLicencia;
    TextView userVigencia;
    LinearLayout lnco;
    TextView noss,sAs,poliza,aseg,teas,psu,tell,urfc,tiempo,control;
    ImageView fotos;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_documentos, container, false);
        context = view.getContext();

        noss=view.findViewById(R.id.NSS);
        fotos=view.findViewById(R.id.foto);
        userName = view.findViewById(R.id.nameUser);
        curpUser = view.findViewById(R.id.userCurp);
        userUnit = view.findViewById(R.id.unitUser);
        urfc = view.findViewById(R.id.RFC);
        userLicencia = view.findViewById(R.id.licenciaUser);
        userPlaca = view.findViewById(R.id.placaUser);
        userVigencia = view.findViewById(R.id.vigenciaUser);
        aseg = view.findViewById(R.id.Aseguradora);
        teas = view.findViewById(R.id.telAseguradora);
        sAs=view.findViewById(R.id.sua);
        tiempo=view.findViewById(R.id.antiguedad);
        psu=view.findViewById(R.id.Poliza);
        tell=view.findViewById(R.id.Tel);
        control=view.findViewById(R.id.Rcontrol);
        poliza=view.findViewById(R.id.POLIZASEGURO);
        userName.setText(nombre);
        userUnit.setText(alia);
        try {
            pdialog = ProgressDialog.show(context, "", "Espere un momento...", true);
            asyncdocuments b = new asyncdocuments();
            b.execute();

        }catch (Exception e){

        }


        tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  try{
    String llamada;
    llamada=tell.getText().toString();
                      Intent i = new Intent(Intent.ACTION_CALL);
                      i.setData(Uri.parse("tel:"+llamada));
                      startActivity(i);
                }catch (Exception e)
                  {

            }
            }});

        userLicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try{
    Uri uri = Uri.parse(imgliv.replace(" ",""));
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);

}catch (Exception e){

    Intent imagen = new Intent(getActivity().getApplication(), imagens.class);
    startActivity(imagen);
}

            }
        });
        noss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Uri uri = Uri.parse(imgseg.replace(" ",""));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
                catch (Exception e){

                   Intent imagen = new Intent(getActivity().getApplication(), imagens.class);
                    startActivity(imagen);
                }

            }
        });
        sAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{


                  //  Toast.makeText(context, usus, Toast.LENGTH_SHORT).show();  // el usuario no tiene ninguna app que pueda abrir pdfs


                    Uri uri = Uri.parse(imgsua.replace(" ",""));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }catch (Exception e){

                    Intent imagen = new Intent(getActivity().getApplication(), imagens.class);
                    startActivity(imagen);
                }
            }
        });

        poliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Uri uri = Uri.parse(imgpoliza.replace(" ",""));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }catch (Exception e){

                    Intent imagen = new Intent(getActivity().getApplication(), imagens.class);
                    startActivity(imagen);
                }
            }
        });
        curpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Uri uri = Uri.parse(imgcurp);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }catch (Exception e){

                    Intent imagen = new Intent(getActivity().getApplication(), imagens.class);
                    startActivity(imagen);
                }


            }
        });


        return view;
    }



    public boolean consumirWS(){
        Boolean bandera=true;
        try {


            alia=globalVariable.getUsuario().getUnidad();
            pru=globalVariable.getUsuario().getId();
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("aliasUnidad", alia);
            request.addProperty("id_tms", pru);


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
            /*
             * El finally siempre se va a ejecutar, sin importar que se lanze
             * una exepction
             */
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
            try {
                userUnit.setText(alia);
                tag=car.getString("tag");
                userPlaca.setText(car.getString("tag"));
                seguro=car.getString("Insurance");
                aseg.setText(car.getString("Insurance"));
                teas.setText(car.getString("Ins_Phone"));
                psu.setText(car.getString("Ins_Policy"));

                telcontac=car.getString("telefono");
                tell.setText(car.getString("telefono"));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Car_error",e.getMessage());
            }

        }
        private void getoperador(JSONObject car){
            //srfc,snimss,scurp,clic
            try {
                String nombrecompleto;
                String format,fant;
                int ant,an2;
                imgemp=car.getString("imgEmpleado");
                no=car.getString("nombre");
                ap=car.getString("apPat");
                am=car.getString("apMat");
                srfc=car.getString("rfc");
                scurp=car.getString("curp");
                clic=car.getString("nLicencia");
                Svic=car.getString("fch_lic_vencimiento");
                snimss=car.getString("nIMSS");
                nocont=car.getString("noControl");
                antuguedad=car.getString("antiguedad");

                imgliv=car.getString("licenciaURL");
                imgsua=car.getString("imgSUA");
                imgseg=car.getString("imsURL");


   if (nocont=="null"){
       control.setText("");
   }else{
       control.setText(""+nocont);
   }









                asignarImagen(imgemp);
                ant=Integer.parseInt(antuguedad);
                an2=ant/365;
                fant=an2+" años";
                nombrecompleto=no+" "+ap+" "+am;
                userName.setText(nombrecompleto);
                urfc.setText(srfc);
                noss.setText(snimss);
                curpUser.setText(scurp);
                userLicencia.setText(clic);

                long time = Long.parseLong( Svic.substring(6, Svic.length() - 2 ));
                Date date = new Date(time);
                format = new SimpleDateFormat("dd/MM/yyyy").format(date);
                userVigencia.setText(format);
                if(ant>=360){
                    tiempo.setText(fant);
                }else if(ant<360){
                    tiempo.setText(antuguedad+" dias");
                }
                try {
                    imgpoliza=car.getString("imgpoliza");
                    imgcurp=car.getString("imgcurp");
                }catch (Exception e){

                }




            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Car_error",e.getMessage());
            }
        }

        public void asignarImagen(String picture) {

            try {

                byte[] imageAsBytes = Base64.decode(picture.getBytes(), Base64.DEFAULT);
                fotos.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                );
            } catch (Exception e) {
                Log.e("Error imagen",e.getMessage());
            }
        }

        protected void onPostExecute(String result){
            if(result.equals("ok")){
                try {
                    //Se cargara la informacion en todo

                    Log.e("ResultadoEnPostExecute", resultado.toString());



                    try {
                        JSONObject o=new JSONObject(resultado.toString());
                        JSONObject op=new JSONObject(resultado.toString());
                        JSONObject vehicle=o.getJSONObject("vehicle");
                        getCar(vehicle);
                        getoperador(op);
                        pdialog.dismiss();


                    }
                    catch(Exception e){
                        Toast.makeText(context, "" + "!!No hay datos intentelo mas tarde¡¡", Toast.LENGTH_SHORT).show();  // el usuario no tiene ninguna app que pueda abrir pdfs


                        pdialog.dismiss();
                        Log.e("JSONArrayError",e.getMessage());
                    }
                } catch (Exception e) {


                }
            }else{
                pdialog.dismiss();
                Log.e("ERROR", "Error al consumir el webService");
            }
        }




    }




}
