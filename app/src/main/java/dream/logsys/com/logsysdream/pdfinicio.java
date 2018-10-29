package dream.logsys.com.logsysdream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.db.ViajesDB;
import logsys.dream.com.mx.helpers.Viajes;
import logsys.dream.com.mx.startup.FrescoApplication;
import logsys.dream.com.mx.models.*;

public class pdfinicio{


    bitacora2 b = new bitacora2();



 private final Context context;
 public String[]header={"Evento","Ultimas 24:00hrs","Ultimas 5:00hrs"};
 public String sh="TRANSPORTES MEX AMERI K, S.A. DE C.V.";
 public String shl="Calle Mariano Escobedo S/N Colonia: Mariano Escobedo, Tultitlán, Estado de México, C.P. 54946";
 public String nombres;
 public int ids;
 public String fecha;
 public int no;
 public String originen;
 public String destyno;
 public String alia;

 public pdfinicio(Context context) {
  this.context=context;
  createarch2();
 }

 public void createarch2(){

  template2 tem= new template2(context.getApplicationContext());
  tem.opendocument();
  tem.addMetaData("Bitacora","NOM 87","Mexapp");
  tem.addtitle("Bitacora","NOM 87",fecha);
  tem.addparagraph(sh);
  tem.addparagraph(shl);
  tem.addparagraph("Operador  ");
  tem.addparagraph("licencia  "+"Vigencia  ");
  tem.addparagraph("unidad  "+"placas  ");
  tem.addparagraph("solicitud  "+"Tipo  ");
  tem.addparagraph("Origen  ");
  tem.addparagraph("Destino   ");
  tem.addparagraph("Origen  ");
  tem.addparagraph("Ultimas 24:00hrs  "+"  activo"+"  inactivo");
  tem.addparagraph("Ultimas 05:00hrs  "+"  activo"+"  inactivo");





 }


}
