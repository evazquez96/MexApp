package dream.logsys.com.logsysdream;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.media.Ringtone;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import logsys.dream.com.mx.contracts.Dream;
import logsys.dream.com.mx.contracts.Dream_Dashboard;
import logsys.dream.com.mx.contracts.dream_indicador;
import logsys.dream.com.mx.db.DreamDB;
import logsys.dream.com.mx.db.ParamsDB;
import logsys.dream.com.mx.repos.RepDreamDashboard;
import logsys.dream.com.mx.utils.DateTimePicker;
import logsys.dream.com.mx.ws.Repos.Dashboard_Ws;
import logsys.dream.com.mx.ws.Repos.LoginRepo_Ws;
import utils.GsonHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DreamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DreamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DreamFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PostTaskDashboard DashboardTask = null;
    private PostTaskSueno mAuthTask1 = null;

    private PostTaskSuenoAsincrono mAuthTaskSendDream = null;
    private static final int WAKE_UP_CALL = 0;


    private Chronometer chrono_ui;

    private int horas;
    private int minutos;
    private  long fin_milis;

    //private long _____inicio_id;
    long sleepMilliseconds;


    private Handler handler;

    private Runnable wakeUpCall = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(WAKE_UP_CALL);
        }
    };

    View view = null;
    static TabLayout tabLayout;

    public DreamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DreamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DreamFragment newInstance(String param1, String param2) {
        DreamFragment fragment = new DreamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ocultarlayout();


        View _view = getActivity().findViewById(R.id.drawer_layout);
        tabLayout = _view.findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);

        mostrarProgress("Conectando con servidor remoto para actualizar, por favor espere");
        iniciarControles();

        reanudarSuenoCorriendo();

        configurar_visibilidad();

        inicializarHilo();

        ImageView button = getView().findViewById(R.id.dd_b_iniciarsueno);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chrono_ui.getVisibility() == View.VISIBLE)
                    btn_finalizar_sueno();
                else
                    btn_iniciar_sueno();
            }
        });

    }

@Override
int get_ViewResourceId() {
    return R.layout.fragment_dream;
}

    @Override
    String getNombreModulo()
    {
        return "Sueño";
    }

    private void inicarAsincrono()
    {
        mAuthTaskSendDream = new PostTaskSuenoAsincrono();
        mAuthTaskSendDream.execute((Void) null);
    }

    protected boolean  is_logued()
    {
        try {
            DreamDB te = new DreamDB((globalVariable));
            long inicio_id = te.obtenerIdUltimSueno(getUserId());
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
            ParamsDB dbParams = new ParamsDB(globalVariable);

            int logueado = lws.islogued(getUserId(), inicio_id,getNumeroTelefono(),getVersionApp());
            if (logueado !=0) {
                if(LoginRepo_Ws.s_iniciado && logueado ==2)
                {
                    LoginRepo_Ws.s_iniciado=false;
                    configurar_visibilidad();
                    //dbParams.deleteParam(2);
                    //dbParams.deleteParam(1);
                    te.depurarRegistros(getUserId());
                }
                return true;
            }
            LoginRepo_Ws.s_iniciado=false;
            configurar_visibilidad();

            te.depurarRegistros(getUserId());

            globalVariable.setUsuario(null);

            return false;
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return true;
        }
    }

    private void ocultarlayout()
    {
        ScrollView a = getView().findViewById(R.id.a_dream_dashboard_tst);
        a.setVisibility(View.GONE);
    }

    private  void mostrarLayout()
    {
        ScrollView a = getActivity().findViewById(R.id.a_dream_dashboard_tst);
        a.setVisibility(View.VISIBLE);
    }

    private void sincronizarRegistros()
    {
        try {
            DashboardTask = new PostTaskDashboard();
            DashboardTask.execute((Void) null);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void inicializarIndicadores(final Dream_Dashboard dash)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                TextView tvAux;
                dream_indicador indicadorAux;
                ImageView imAux = getActivity().findViewById(R.id.dd_ib_sueno);
                //ImageButton imAux = ((ImageButton) getActivity().findViewById(R.id.dd_ib_sueno));
                ((TextView) getActivity().findViewById(R.id.dd_tv_nombre)).setText(dash.getNombre());

                indicadorAux = dash.getI_ultimas_48();
                tvAux = getActivity().findViewById(R.id.dd_tv_48horas);
                tvAux.setText("Sueño a 48 - " + String.valueOf(indicadorAux.getTiempo()));
                tvAux.setBackgroundResource(indicadorAux.get_Color());

                //a 24 horas
                indicadorAux = dash.getI_ultimas_24();
                tvAux = getActivity().findViewById(R.id.dd_tv_24horas);
                tvAux.setText("Sueño a 24 - " + String.valueOf(indicadorAux.getTiempo()));
                tvAux.setBackgroundResource(indicadorAux.get_Color());
                imAux.setImageResource(indicadorAux.obtenerImagen());

                indicadorAux = dash.getI_semanal();
                tvAux = getActivity().findViewById(R.id.dd_tv_semana);
                tvAux.setText("Sueño Ult Sem - " + String.valueOf(indicadorAux.getTiempo()));
                tvAux.setBackgroundResource(indicadorAux.get_Color());

                RatingBar ratingBar = getActivity().findViewById(R.id.dd_rb_rating);
                ratingBar.setRating(dash.getCalificacion());
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_ATOP);

                ((TextView) getActivity().findViewById(R.id.dd_tv_ultsueno)).setText("Último sueño - " + dash.getUltimas_horas());
                ((TextView) getActivity().findViewById(R.id.dd_tv_ultsuenofecha)).setText(dash.getUltimo_sueno());
            }
        });
    }
/*
    @Override
    public void onBackPressed() {
        try {
            new AlertDialog.Builder(this)
                    .setTitle("Salir de la aplicación?")
                    .setMessage("¿Estás seguro de salir de la aplicación?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            salir = true;
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startMain);
                        }
                    })

                    .setNegativeButton(android.R.string.no, null).show();

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(salir)
            super.onBackPressed();
    }
*/
    @Override
    public void onRefresh() {
        super.onRefresh();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                DashboardTask = new PostTaskDashboard();
                DashboardTask.execute((Void) null);
            }
        },2000);
    }

    public class PostTaskDashboard extends AsyncTask<Void, Void, Boolean> {

        PostTaskDashboard() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            RepDreamDashboard a = new RepDreamDashboard();
            inicializarIndicadores(a.obtenerDashBoard());

            return true ;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mostrarLayout();
            DashboardTask = null;
            progress.hide();
        }

        @Override
        protected void onCancelled() {
            DashboardTask = null;
        }
    }



    private void configurar_visibilidad()
    {
        try {
            ((ImageView) getActivity().findViewById(R.id.dd_b_iniciarsueno)).setImageResource(LoginRepo_Ws.s_iniciado == false ? R.drawable.start : R.drawable.stop);
            chrono_ui.setVisibility(LoginRepo_Ws.s_iniciado == true ? View.VISIBLE : View.INVISIBLE);
        }catch (Exception ex)
        {
        }
    }

    private void reanudarSuenoCorriendo()
    {
        ParamsDB paramsdb = new ParamsDB(globalVariable);
        String param1 = "";
        try {
            sincronizarRegistros();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        String param = null;
        try {
            param= paramsdb.get_Parametro("1",String.valueOf(getUserId()));

            if(param == null)
                return;
        } catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }

        DreamDB te = new DreamDB(globalVariable);
        //_____inicio_id = te.obtenerIdUltimSueno(getUserId());


        param1 = paramsdb.get_Parametro("2",String.valueOf(getUserId()));
        sleepMilliseconds =0;
        if(param1!=null) {
            sleepMilliseconds = Long.parseLong(param1);
            minutos = (int) ((sleepMilliseconds / (1000*60)) % 60);
            horas  = (int) ((sleepMilliseconds / (1000*60*60)) % 24);
        }
        this.iniciarSueno(Long.parseLong(param));
    }

    private void iniciarSueno(long startMilliseconds)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startMilliseconds);
        long milliseconds =  System.currentTimeMillis() - startMilliseconds; //f_fin - f_ini;
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

        milliseconds = SystemClock.elapsedRealtime() - (seconds * 1000) - (minutes * 1000*60) - (hours*1000*60*60);


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");


        if(sleepMilliseconds>0)
            ((TextView)getActivity().findViewById(R.id.dd_tv_tiempo_dormir)).setText("Dormir: " + String.format("%02d",horas) + ":" + String.format("%02d",minutos));


        chrono_ui.setBase(milliseconds);
        chrono_ui.setVisibility(View.VISIBLE);
        chrono_ui.start();

        LoginRepo_Ws.s_iniciado = true;
        configurar_visibilidad();

        if(sleepMilliseconds>0) {
            if(handler!=null)
                handler.postDelayed(wakeUpCall, sleepMilliseconds);
        }
    }

    private void _iniciarSueno(long startMiliseconds)
    {
        showDateTimeDialog(startMiliseconds);
    }

    public void btn_iniciar_sueno()
    {
        mostrarProgress("Conectando con servidor remoto, por favor espere");
        inicarAsincrono();
    }

    public  void iniciarSueno()
    {
        _iniciarSueno(System.currentTimeMillis());
        /* Pablo solicita quitar esta pregunta
        new AlertDialog.Builder(getActivity())
                .setTitle("Iniciar sueño")
                .setMessage("¿Cuánto tiempo deseas dormir?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        _iniciarSueno(System.currentTimeMillis());
                    }})
                .setNegativeButton(android.R.string.no, null).show();
                */
    }


    private long get_FechaInicioCronometro()
    {
        return System.currentTimeMillis();
    }


    public  void btn_finalizar_sueno()
    {
        new AlertDialog.Builder(getActivity())
                .setTitle("Detener sueño")
                .setMessage("¿Estás seguro de detener sueño?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        finalizarSueno();
                    }})

                .setNegativeButton(android.R.string.no, null).show();
    }

    private void finalizarSueno() {
        // Create the dialog
        final Dialog mDateTimeDialog = new Dialog(getActivity());
        // Inflate the root layout
        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.date_time_dialog, null);
        // Grab widget instance
        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
        // Check is system is set to use 24h time (this doesn't seem to work as expected though)
        final String timeS = android.provider.Settings.System.getString(getActivity().getContentResolver(), android.provider.Settings.System.TIME_12_24);
        final boolean is24h = !(timeS == null || timeS.equals("12"));

        final TextView _date;
        final TextView _time;

        mDateTimeDialogView.findViewById(R.id.SetDateTime).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mDateTimePicker.clearFocus();



                horas = mDateTimePicker.get(Calendar.HOUR_OF_DAY);
                minutos = mDateTimePicker.get(Calendar.MINUTE);

                fin_milis = mDateTimePicker.getDateTimeMillis();

                long fechatoday = get_FechaInicioCronometro();
                if(fin_milis>= fechatoday)
                {
                    Toast.makeText(getActivity(), "La fecha y hora de fin, no puede ser mayor a la actual", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParamsDB paramsdb = new ParamsDB(globalVariable);

                //added
                String param = paramsdb.get_Parametro("1",String.valueOf(getUserId()));
                if(param == null)
                    return;
                long f_ini = Long.parseLong(param);

                if(f_ini>= fin_milis)
                {
                    Toast.makeText(getActivity(), "La fecha y hora de fin, no puede ser menor a la de inicio", Toast.LENGTH_SHORT).show();
                    return;
                }

                finalizar_Cronometro_1();

                mDateTimeDialog.dismiss();
            }
        });


        // Cancel the dialog when the "Cancel" button is clicked
        mDateTimeDialogView.findViewById(R.id.CancelDialog).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDateTimeDialog.cancel();
            }
        });

        // Reset Date and Time pickers when the "Reset" button is clicked
        /*
        mDateTimeDialogView.findViewById(R.id.ResetDateTime).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDateTimePicker.reset();
            }
        });
*/
        // Setup TimePicker
        mDateTimePicker.setIs24HourView(true);
        mDateTimePicker.reset();
        // No title on the dialog window
        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set the dialog content view
        mDateTimeDialog.setContentView(mDateTimeDialogView);
        // Display the dialog

        //pacomDateTimeDialogView.findViewById(R.id.SwitchToTime).callOnClick();

        RadioGroup radioGroupID = (RadioGroup)mDateTimeDialogView.findViewById(R.id.radio_time);
        radioGroupID.setVisibility(View.INVISIBLE);
        mDateTimeDialog.show();

    }

    private void guardadoExitosamente()
    {
        Toast.makeText(getActivity(), "Se sincronizo el registro correctamente", Toast.LENGTH_SHORT).show();
        chrono_ui.setVisibility(View.INVISIBLE);
        chrono_ui.stop();
    }

    private void errorEnvio()
    {
        Toast.makeText(getActivity(), "No se pudo conectar con el servidor, por favor intentarlo más tarde", Toast.LENGTH_LONG).show();
    }


    /*          clase para postbacks    */
    public class PostTaskSueno extends AsyncTask<Void, Void, Boolean> {

        PostTaskSueno() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            minutos = horas = 0;

            DreamDB db = new DreamDB(globalVariable);
            ParamsDB paramsdb = new ParamsDB(globalVariable);

            String param = paramsdb.get_Parametro("1",String.valueOf(getUserId()));
            if(param == null)
                return false;
            long f_fin  = fin_milis; //System.currentTimeMillis();

            Dream sueno = new Dream();


            Dashboard_Ws r = new Dashboard_Ws();

            String result = "";

            //Log.d("Paco","Inicio Id::::::, Fin " +  _____inicio_id);
            //sueno = db.obtenerSueno(_____inicio_id);


            try {
                param= paramsdb.get_Parametro("3",String.valueOf(getUserId()));

                if(param == null)
                    return false;
            } catch(Exception ex)
            {
                ex.printStackTrace();
                return false;
            }


            sueno = db.obtenerSuenoSQL(Integer.parseInt(param));

try {
    //System.out.println("***************Sueño" + sueno.getId()  + " ," + sueno.getSql_id() + "  - "+  _____inicio_id) ;
}catch (Exception ex)          {}

            Log.d("Paco","Inicio Id::::::, Fin " +sueno);
            sueno.setFechaFin(new Date(f_fin));
            result = r.enviarSueno(sueno);

            if(result==null)
                return false;
            return true ;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask1 = null;
            configurar_visibilidad();
            progress.hide();
            if (success) {
                guardadoExitosamente();

                sincronizarRegistros();

                //Added 20 -12
                ParamsDB dbParams = new ParamsDB(globalVariable);
                LoginRepo_Ws.s_iniciado=false;
                configurar_visibilidad();
                dbParams.deleteParam(2);
                dbParams.deleteParam(1);
                dbParams.deleteParam(3);
                //Added 20 -12

                try {
                    Vibrator mVibrator = BaseFragment.mVibrator;
                    if (mVibrator != null) {
                        mVibrator.cancel();
                    }
                } catch(Exception ex) {

                }

                try {
                    if(ring!=null)
                        ring.stop();
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            } else {

                errorEnvio();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask1 = null;
        }
    }

    private  void finalizar_Cronometro_1()
    {
        DreamDB db = new DreamDB(globalVariable);
        //detener alarma
        if(r!=null)
            r.stop();

        /*Finalizar sueño progress paco*/
        mostrarProgress("Conectando con servidor remoto, por favor espere");

        mAuthTask1 = new PostTaskSueno();
        mAuthTask1.execute((Void) null);
    }

    private void iniciarControles()
    {
        chrono_ui = getActivity().findViewById(R.id.chronometer2);
    }

    /*          Vibrar          */

    private void inicializarHilo() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == WAKE_UP_CALL) {
                    // change message, your code goes here...
                    // let's start the timer again
                    if (LoginRepo_Ws.s_iniciado == false)
                        return true;
                    try {
                        enviarNotificacion("Se alcanzo el tiempo de sueño programado",2,"Sueño alcanzado",true,"texto",true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        }
        );
    }


    public static Ringtone r = null;

    public void showDateTimeDialog(final long startMiliseconds) {
        //showDialog(TIME_DIALOG_ID);

        // Create the dialog
        final Dialog mDateTimeDialog = new Dialog(getActivity());


        //DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
        // AlertDialog.THEME_HOLO_DARK,this,year,month,day);
        //android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,android.R.style.Theme_Material_Light_Dialog_Alert )

        // Inflate the root layout
        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.date_time_dialog, null);
        // Grab widget instance
        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
        // Check is system is set to use 24h time (this doesn't seem to work as expected though)
        final String timeS = android.provider.Settings.System.getString(getActivity().getContentResolver(), android.provider.Settings.System.TIME_12_24);

        mDateTimeDialogView.findViewById(R.id.SetDateTime).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                RadioGroup radioGroupID = (RadioGroup)mDateTimeDialogView.findViewById(R.id.radio_time);
                radioGroupID.setVisibility(View.VISIBLE);
                int radioButtonID = radioGroupID.getCheckedRadioButtonId();

                if(radioButtonID==-1) {
                    Toast.makeText(getActivity(), "Debe seleccionar Dormir Hasta ó Dormir en Tiempo", Toast.LENGTH_SHORT).show();
                    return;
                }
                View radioButton = radioGroupID.findViewById(radioButtonID);
                int idx = radioGroupID.indexOfChild(radioButton);
                if(idx ==0)
                {
                    Time today = new Time(Time.getCurrentTimezone());
                    today.setToNow();

                    horas = mDateTimePicker.get(Calendar.HOUR_OF_DAY);
                    minutos = mDateTimePicker.get(Calendar.MINUTE);

                    if(today.minute> minutos)
                        horas--;

                    if(horas< today.hour) {
                        horas+=24;
                    }
                    horas = horas - today.hour;
                    if(minutos> today.minute)
                        minutos = minutos- today.minute;
                    else
                        minutos = (60 -today.minute) + minutos;
                }
                else
                {
                    horas = mDateTimePicker.get(Calendar.HOUR_OF_DAY);
                    minutos = mDateTimePicker.get(Calendar.MINUTE);
                }
                mDateTimePicker.clearFocus();

                if(horas> 8 || (horas==8 && minutos>0)) {
                    Toast.makeText(getActivity(), "No se permite programar sueños mayores a 8 horas", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    ((TextView) getActivity().findViewById(R.id.dd_tv_tiempo_dormir)).setText("Dormir: " + String.format("%02d", horas) + ":" + String.format("%02d", minutos));

                    sleepMilliseconds = ((horas * (1000 * 60 * 60))) + ((minutos * (1000 * 60)));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                mDateTimeDialog.dismiss();

                ParamsDB paramsdb = new ParamsDB(globalVariable);

                boolean existe = paramsdb.existParam("1", getUserId());

                if (!existe)
                    paramsdb.insertParam("1", String.valueOf(getUserId()), "fecha_inicio", String.valueOf(startMiliseconds));
                else
                    paramsdb.updateParam(1, String.valueOf(getUserId()), String.valueOf(startMiliseconds), "fecha_inicio");

                existe = paramsdb.existParam("2", getUserId());

                if (sleepMilliseconds > 0) {
                    if (!existe)
                        paramsdb.insertParam("2", String.valueOf(getUserId()), "tiempo_dormir", String.valueOf(sleepMilliseconds));
                    else
                        paramsdb.updateParam(2, String.valueOf(getUserId()), String.valueOf(sleepMilliseconds), "tiempo_dormir");
                }

                DreamDB db = new DreamDB(globalVariable);

                Dream sueno = new Dream();
                sueno.setFechaInicio(new Date(startMiliseconds));


                sueno.setComentarios("Sueño Mex App");
                sueno.setUsuarioId(getUserId());

                long registro_id= db.insertarSueno(sueno);
                //_____inicio_id =  db.obtenerSueno(registro_id).getSql_id(); //sueno.getSql_id();


                //Log.d("Paco","Inicio Id::::::, iniciando " +  _____inicio_id);

                sueno = db.obtenerSueno(registro_id);


                String result = "";
                if(registro_id!=-1)
                {
                    Dashboard_Ws r = new Dashboard_Ws();
                    sueno.setId(0);
                    result = r.enviarSueno(sueno);

                    if(result!=null)
                    {
                        sueno = new GsonHelper().getGson().fromJson(result.toString(), Dream.class);

                        sueno.setEnviado(1);

                        //System.out.println("***************Sueño" + sueno.getId()  + " ," + sueno.getSql_id() + "  - " + _____inicio_id) ;
                        System.out.println(result.toString());

                        db.actualizarSueno(sueno);

                        existe = paramsdb.existParam("3", getUserId());

                        if (!existe)
                            paramsdb.insertParam("3", String.valueOf(getUserId()), "id_sql", String.valueOf(sueno.getId()));
                        else
                            paramsdb.updateParam(3, String.valueOf(getUserId()), String.valueOf(sueno.getId()), "id_sql");

                    }
                    else {

                    }
                }
                iniciarSueno(System.currentTimeMillis());
            }
        });


        // Cancel the dialog when the "Cancel" button is clicked
        mDateTimeDialogView.findViewById(R.id.CancelDialog).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDateTimeDialog.cancel();
            }
        });

        // Reset Date and Time pickers when the "Reset" button is clicked
        /*
        ((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDateTimePicker.reset();
            }
        });
        */

        // Setup TimePicker
        mDateTimePicker.setIs24HourView(true);
        mDateTimePicker.reset();
        // No title on the dialog window
        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set the dialog content view
        mDateTimeDialog.setContentView(mDateTimeDialogView);
        // Display the dialog

        //pacomDateTimeDialogView.findViewById(R.id.SwitchToTime).callOnClick();
        //pacomDateTimeDialogView.findViewById(R.id.SwitchToDate).setVisibility(View.INVISIBLE);

        if(horas>0 || minutos>0)
        {
            mDateTimePicker.updateTime(horas,minutos);
        }
        else
            mDateTimePicker.updateTime(0,0);

        mDateTimeDialog.show();
    }


    private TimePicker timePicker1;
    static final int TIME_DIALOG_ID = 999;

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(getActivity(),
                        timePickerListener, 0, 0,false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    /*tvDisplayTime.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));
*/
                    // set current time into timepicker
                    timePicker1.setCurrentHour(0);
                    timePicker1.setCurrentMinute(0);

                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private static NotificationCompat.Builder buildNotificationCommon(Context _context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context)
                .setWhen(System.currentTimeMillis());
        //Vibration
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
        builder.setLights(Color.RED, 3000, 3000);

        //Ton
        //builder.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));
        return builder;
    }

    public class PostTaskSuenoAsincrono extends AsyncTask<Void, Void, Boolean> {

        private String resultadoServicio;

        PostTaskSuenoAsincrono() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //LoginRepo_Ws.s_iniciado = false;
            LoginRepo_Ws lws = new LoginRepo_Ws();

            int logueado = lws.islogued(getUserId(), -1,getNumeroTelefono(),getVersionApp());
            if(logueado== 4)
            {
                resultadoServicio = "No se puede iniciar sueño, hay un sueño iniciado manualmente";
                return false;
            }

            logueado = lws.puedeIniciarSueno(getUserId(),getNumeroTelefono());

            if(logueado== -1)
            {
                resultadoServicio = "No se puede establecer conexión con el servidor, no se permite capturar sueño";
                return false;
            }
            else if((logueado !=3))
            {
                resultadoServicio = "La unidad no se encuentra en estado permitido para iniciar sueño";
                return false;
            }
            return  true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTaskSendDream = null;
            //showProgress(false);
            configurar_visibilidad();
            progress.hide();


            if(is_logued()==false)
                return;

            if (success) {
                Toast.makeText(getActivity(), "Conectado Exitósamente", Toast.LENGTH_SHORT).show();
                iniciarSueno();
            } else {
                Toast.makeText(getActivity(), resultadoServicio, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTaskSendDream = null;
        }
    }

}
