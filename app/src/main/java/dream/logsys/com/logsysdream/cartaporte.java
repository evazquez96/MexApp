package dream.logsys.com.logsysdream;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import logsys.dream.com.mx.activities.LoginActivity;

public class cartaporte extends AppCompatActivity {
TextView cpo,sol;
String ssol,scpo;
ImageView verCP;
    Context context = null;
    ProgressDialog pdialog = null;
    private File pdf2;
    private File pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartaporte);
        cpo=(TextView)findViewById(R.id.CP);
        sol=(TextView)findViewById(R.id.solicitud);
        verCP=(ImageView)findViewById(R.id.attach);
        context=this;
        ssol = getIntent().getStringExtra("solicitud");
        scpo= getIntent().getStringExtra("cporte");
      try {
          pdialog = ProgressDialog.show(context, "", "Espere un momento...", true);
          asyncCp b = new asyncCp();
          b.execute();
          cpo.setText(scpo);
          sol.setText(ssol);
          pdialog.dismiss();
      }catch (Exception e0){

      }


       verCP.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               resp();

           }
       });

    }

    public void resp(){
        Toast.makeText(cartaporte.this, "Abriendo Documento ", Toast.LENGTH_LONG).show();
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Carta porte/"+ssol+".pdf");
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        Intent intent = Intent.createChooser(target, "Abrir PDF");
        try {
            cartaporte.this.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(cartaporte.this, "No existe una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show();  // el usuario no tiene ninguna app que pueda abrir pdfs
        }
    }


    public boolean consumirWScp() {
        Boolean bandera=true;
        HttpClient httpClient = new DefaultHttpClient();

        String id = sol.getText().toString();

        HttpGet del = new HttpGet("http://tms.logsys.com.mx/tms/api/v2.0/cartaporte/" + id+"/app");
        del.setHeader("Authorization", "Basic YWRtaW46bTN4NG0zcjFr");
        del.setHeader("Accept", "*/*");

        try {
            String ruta;
            HttpResponse resp = httpClient.execute(del);
            byte[]  respStr = EntityUtils.toByteArray(resp.getEntity());
            File folder= new File(Environment.getExternalStorageDirectory().toString(),"Carta porte");
            folder.mkdirs();
            pdf2=new File(folder,ssol+".pdf");
            ruta=pdf2.getPath();

            escribirArchivo(respStr,ruta);



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
            pdialog.dismiss();
            if(result.equals("ok")){
                pdialog.dismiss();

            }
            else{
                pdialog.dismiss();
                Log.e("ERROR", "Error al consumir el webService");
            }
            pdialog.dismiss();
        }






    }
    public static boolean escribirArchivo(byte[] fileBytes, String archivoDestino){
        boolean correcto = false;
        try {
            OutputStream out = new FileOutputStream(archivoDestino);
            out.write(fileBytes);
            out.close();
            correcto = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return correcto;

    }
    public static boolean respaldo(byte[] fileBytes, String archivoDestino){
        boolean correcto = false;
        try {
            OutputStream out = new FileOutputStream(archivoDestino);
            out.write(fileBytes);
            out.close();
            correcto = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return correcto;

    }

}
