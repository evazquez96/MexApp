package logsys.dream.com.mx.ws;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.startup.FrescoApplication;

/**
 * Created by JUAHERNA on 2/21/2018.
 */

public class Ws_Utils {

    //hacerla dinámica, leerla de algun archivo de configuración
    private static String url_ServidorDreams = "http://app.mexamerik.com/Dream/";
    private static String url_ServidorErp = "http://app.mexamerik.com/Dream/";
    //private static String url_ServidorDreams = "http://app.mexamerik.com/Dream_Pruebas/";
    //private static String url_ServidorErp = "http://app.mexamerik.com/Dream_Pruebas/";
    //private static String url_Servidor = "http://192.168.1.149:17245/";
    //private static String url_ServidorDreams = "localhost:1433";
    //private static String url_ServidorErp = "localhost:1433";

    public enum ServerUrl {
        Drems,
        Evidencias,
    }

    protected FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();

    public String Post(String urlServicio, JSONObject json) {
        return Post(ServerUrl.Drems,urlServicio,json);
    }

        public String Post(ServerUrl serverUrl,String urlServicio, JSONObject json) {

        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        String url_Servidor = url_ServidorDreams;
        switch (serverUrl)
        {
            case Evidencias: url_Servidor = url_ServidorErp;
            default: break;
        }
        try {
            URL url = new URL(url_Servidor + urlServicio);
            Log.d("WS_Utils",":::::::::::::::::POST:::::" + url_Servidor + urlServicio);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(20000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(20000);
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");

            streamWriter.write(json.toString());
            streamWriter.flush();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();
                Log.d("::::::::::POST Response", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("::::::::::POST Response", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception) {
            Log.e("test", exception.toString());
            exception.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String Get(ServerUrl serverUrl,String urlServicio) {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        String url_Servidor = url_ServidorDreams;
        switch (serverUrl)
        {
            case Evidencias: url_Servidor = url_ServidorErp;
            default: break;
        }

        try {
            URL url = new URL(url_Servidor + urlServicio);
            Log.d("WS_Utils",":::::::::::::::::GET:::::" + url_Servidor + urlServicio);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();
                Log.d(":::::::::::GET Response", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e(":::::::::::GET Response", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception) {
            Log.e("test", exception.toString());
            exception.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String Get(String urlServicio) {
        return Get(ServerUrl.Drems,urlServicio);


        /*
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(url_Servidor + urlServicio);
            Log.d("WS_Utils",":::::::::::::::::GET:::::" + url_Servidor + urlServicio);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();
                Log.d(":::::::::::GET Response", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e(":::::::::::GET Response", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception) {
            Log.e("test", exception.toString());
            exception.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        */
    }

}
