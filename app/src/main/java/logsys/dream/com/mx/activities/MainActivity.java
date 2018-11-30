package logsys.dream.com.mx.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
       startService(new Intent(this,SecondPlain.class));


        startService(new Intent(this,SPcartaporte.class));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
      /*  WebView myWebView = (WebView) findViewById(R.id.webvew);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://mexamerik.com/");
*/
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
        Intent nav = new Intent(MainActivity.this, MainActivity.class);
        startActivity(nav);
        finish();
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

    

}
