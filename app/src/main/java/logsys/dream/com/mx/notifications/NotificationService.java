package logsys.dream.com.mx.notifications;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.db.ViajesDB;

/**
 * Created by JUAHERNA on 3/5/2018.
 */

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        Map<String, String> data = remoteMessage.getData();

        Log.d("NotificationService", "From: " + remoteMessage.getFrom());



        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("NotificationService", "Message data payload: " + remoteMessage.getData());

            if (true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("NotificationService", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        displayNotification(remoteMessage.getNotification(), remoteMessage.getData());
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }

    public void  mostrarNotificacion(StringBuilder msgNotificacion,int fragmento,String titulo)
    {
        Intent intent = new Intent(MainActivity.sContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragmento",fragmento);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.sContext, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_app_notificacion)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.menu_viaje))
                .setContentTitle(titulo)
                .setContentText("textoooo")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msgNotificacion.toString()))
                ;
        NotificationManager notificationManager =
                (NotificationManager)MainActivity.sContext.getSystemService(MainActivity.sContext.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


    private void displayNotification(RemoteMessage.Notification notification, Map<String, String> data) {
        StringBuilder msgNotificacion = new StringBuilder();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragmento",1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);



        viajeTO viaje = toViaje(data);

        msgNotificacion.append("Cita Carga:" + viaje.getCitaCarga() + System.getProperty("line.separator"));
        msgNotificacion.append("Dirección Carga:" + viaje.getDireccionCarga()+ System.getProperty("line.separator"));
        msgNotificacion.append("Cita Descarga:" + viaje.getCitaDescarga()+ System.getProperty("line.separator"));
        msgNotificacion.append("Dirección Descarga:" + viaje.getDireccionDescarga()+ System.getProperty("line.separator"));
        msgNotificacion.append("Shipment:" + viaje.getShipment());

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_app_notificacion)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.menu_viaje))
                .setContentTitle("Asignación de Viaje: " + viaje.getNumeroSolicitud()) //+ data.get("NumeroSolicitud"))
                .setContentText(viaje.getCliente()) //(data.get("cliente"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msgNotificacion.toString()))
                //.addAction(R.drawable.add_notification,"More",pendingIntent)
                ;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        try {
            ViajesDB db = new ViajesDB(LoginActivity.getAppContext());
            db.guardarViaje(viaje);
            Log.i("NotificationService","# de viajes en Bd Local:" + db.countRows("Viajes"));

        }catch (Exception ex)
        {
            Log.e("NotificationService",ex.getMessage());
        }

    }

    private viajeTO toViaje(Map<String,String> data)
    {
        viajeTO viaje = new viajeTO();

        viaje.setCitaCarga(data.get("Cita_Carga"));
        viaje.setCitaDescarga(data.get("Cita_Descarga"));
        viaje.setCliente(data.get("Cliente"));
        viaje.setConvenio(data.get("Convenio"));
        viaje.setCp(data.get("CP"));
        viaje.setDestino(data.get("Destino"));
        viaje.setDireccionDescarga(data.get("Direccion_Descarga"));
        viaje.setDireccionEntrega(data.get("Direccion_Carga"));
        //viaje.setId(Integer.parseInt(data.get("NumeroSolicitud")));
        viaje.setNumeroSolicitud(Integer.parseInt(data.get("NumeroSolicitud")));
        viaje.setOrigen(data.get("Origen"));
        viaje.setShipment(data.get("Shipment"));
        viaje.setEstatus("Actual");
        viaje.setId(Integer.parseInt(data.get(("Id"))));

        viaje.setoLatitud(data.get("oLatitud"));
        viaje.setoLongitud(data.get("oLongitud"));
        viaje.setdLatitud(data.get("dLatitud"));
        viaje.setdLongitud(data.get("dLongitud"));
        viaje.setIntermedios(data.get("Intermedios"));

        viaje.setDoLatitud(data.get("doLatitud"));
        viaje.setDoLongitud(data.get("doLongitud"));
        viaje.setDdLatitud(data.get("ddLatitud"));
        viaje.setDdLongitud(data.get("ddLongitud"));
        viaje.setDintermedios(data.get("dIntermedios"));

        return  viaje;
    }
}