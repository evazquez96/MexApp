package dream.logsys.com.logsysdream;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.contracts.Dream;
import logsys.dream.com.mx.db.DreamDB;
import logsys.dream.com.mx.startup.FrescoApplication;
import logsys.dream.com.mx.ws.Repos.LoginRepo_Ws;

/**
 * Created by JUAHERNA on 2/21/2018.
 */

public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public static Vibrator mVibrator;
    public static MediaPlayer ring = null;

    protected SwipeRefreshLayout mswipeRefreshLayout;
    protected ProgressDialog progress;
    protected FrescoApplication globalVariable;

    abstract int get_ViewResourceId();
    abstract String getNombreModulo();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mswipeRefreshLayout = getView().findViewById(R.id.swipe_refresh_layout);
        mswipeRefreshLayout.setOnRefreshListener(this);

        globalVariable = (FrescoApplication) getActivity().getApplicationContext();
    }

    private OnFragmentInteractionListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(get_ViewResourceId(), container, false);

        if (mListener != null) {
            mListener.onFragmentInteraction(getNombreModulo());
        }
        return view;
    }

    @Override
    public void onRefresh() {
        mswipeRefreshLayout.setRefreshing(false);

        progress=new ProgressDialog(getActivity());
        progress.setMessage("Conectando con servidor remoto para actualizar, por favor espere");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    protected void mostrarProgress(String mensaje)
    {
        progress=new ProgressDialog(getActivity());
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCancelable(false);
        progress.show();
    }

    protected void ocultarTabs()
    {

    }

    protected String getNumeroTelefono()
    {
        return globalVariable.getUsuario().getImei();
    }

    protected String getVersionApp()
    {
        String version = "";
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version = e.getMessage();
        }
        return version;
    }

    protected int getUserId()
    {
        return  globalVariable.getUsuario().getId();
    }

    protected boolean is_logued()
    {
        try {
            String numeroTelefono = getNumeroTelefono();
            String version = getVersionApp();

            //Verificar esta parte o encapsularla
            DreamDB te = new DreamDB((globalVariable));
            long inicio_id = te.obtenerIdUltimSueno(globalVariable.getUsuario().getId());
            try {
                Dream d = te.obtenerSueno(inicio_id);
                if(d.getFechaFin()!=null)
                    inicio_id= -1;
            }catch (Exception ex)
            {
            }
            if(inicio_id==0)
                inicio_id=-1;

            LoginRepo_Ws lws = new LoginRepo_Ws();
            int logueado = lws.islogued(globalVariable.getUsuario().getId(), inicio_id,numeroTelefono,version);

            //logica para regresar true o false

            /*
            if (logueado !=0) {
                if(LoginRepo_Ws.s_iniciado && logueado ==2)
                {
                    LoginRepo_Ws.s_iniciado=false;

                    //dbParams.deleteParam(2);
                    //dbParams.deleteParam(1);
                    te.depurarRegistros(LoginRepo_Ws.user.getId());
                }
                return true;
            }
            LoginRepo_Ws.s_iniciado=false;


            te.depurarRegistros(LoginRepo_Ws.user.getId());

            globalVariable.setUsuario(null);
            LoginRepo_Ws.user = null;


            //dbParams.deleteParam(3);
            //dbParams.deleteParam(1);






            //finishAffinity();

            return false;
            */
            return  true;
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return true;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( progress!=null && progress.isShowing() ){
            progress.cancel();
        }
    }

    //public Vibrator mVibrator;

    public void enviarNotificacion(String msg,int idFragmento,String titulo,boolean autoCancel,String txt,boolean vibrar)
    {
        Log.d("sendNotification", "::::::::::::::Preparing to send notification...: " + msg);

        if(vibrar)
        {
            long[] pattern = {0, 100, 1000, 300, 3000, 300};
            mVibrator = (Vibrator) MainActivity.sContext.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            mVibrator.vibrate(pattern, 0);
        }

        /*
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(MainActivity.sContext.getApplicationContext(), notification);
        r.play();
*/

try
{
        ring = MediaPlayer.create(MainActivity.sContext.getApplicationContext(),R.raw.gallo);
        ring.setLooping(true);
        //ring.setVolume(90f,100f);

        AudioManager audio = (AudioManager) MainActivity.sContext.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        audio.setStreamVolume(AudioManager.STREAM_MUSIC,audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);

        ring.start();
} catch (Exception ex)
{
    ex.printStackTrace();
}

        Intent intent = new Intent(MainActivity.sContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragmento",idFragmento);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.sContext, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MainActivity.sContext)
                .setSmallIcon(R.drawable.logo_app_notificacion)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.menu_viaje))
                .setContentTitle(titulo)
                .setContentText(txt)
                .setAutoCancel(autoCancel)
                .setSound(defaultSoundUri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                ;
        NotificationManager notificationManager =
                (NotificationManager)MainActivity.sContext.getSystemService(MainActivity.sContext.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }

}
