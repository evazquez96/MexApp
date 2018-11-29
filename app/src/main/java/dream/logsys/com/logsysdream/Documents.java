package dream.logsys.com.logsysdream;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import logsys.dream.com.mx.activities.LoginActivity;

public class Documents extends AppCompatActivity {
Button cp,n87;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        cp=(Button)findViewById(R.id.CPO);
        n87=(Button)findViewById(R.id.NO87);
n87.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

  resp();
    }
});
cp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        resp1();
    }
});
    }




    public void resp(){
        Toast.makeText(Documents.this, "Visualizando documento", Toast.LENGTH_LONG).show();
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/pdf/respaldo.pdf");
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        Intent intent = Intent.createChooser(target, "Abrir PDF");
        try {
            Documents.this.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(Documents.this, "No existe una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show();  // el usuario no tiene ninguna app que pueda abrir pdfs
        }
    }
    public void resp1(){
        Toast.makeText(Documents.this, "Visualizando documento", Toast.LENGTH_LONG).show();
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Carta porte/Respaldo.pdf");
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        Intent intent = Intent.createChooser(target, "Abrir PDF");
        try {
            Documents.this.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(Documents.this, "No existe una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show();  // el usuario no tiene ninguna app que pueda abrir pdfs
        }
    }
}
