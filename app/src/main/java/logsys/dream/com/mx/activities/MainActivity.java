package logsys.dream.com.mx.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import dream.logsys.com.logsysdream.BaseFragment;
import dream.logsys.com.logsysdream.BlankFragment;
import dream.logsys.com.logsysdream.DreamFragment;
import dream.logsys.com.logsysdream.InicioFragment;
import dream.logsys.com.logsysdream.NotificacionFragment;
import dream.logsys.com.logsysdream.R;
import dream.logsys.com.logsysdream.SecondPlain;
import dream.logsys.com.logsysdream.ViajeFragment;
import dream.logsys.com.logsysdream.bitacora2;
import dream.logsys.com.logsysdream.documentos;
import dream.logsys.com.logsysdream.wsBitaciora.SPcartaporte;
import logsys.dream.com.mx.startup.FrescoApplication;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        BaseFragment.OnFragmentInteractionListener
{
    public  static Context sContext;
    ImageView fotos;
    //ver bitacora prueba
    //Button bitaco = (Button) findViewById(R.id.bita);
    //Button mm = (Button) findViewById(R.id.mi_procesos_bitacora);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        pdialog = ProgressDialog.show(context, "", "cargando documentos", true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
      /*  WebView myWebView = (WebView) findViewById(R.id.webvew);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://mexamerik.com/");*/
        TextView tv = (TextView)headerView.findViewById(R.id.txt_globaluser);
        fotos=(ImageView)headerView.findViewById(R.id.Fotooperador);
        final FrescoApplication globalVariable = (FrescoApplication) getApplicationContext();
        tv.setText(globalVariable.getUsuario().getNombre());
        tv = (TextView)headerView.findViewById(R.id.txt_globaluser_ma);
        tv.setText(globalVariable.getUsuario().getUnidad());
        //txt_globaluser_ma


        //Button_bitacora prueba


        sContext = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState!=null)
            return;


      BaseFragment fragment = new InicioFragment();

        Intent intent = getIntent();

        if(intent!=null && intent.getExtras()!=null)
        {
           int fragmentId = intent.getIntExtra("fragmento",1);
            if( fragmentId==1)
                fragment = new ViajeFragment();
            else if(fragmentId==2)
                fragment = new DreamFragment();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commitNow();
        mostrarimagen();
        asignarImagen(imagens);
        try {
            asyncdocuments b= new asyncdocuments();
            b.execute();
        }catch (Exception e){

        }
    }

    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //NOTE: creating fragment object
        Fragment fragment = null;
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setVisible(true);

        if (id == R.id.mi_procesos_sueno) {
            fragment = new DreamFragment();
            menuItem.setIcon(R.drawable.despertador);
        }
        else if( id == R.id.mi_procesos_eviencia) {
            fragment = new BlankFragment(); //new EvidenciasFragment();
            menuItem.setIcon(R.drawable.escaner);
        }
        else if( id == R.id.mi_procesos_notificaciones) {
            fragment = new NotificacionFragment();
            menuItem.setIcon(R.drawable.campana);
        }
        else if(id==R.id.mi_procesos_bitacora) {

            Intent nav = new Intent(MainActivity.this, bitacora2.class);
            startActivity(nav);

        }
        else if(id==R.id.mi_procesos_viajes) {
            fragment = new ViajeFragment();
            menuItem.setVisible(false);
        }
        else if(id==R.id.doc) {
            fragment = new documentos();
            menuItem.setIcon(R.drawable.lobit);
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commitNow();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String uri)
    {
        ActionBar bar = getSupportActionBar();
        bar.setTitle(uri);
    }


    @Override
    public void onBackPressed() {

    }
    String imagens;

    public void mostrarimagen(){
        try {
            SQLiteDatabase myDB = openOrCreateDatabase("image", MODE_PRIVATE, null);
            Cursor myCursor = myDB.rawQuery("select name from user", null);
            while(myCursor.moveToNext()) {
                imagens = myCursor.getString(0);
                myCursor.close();
                myDB.close();

            }
        }catch (Exception e){

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

    // Metodo que queremos ejecutar en el servicio web
    private static final String Metodo = "GetViajesActual";
    // Namespace definido en el servicio web
    private static final String namespace = "http://tempuri.org/";
    // namespace + metodo
    private static final String accionSoap = "http://tempuri.org/GetViajesActual";
    // Fichero de definicion del servcio web
    private static final String url = "https://app.mexamerik.com/Mexapp_viajes/Viajes.asmx?";
    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();
    public SoapPrimitive resultado;
    int pru;
    Context context = null;
    ProgressDialog pdialog = null;
    private File pdf;
    String fecha,ssolic,sclient,ssshitmen,scpo,sorigen,sdeestino,sdirori,sccarga,sdirdesty,scitcar,sconve,inter,olat,olog,dlat,dlog,dinter,ddlat,ddlog,dolat,dolog;
    public boolean consumirWS(){

pru=globalVariable.getUsuario().getId();

        Boolean bandera=true;
        try {
            String s=new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date());
            fecha=s;
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("fecha", fecha);
            request.addProperty("ID_Usuario", pru);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(url);
            transporte.call(accionSoap, sobre);
            resultado= (SoapPrimitive) sobre.getResponse();
            int ssma=4+4;

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            bandera=false;
        }finally {

            return bandera;
        }}
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
                ssolic=car.getString("Id");


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
                    try {
                        asyncdocuments b= new asyncdocuments();
                        b.execute();
                        asyncCp cp= new asyncCp();
                        cp.execute();
                    }catch (Exception e){

                    }
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
    public boolean consumirWScp() {
        Boolean bandera=true;
        HttpClient httpClient = new DefaultHttpClient();

        String id = ssolic;

        HttpGet del = new HttpGet("http://tms.logsys.com.mx/tms/api/v2.0/cartaporte/" + id+"/app");
        del.setHeader("Authorization", "Basic YWRtaW46bTN4NG0zcjFr");
        del.setHeader("Accept", "*/*");

        try {
            String ruta2;
            HttpResponse resp = httpClient.execute(del);
            byte[]  respStr = EntityUtils.toByteArray(resp.getEntity());
            File folder= new File(Environment.getExternalStorageDirectory().toString(),"Carta porte");
            folder.mkdirs();
            pdf=new File(folder,"Respaldo.pdf");
            ruta2=pdf.getPath();
            respaldo(respStr,ruta2);


        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
        }
        return bandera;
    }

    private class asyncCp extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            if (consumirWScp()) {
                return "ok";
            } else
                return "error";
        }

        protected void onPostExecute(String result){

         }}

    public boolean respaldo(byte[] fileBytes, String archivoDestino){
        boolean correcto = false;
        try {
            OutputStream out = new FileOutputStream(archivoDestino);
            out.write(fileBytes);
            out.close();
            startService(new Intent(context,SecondPlain.class));
            pdialog.dismiss();
            correcto = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return correcto;

    }

}
