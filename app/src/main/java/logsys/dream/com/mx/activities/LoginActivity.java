package logsys.dream.com.mx.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.io.File;

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
    private void terms(){

         Intent condi= new Intent(LoginActivity.this,condiciones.class);
         startActivity(condi);



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
    //sim = "5566940882";//
   // sim = "5530165296";//
sim = "5555071692";//Pablo sanches Rebolledo
// sim = "4422391223";//Heriberto Rico Hernandez
 //sim = "5563187021";

        Log.d("LoginActivity","::::::::::::::::::::::::::::: #teléfono" + sim);
        LoginTask = new UserLoginAutomaticoTask(sim);
        LoginTask.execute((Void) null);

    }

    private void configurarPermisos() {
        if (CheckingPermissionIsEnabledOrNot())
            iniciarApp();
        else {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                    {       WRITE_EXTERNAL_STORAGE,
                            READ_SMS,
                            READ_PHONE_STATE,
                            INTERNET
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
                dbParams.mergeParam("3",globalVariable.getUsuario().getId()+"","usuarioLogeado",globalVariable.getUsuario().getId()+"");


                RepViajes rep = new RepViajes();
                rep.inicializarRegistros();


                startMain();
            } else {
                Toast.makeText(LoginActivity.this, resultado, Toast.LENGTH_LONG).show();
                globalVariable.setUsuario(null);
                ParamsDB dbParams = new ParamsDB(globalVariable.getApplicationContext());
                dbParams.deleteParam(3);
                resp();
            }
        }

        @Override
        protected void onCancelled() {
            LoginTask = null;
        }
    }
    public void resp(){
        Toast.makeText(LoginActivity.this, "Visualizando documento", Toast.LENGTH_LONG).show();
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/pdf/respaldo.pdf");
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        Intent intent = Intent.createChooser(target, "Abrir PDF");
        try {
           LoginActivity.this.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(LoginActivity.this, "No existe una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show();  // el usuario no tiene ninguna app que pueda abrir pdfs
        }
    }
}

