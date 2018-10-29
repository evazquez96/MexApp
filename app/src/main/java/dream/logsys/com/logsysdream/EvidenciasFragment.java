package dream.logsys.com.logsysdream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import logsys.dream.com.mx.contracts.Dream;
import logsys.dream.com.mx.ws.Repos.Dashboard_Ws;
import logsys.dream.com.mx.ws.Repos.Evidencias_Ws;
import logsys.dream.com.mx.ws.Ws_Utils;
import logsys.dream.com.mx.contracts.EvidenciasContract;
import utils.GsonHelper;
import utils.Evidencias;
import utils.internetConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EvidenciasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EvidenciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvidenciasFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int REQUEST_CODE = 99;

    //Elementos en pantalla
    TextView connectedNetwork;
    Spinner spinnerSolicitudesActivas;
    TextView currentDateText;
    private Button scanButton;
    private Button cameraButton;
    private Button mediaButton;
    private ImageView scannedImageView;

    //Variables de sesiòn
    Evidencias evidencias;
    Ws_Utils ws_utils = new Ws_Utils();
    ArrayList<String> solicitudesArray;
    JSONArray jsonarraysolicitudes;



    @Override
    int get_ViewResourceId() {
        return R.layout.fragment_evidencias;
    }

    @Override
    String getNombreModulo()
    {
        return "Evidencias";
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        progress.hide();
    }

    public EvidenciasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvidenciasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvidenciasFragment newInstance(String param1, String param2) {
        EvidenciasFragment fragment = new EvidenciasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
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

    static TabLayout tabLayout;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View _view = getActivity().findViewById(R.id.drawer_layout);

        tabLayout = _view.findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);

        Log.d("TESTING APP", "Entrando al onViewCreated.................");

        //Conexion a internet
        /*
        internetConnection IC = new internetConnection(this.getContext());
        connectedNetwork = _view.findViewById(R.id.connectionText);
        if (IC.isConnected()) {
            connectedNetwork.setBackgroundColor(0xFF00CC00);
            connectedNetwork.setText("Está conectado a internet");
        } else {
            connectedNetwork.setText("No cuenta con conexón a internet");
        }
*/

        //Fecha de la evidencia
        currentDateText = _view.findViewById(R.id.currentDate);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = sdf.format(date);
        currentDateText.setEnabled(false);
        currentDateText.setText(dateString);


        //Obteniendo solicitudes
        spinnerSolicitudesActivas = _view.findViewById(R.id.solicitudesActivasSpinner);
        String url_select = "/Sueno/Sueno.svc/obtenerSolicitudes";
        String result = "";
        result = ws_utils.Get(url_select);
        Log.d("TESTING APP", "Resultado consulta PAquito................." + result);

        Evidencias_Ws eWs = new Evidencias_Ws();

        Collection<EvidenciasContract> enums = eWs.obTenerSolicitudes();
        EvidenciasContract currentEvidencia;

        if(result!=null)
        {
            spinnerSolicitudesActivas.setEnabled(true);
            Log.d("TESTING APP", "Entrando a RESULT EVIDENCIAS.................");
            solicitudesArray = new ArrayList<>();
            spinnerSolicitudesActivas = _view.findViewById(R.id.solicitudesActivasSpinner);
            try {
                for(EvidenciasContract ec : enums){
                    solicitudesArray.add(String.valueOf(ec.getNoSolicitud()).concat("||").concat(String.valueOf(ec.getOrdenVenta()).concat("||").concat(ec.getCliente())));
                }

            } catch (Exception e) {
                Log.d("Excepción JSON Object", e.getLocalizedMessage());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_spinner_item, solicitudesArray);

            dataAdapter.setDropDownViewResource
                    (android.R.layout.simple_spinner_dropdown_item);

            spinnerSolicitudesActivas.setAdapter(dataAdapter);

            // Spinner item selection Listener
            addListenerOnSpinnerItemSelection();

            init();
        }
        else {

            spinnerSolicitudesActivas.setEnabled(false);

        }

    }

    private void init() {
        View _view = getActivity().findViewById(R.id.drawer_layout);

        Log.d("TESTING APP", "Inicializando.................");
        scanButton = _view.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.SEND_DATA));
        scanButton.setEnabled(false);
        cameraButton = _view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA));
        mediaButton = _view.findViewById(R.id.mediaButton);
        mediaButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_MEDIA));
        scannedImageView = _view.findViewById(R.id.scannedImage);

    }



    private void addListenerOnSpinnerItemSelection() {

        //spinnerSolicitudesActivas.setOnItemClickListener(new CustomOnItemSelectedListener());
    }

    private class ScanButtonClickListener implements View.OnClickListener {

        private int preference;

        public ScanButtonClickListener(int preference) {
            Log.d("TESTING APP", "Boton presionado................."+ preference);

            this.preference = preference;
        }

        public ScanButtonClickListener() {

            Log.d("TESTING APP", "Boton de escaneo presionado.................");

            //new HttpAsyncTask22().execute("http://tms.logsys.com.mx/Android_Pruebas/Sueno/Sueno.svc/guardarArchivo");

            //String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);

        }

        @Override
        public void onClick(View v) {
            if(preference == 66){
                new HttpAsyncTask22().execute("http://tms.logsys.com.mx/Android_Pruebas/Sueno/Sueno.svc/guardarArchivo");
            }else{
                startScan(preference);
            }



        }

        protected void startScan(int preference) {
            Intent intent = new Intent(getActivity(), ScanActivity.class);
            intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
            startActivityForResult(intent, REQUEST_CODE);
        }


        private class HttpAsyncTask22 extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... urls) {

                Log.d("TESTING APP", "Entrando a async task.................");


                evidencias.setFileExtension(".JPEG");
                evidencias.setFileName("evidencia001");

                return POST(urls[0],evidencias);
            }
            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getActivity().getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
                //new DownloadJSON().execute();
            }
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;
        }

        public String POST(String url, Evidencias evidencias){
            InputStream inputStream = null;
            String result = "";
            try {
                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(url);

                String json = "";

                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("extension", evidencias.getFileExtension());
                jsonObject.accumulate("pFileBytes", evidencias.getBase64Image());
                jsonObject.accumulate("pFileName", evidencias.getFileName());

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();

                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);

                // 6. set httpPost Entity
                httpPost.setEntity(se);


                // 7. Set some headers to inform server about the type of the content   
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                Log.d("TESTING APP....", "Texto enviadooooo--------"+httpPost.getRequestLine());
                Log.d("TESTING APP....", "Texto enviadooooo--------"+httpPost.getParams());


                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);

                // 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();


                // 10. convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            Log.d("InputStream","Texto de respuesta" + result);
            // 11. return result
            return result;
        }
    }

}
