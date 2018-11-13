package dream.logsys.com.logsysdream;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;

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
import java.util.Timer;
import java.util.TimerTask;
import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.db.ViajesDB;
import logsys.dream.com.mx.helpers.Viajes;
import logsys.dream.com.mx.models.Bitacora;
import logsys.dream.com.mx.models.Dream;
import logsys.dream.com.mx.models.Registro;
import logsys.dream.com.mx.models.ServiceData;
import logsys.dream.com.mx.startup.FrescoApplication;


public class SecondPlain extends Service {

    boolean a;
    // Metodo que queremos ejecutar en el servicio web
    private static final String Metodo = "getRegistroBitacora";
    // Namespace definido en el servicio web
    private static final String namespace = "http://app.mexamerik.com";
    // namespace + metodo
    private static final String accionSoap = "http://app.mexamerik.com/getRegistroBitacora";
    // Fichero de definicion del servcio web
    private static final String url = "http://tms.logsys.com.mx/bitacoradream/Service.asmx";

    private String fecha_fin_ultimo_evento;
    public LinkedList<ServiceData> data = new LinkedList<ServiceData>();
    static final int variableId = 49;
    static final String variableUnidad = "MA0096";
    static final int variableSolicitud = 5381869;
    public SoapPrimitive resultado;
    private String fecha;
    int pru;
    public String pa;//placas
    String alia;
    String str;
    public String format;//vigencia
    public String lice;//licencia
    public String hoursMinutes;//inactivo 1
    public String hoursMinutes2;//activo 1
    public String hoursMinutes3;//inactivo 2
    public String hoursMinutes4;//activo 2
    public String tipop; //tipo ya esta
    public String marcaa;//marca ya esta
    String modell;
    private Bitacora bitacora;
    private LinkedList<Dream> nuevo3;
    private int numeritos[];
    private int tamanos[];
    private String horitas[];
    ArrayList<Integer> cadena1;
    int pruebaUsuario;
    String pruebaAlias ="";
    int pruebaSolicitud;

    public int getoperador(){

        int request = 0;

        try {
            pru = globalVariable.getUsuario().getId();
            request = 1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return request;
    }

    public void getunidad(){
        try {
            alia = globalVariable.getUsuario().getUnidad();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public boolean consumirWS(){
        Boolean bandera=true;
        getoperador();
        getunidad();

        try {

            Viajes v = new Viajes();
            // Modelo el request
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("user_id", pru);
            //request.addProperty("user_id", 100107);
            String s=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date());
            //String fechaPrueba="2018/07/31 23:56:56";
            //Log.e("fecha",fechaPrueba);
            request.addProperty("alias_unidad", alia);

            request.addProperty("solicitud",v.getSolicitud());

            request.addProperty("date", s);
            //request.addProperty("date", fechaPrueba);
            //this.fecha=fechaPrueba; //A la variable de clase fecha se le asigna la fecha en la que se consumio el servicio.
            this.fecha=s;
            //request.addProperty("user_id", 2); // Paso parametros al WS

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



        } catch (Exception e) {
            Log.e("ERROR de variables ja", e.getMessage());
            bandera=false;
        }finally {

            return bandera;
            /*
             * El finally siempre se va a ejecutar, sin importar que se lanze
             * una exepction
             */
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Servicio creado!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override public void run() {

                try {
                new asyncPdf().execute("");

                } catch (Exception e) {
                }

            }

        };

        timer.schedule(task, 0, (60000 * 1));// In ms 60 secs is 60000
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        startService(new Intent(this,SecondPlain.class));
        Toast.makeText(this, "Servicio destruído!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class asyncPdf extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                if (consumirWS()) {
                    return "ok";
                } else {
                    return "error";
                }
            } catch (Exception e) {
                return  "error";
            }
        }

        @Override
        protected void onPostExecute(String result){
            if(result.equals("ok")) {
                try {
                    //Se cargara la informacion en todo
                    Log.e("ResultadoEnPostExecute", resultado.toString());
                    try {

                        JSONObject o = new JSONObject(resultado.toString());
                        JSONArray array = o.getJSONArray("list");
                        JSONObject driver = o.getJSONObject("driver");
                        JSONObject vehicle = o.getJSONObject("vehicle");
                        //tipo de carga
                        tipop = o.getString("Operation_Type_Alias");
                        Registro Registro_24, Registro_5_30;
                        Registro_24 = getRegistro(array, 0);
                        // LinkedList<Dream> nuevo=validarFechasEventos(Registro_24.getDreams());
                        LinkedList<Dream> nuevo = Registro_24.getDreams();
                        LinkedList<Dream> nuevo2 = validarFechasEventos(Registro_24.getDreams());
                        Registro_5_30 = getRegistro(array, 1);
                        //LinkedList<Dream> nuevo3 =getBanderaEvents(nuevo2);
                        nuevo3 = getBanderaEvents(nuevo2);
                        numeritos = getbarras(nuevo3);
                        horitas = getHoras(nuevo3);
                        String ffff = fecha.replace('/', '-');
                        getDriver(driver);
                        getCar(vehicle);

                        //modelo y marca
                        marcaa = o.getString("Marca_y_Modelo");
                        asyncdocuments c = new asyncdocuments();

                        c.execute();
                        createarch2();
                        //aqui ira el pdf
                        Log.e("JSONArray", "Todo correcto al crear el JSONArray");
                        Log.e("JSONArray2", array.toString());
                        /**
                         *
                         * Aqui se manejaria la logica para llenar el objeto de tipo bitacora.
                         */

                    } catch (Exception e) {

                        Log.e("JSONArrayError", e.getMessage());
                    }
                } catch (Exception e) {


                }
            } else{
                Log.e("ERROR", "Error al consumir el webService");
            }
        }

        private int[] getbarras(LinkedList<Dream> list){

            int bandera[] = new int[list.size()];

            for (int a=0;a<list.size();a++){

                bandera[a] = nuevo3.get(a).getBandera();

            }

            return bandera;

        }

        private Registro getRegistro(JSONArray array,int index){
            Registro registro=new Registro();
            Boolean bandera=true;
            try{
                JSONObject object=array.getJSONObject(index);
                JSONArray events=object.getJSONArray("listDreams");
                /**
                 * En el objeto events se almacenaran los eventos que se obtengan
                 * al consumir el webServices. si el index es 0 corresponde a los
                 * registros de las ultimas 24 horas, si el index es 1 el registro
                 * corresponde a las ultimas 5:30.
                 */
                LinkedList<Dream> list=new LinkedList<Dream>();
                Dream dream;
                Double inactivo;
                Double activo;
                Double inactivos;
                Double activos;
                int inactivoss;
                int activoss;
                switch(index){
                    case 0:
                        inactivo=object.getDouble("inactivo");
                        inactivos = inactivo%60;
                        inactivoss = (inactivo.intValue())/60;
                        activo=object.getDouble("activo");
                        activos = activo%60;
                        activoss = (activo.intValue())/60;
                        registro.setActivo(activo);
                        registro.setInactivo(inactivo);

                        hoursMinutes = String.format("%02d", inactivoss) + ":" + String.format("%02d", inactivos.intValue());
                        hoursMinutes2 = String.format("%02d", activoss) + ":" + String.format("%02d", activos.intValue());
                        registro.setDreams(this.getEvents(object.getJSONArray("listDreams")));
                        //Entra para obtener el registro de las 24 horas
                        break;
                    case 1:
                        //Entra para obtener el registro de las ultimas 5 horas y media
                        inactivo=object.getDouble("inactivo");
                        inactivos = inactivo%60;
                        inactivoss = (inactivo.intValue())/60;
                        activo=object.getDouble("activo");
                        activos = activo%60;
                        activoss = (activo.intValue())/60;
                        registro.setActivo(activo);
                        registro.setInactivo(inactivo);
                        hoursMinutes3 = String.format("%02d", inactivoss) + ":" + String.format("%02d", inactivos.intValue());
                        hoursMinutes4 = String.format("%02d", activoss) + ":" + String.format("%02d", activos.intValue());
                        registro.setDreams(this.getEvents(object.getJSONArray("listDreams")));

                        break;
                    default:
                        break;
                }

            }catch(JSONException e){
                /**
                 * Entrara en el catch cuando no se tengan registro de las  ultimas 5:30
                 */
                Date d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha_fin_ultimo_evento);
                Date b=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha.replace('/','-'));

                /**
                 * Convertimos la fecha_fin del ultimo evento de 24 horas
                 */
                //long ultimo=d.getTime();
                long diff=b.getTime()-d.getTime();
                long diff_seg=diff/1000l;//Diferencia en segundos
                long diff_min=diff_seg/60l;//Diferencia en minutos.
                if(diff_min>=330){
                    /**
                     * Si entra aqui implica que el operador
                     * no a registrado eventos de inactivo
                     * en mas de 5 horas y media.
                     */
                }else{
                    Double activo,inactivo;
                    int a2,i2;
                    activo=(double)(diff_min%60);
                    a2=activo.intValue()/60;

                    long i=330-diff_min;
                    inactivo=(double)(i%60);
                    i2=inactivo.intValue()/60;
                    String hoursMinutes3 = String.format("%02d", a2) + ":" + String.format("%02d", activo.intValue());
                    String hoursMinutes4 = String.format("%02d", i2) + ":" + String.format("%02d", inactivo.intValue());
                }
                Log.e("JsonE",e.getMessage());

            }finally{
                return registro;
            }

        }

        private void getDriver(JSONObject dri){
            try {
                str = dri.getString("vigencia");
                long time = Long.parseLong( str.substring(6, str.length() - 2 ));
                lice = dri.getString("License");

                Date date = new Date(time);
                format = new SimpleDateFormat("dd/MM/yyyy").format(date);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Driver_error",e.getMessage());
            }

        }

        private void getCar(JSONObject car){
            try {

                pa=car.getString("tag");

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Car_error",e.getMessage());
            }

        }

        private LinkedList<Dream>  getEvents(JSONArray arrayEvents) {

            LinkedList<Dream> list=new LinkedList<Dream>();
            Dream dream;
            JSONObject obj;
            String fecha_inicio;
            String fecha_fin;
            double diferencia;
            Log.e("size",String.valueOf(arrayEvents.length()));
            for(int index=0;index<arrayEvents.length();index++){

                try{
                    dream=new Dream();
                    obj=arrayEvents.getJSONObject(index);
                    fecha_inicio=obj.getString("fecha_inicio");
                    fecha_fin=obj.getString("fecha_fin");
                    diferencia=obj.getDouble("inactivo");
                    dream.setFecha_fin(fecha_fin);
                    dream.setFecha_inicio(fecha_inicio);
                    dream.setInactivo(diferencia);
                    dream.setBandera(0);
                    list.add(dream);
                    Log.e("Item",obj.toString());
                }catch (JSONException e){
                    Log.e("GetEventsJSONException",e.getMessage());
                }
            }
            Dream hours =new Dream();
            return list;
        }

        private LinkedList<Dream> validarFechasEventos(LinkedList<Dream> list){
            Dream first=list.getFirst();
            Dream last=list.getLast();
            fecha_fin_ultimo_evento=last.getFecha_fin();
            //Obtiene la fecha fin del ultimo evento

            Dream nuevo;
            /**
             * Se obtiene el primer y utlimo evento registrado.
             */
            Date firstFechaInicio=null;
            Date lastFechaFin=null;
            try {
                firstFechaInicio= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(first.getFecha_inicio());
                lastFechaFin=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(last.getFecha_fin());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //=new Date(first.getFecha_inicio());
            // =new Date(last.getFecha_fin());
            Date dateActual=new Date(fecha);
            Date menos24=restarHoras(dateActual,-24);

            if(firstFechaInicio.getTime()>menos24.getTime()){
                nuevo=new Dream();
                nuevo.setFecha_inicio(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(menos24));
                nuevo.setFecha_fin(first.getFecha_inicio());
                nuevo.setBandera(1);
                list.addFirst(nuevo);
            }else{
                first.setFecha_inicio(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(menos24));
            }

            if(lastFechaFin.getTime()<dateActual.getTime()){
                nuevo=new Dream();
                nuevo.setBandera(1);
                nuevo.setFecha_inicio(last.getFecha_fin());
                nuevo.setFecha_fin(fecha.replace('/','-'));
                list.addLast(nuevo);
            }else{
                last.setFecha_fin(fecha.replace('/','-'));

            }

            return list;

        }

        private Date restarHoras(Date fecha,int horas){
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.add(Calendar.HOUR,horas);
            return calendar.getTime();
        }

        private LinkedList<Dream> getBanderaEvents(LinkedList<Dream> list){
            LinkedList<Dream> nueva=new LinkedList<Dream>();
            /***
             * Obtenemos el iterador para el LinkedList
             * que contendra todos los eventos.
             */
            Dream actual;
            Dream siguiente;
            Dream nuevo;
            /**
             * El objeto llamado nuevo solo se agregara a la lista
             * si se encuentra un evento de activo, el cual corresponde
             * si se encuentra una diferencia entre la fecha_fin y la
             * fecha_inicio de un evento con otro.
             */
            for(int index=0;index<list.size();index++){
                actual=list.get(index);
                getDiferenciaFechas(actual);
                nueva.add(actual);
                /**
                 * Se agrega el evento actual.
                 */
                if(index+1<list.size()){
                    /***
                     * Entrara al if solo si la lista en el
                     * indice actual contiene otro registro
                     * si se incrementa el indice en una unidad.
                     */
                    siguiente=list.get(index+1);

                    if(!actual.getFecha_fin().equals(siguiente.fecha_inicio)){
                        /**
                         * Entrara al if cuando se tenga una diferencia
                         * entre la fecha_inicio y fecha_fin de dos eventos.
                         */
                        nuevo=new Dream();//Activo 1 e inactivo 0.
                        nuevo.setBandera(1);
                        nuevo.setFecha_inicio(actual.getFecha_fin());
                        nuevo.setFecha_fin(siguiente.getFecha_inicio());
                        getDiferenciaFechas(nuevo);
                        nueva.add(nuevo);
                        /**
                         * Se crea la nueva instancia.
                         */
                    }
                }
            }

            return nueva;
        }

        private void getDiferenciaFechas(Dream dream){

            Date inicio,fin;

            try{
                inicio=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dream.getFecha_inicio());
                fin=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dream.getFecha_fin());
                long diff=fin.getTime()-inicio.getTime();
                dream.setInactivo((diff/1000.00d)/60.00d);

            }catch(Exception e){
                e.printStackTrace();
            }

        }

        private String[] getHoras(LinkedList<Dream> list){

            String horas[] = new String[list.size()+1];

            for (int a=0;a<=list.size();a++){

                if (a==list.size()){

                    horas[a] = nuevo3.get(0).getFecha_inicio().substring(11,16);

                } else {

                    horas[a] = nuevo3.get(a).getFecha_inicio().substring(11,16);

                }


            }

            return horas;

        }

    }

    public String sh="TRANSPORTES MEX AMERI K, S.A. DE C.V.";
    public String shl="Calle Mariano Escobedo S/N Colonia: Mariano Escobedo, Tultitlán, Estado de México, C.P. 54946";
    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();
    ViajesDB db = new ViajesDB(LoginActivity.getAppContext());

    public void createarch2() throws DocumentException {

        try {
            viajeTO viaje = db.obtenerActual();
            Viajes viaj = new Viajes();
        } catch(Exception e) {

        }

        Viajes viajess = new Viajes();
        String nombre=globalVariable.getUsuario().getNombre();
        String uni=globalVariable.getUsuario().getUnidad();
        String fecha=new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new java.util.Date());
        String origen=ori;
        String destuno=desty;
       String solicitud=soli;
        template2 tem= new template2(getApplicationContext());
        tem.opendocument();
        tem.lines(fecha);
        tem.addMetaData("Bitacora","NOM 87","Mexapp");
        tem.addtitle("Bitacora NOM 87","MexApp","");
        tem.addparagraph(sh);
        tem.addparagraph(shl);
        tem.lines("Datos del Operado");
        tem.addparagraph("Operador  "+nombre);
        tem.addparagraph("licencia  "+lice+"  Vigencia  "+format);
        tem.lines("Datos de la Unidad");
        tem.addparagraph("unidad  "+uni+"  placas  "+pa);
        tem.addparagraph("Marca y Modelo  "+marcaa);
        tem.lines("Viaje Actual");
        tem.addparagraph("solicitud  "+solicitud+"  Tipo  "+tipop);
        tem.addparagraph("Origen  "+origen);
        tem.addparagraph("Destino   "+destuno);
        tem.lines("Descansos");
        tem.addparagraph("Ultimas 24:00hrs  "+hoursMinutes2+"  activo"+hoursMinutes+"  Descanso");
        tem.addparagraph("Ultimas 05:30hrs  "+hoursMinutes4+"  activo"+hoursMinutes3+"  Descanso");
        tem.lines("Detalles");
        String e[] = getEstado();
        for (int i =0;i<e.length;i++){

            tem.addparagraph(horitas[i]+" - "+horitas[i+1]+" "+e[i]);
            System.out.println(horitas[i]+" - "+horitas[i+1]+" "+e[i]);

        }

        tem.closedocument();

    }

    public String[] getEstado() {

        String estado[] = new String[numeritos.length];

        for (int i=0;i<numeritos.length;i++){

            if (numeritos[i] == 0){

                estado[i] = "Decanso";


            } else {

                estado[i] = "Activo";

            }

        }

        return estado;

    }

    String soli,ori,desty;
    private static final String Metodo1 = "GetViajesActual";
    // Namespace definido en el servicio web
    private static final String namespace1 = "http://tempuri.org/";
    // namespace + metodo
    private static final String accionSoap1 = "http://tempuri.org/GetViajesActual";
    // Fichero de definicion del servcio web
    private static final String url1 = "https://app.mexamerik.com/Mexapp_viajes/Viajes.asmx?";
    public SoapPrimitive resultado1;



    public boolean consumirWS1(){

        Boolean bandera=true;
        try {

            String s=new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date());
            fecha=s;

            SoapObject request = new SoapObject(namespace1, Metodo1);
            request.addProperty("fecha", fecha);
            request.addProperty("ID_Usuario", pru);


            // Modelo el Sobre
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            sobre.dotNet = true;

            sobre.setOutputSoapObject(request);

            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(url1);

            // Llamada
            transporte.call(accionSoap1, sobre);

            // Resultado
            resultado1= (SoapPrimitive) sobre.getResponse();
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

            if (consumirWS1()) {
                return "ok";
            } else
                return "error";
        }


        private void getCar(JSONObject car){
            String format,form2,for3;
            // scpo TextView header,solicitud,shitment,cpo,cliente,origin,destino,dir,ocita,dirdest,citdes;

            try {



                soli=car.getString("Id");
                ori=car.getString("Origen");
                desty=car.getString("Destino");

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

}