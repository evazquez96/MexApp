package logsys.dream.com.mx.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;

import dream.logsys.com.logsysdream.Documents;
import dream.logsys.com.logsysdream.R;
import dream.logsys.com.logsysdream.SecondPlain;
import dream.logsys.com.logsysdream.condiciones;
import dream.logsys.com.logsysdream.lector;
import logsys.dream.com.mx.contracts.Usuario;
import logsys.dream.com.mx.db.ParamsDB;
import logsys.dream.com.mx.repos.RepUsuario;
import logsys.dream.com.mx.repos.RepViajes;
import logsys.dream.com.mx.startup.FrescoApplication;
import logsys.dream.com.mx.ws.Repos.LoginRepo_Ws;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {//implements LoaderCallbacks<Cursor> {

    private static FrescoApplication globalVariable;

    private UserLoginAutomaticoTask LoginTask = null;

    // UI references.

    private Button btn_reintentar;

    private ProgressDialog progress;

    public static final int RequestPermissionCode = 8;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        configurarPermisos();
        this.inicializarApp();
        this.inicializarUI();
        this.intentarLoginAutomatico();


        globalVariable = (FrescoApplication)getApplicationContext();

    }


    private void inicializarApp()
    {
        progress=new ProgressDialog(this);
    }

    private void inicializarUI()
    {
        setContentView(R.layout.activity_login);

        //Hardcodeado, si se borra marca error al accesar a los elementos del UI desde un hilo
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_reintentar = findViewById(R.id.btn_reintentar);
        btn_reintentar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                intentarLoginAutomatico();
            }
        });
    }


    private void iniciarApp()
    {
        //aqui se setea el numero telefonico
        TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String sim = telemamanger.getLine1Number();

//sim = sim.substring(sim.indexOf('+')+3);       //descomentar esta linea y comentar la linea 95
        //hardcodeado
//sim="5551399925";
   //   sim="5555082466";//jg Aguilar
     //sim="5525647774";ivan
       // sim="5530436756";
        //sim="5551399925";//paola reyes
// sim = "5530316921";//forlan
sim = "5585304510";//Pablo sanches Rebolledo
//sim = "5530165296";//mario peña hr
      //  sim = "5521016727";//ruben reyes
//sim = "4422391223";//Heriberto Rico Hernandez
 //sim = "5563187021";

        Log.d("LoginActivity","::::::::::::::::::::::::::::: #teléfono" + sim);
        LoginTask = new UserLoginAutomaticoTask(sim);
        LoginTask.execute((Void) null);
        asyncdocuments b = new asyncdocuments();
        b.execute();

    }

    private void configurarPermisos() {
        if (CheckingPermissionIsEnabledOrNot())
            iniciarApp();
        else {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                    {       WRITE_EXTERNAL_STORAGE,
                            READ_SMS,
                            READ_PHONE_STATE,
                            INTERNET,CALL_PHONE

                    }, RequestPermissionCode);
        }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int ReadSmsPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        int ReadPhoneStatePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int ReadSdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int InternetPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);

        return ReadSmsPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ReadPhoneStatePermissionResult == PackageManager.PERMISSION_GRANTED && InternetPermissionResult == PackageManager.PERMISSION_GRANTED;
    }


    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean ReadSmsPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhonePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean InternetPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (ReadSmsPermission && InternetPermission && ReadPhonePermission) {
                        iniciarApp();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }
                break;
        }
    }

    private void intentarLoginAutomatico() {

        try {
            progress.setMessage("Conectando con servidor remoto para ingresar, por favor espere");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.setCancelable(false);
            progress.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Abrir actividad Main Principal
    public void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static Context getAppContext() {
        return globalVariable;
    }



    public class UserLoginAutomaticoTask extends AsyncTask<Void, Void, Boolean> {

        private final String mNumeroTelefono;
        private String resultado;

        UserLoginAutomaticoTask(String numeroTelefono) {
            this.mNumeroTelefono = numeroTelefono;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            final FrescoApplication globalVariable = (FrescoApplication) getApplicationContext();
            LoginRepo_Ws lws = new LoginRepo_Ws();

            Usuario res = lws.logueoAutomatico(mNumeroTelefono);
            RepUsuario rUsu = new RepUsuario();
            try
            {
                rUsu.sincronizarUsuarios();
            }catch (Exception ex2)
            {
            }
            if(res==null) {

                resultado = "No se encuentra asignado a una unidad o no se pudo conectar con el servidor remoto";
                return false;
            }
            else if(res.getCodigRespuesta()==1)
                resultado = res.getRespuesta();

            res.setImei(mNumeroTelefono);
            globalVariable.setUsuario(res);

            if(res.getCodigRespuesta() == 0) {
                resultado = "True";
                return  true;
            }
            else {
                resultado = res.getRespuesta() + mNumeroTelefono;
                return  false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            LoginTask = null;
            progress.hide();

            if (success) {
                ParamsDB dbParams= new ParamsDB(globalVariable);
                dbParams.mergeParam("0",globalVariable.getUsuario().getId()+"","usuarioLogeado",globalVariable.getUsuario().getId()+"");


                RepViajes rep = new RepViajes();
                rep.inicializarRegistros();


                startMain();
            } else {
                Toast.makeText(LoginActivity.this, resultado, Toast.LENGTH_LONG).show();
                globalVariable.setUsuario(null);
                ParamsDB dbParams = new ParamsDB(globalVariable.getApplicationContext());
                dbParams.deleteParam(0);
            Intent doc= new Intent(LoginActivity.this,Documents.class);
            startActivity(doc);
            }
        }

        @Override
        protected void onCancelled() {
            LoginTask = null;
        }
    }


    // Metodo que queremos ejecutar en el servicio web
    private static final String Metodo = "getInfoGeneral";
    // Namespace definido en el servicio web
    private static final String namespace = "http://tempuri.org/";
    // namespace + metodo
    private static final String accionSoap = "http://tempuri.org/getInfoGeneral";
    // Fichero de definicion del servcio web
    private static final String url = "Finte";
    public SoapPrimitive resultado;
    Context context = null;

    int pru;
    String alia,imgemp;


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

        private void getoperador(JSONObject car){
            try {


                imgemp=car.getString("imgEmpleado");
                dbimage();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Car_error",e.getMessage());
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
                        getoperador(op);



                    }
                    catch(Exception e){
                        Toast.makeText(context, "" + "!!No hay datos intentelo mas tarde¡¡", Toast.LENGTH_SHORT).show();  // el usuario no tiene ninguna app que pueda abrir pdfs



                        Log.e("JSONArrayError",e.getMessage());
                    }
                } catch (Exception e) {


                }
            }else{

                Log.e("ERROR", "Error al consumir el webService");
            }
        }




    }

    public void dbimage(){
        SQLiteDatabase myDB =
                openOrCreateDatabase("image", MODE_PRIVATE, null);
        myDB.execSQL("drop table IF  EXISTS user");
        myDB.execSQL("CREATE TABLE IF NOT EXISTS user (name VARCHAR(100000))");
        ContentValues row1 = new ContentValues();
        row1.put("name", imgemp);
        myDB.insert("user", null, row1);
        myDB.close();
    }



}

