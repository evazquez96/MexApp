package logsys.dream.com.mx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import logsys.dream.com.mx.contracts.viaje.to.viajeTO;

/**
 * Created by JUAHERNA on 2/23/2018.
 */

public class ViajesDB extends DBHelper {

    public static final String TABLE_NAME = "Viajes";

    public ViajesDB(Context context)
    {
        super(context);
    }

    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db,oldVersion,newVersion);
    }

    public long guardarViaje(viajeTO viaje)
    {
        //Actualizar estatus de viajes anteriores

        this.deleteQuery(this.TABLE_NAME,"id=?",new String[] {String.valueOf(viaje.getId())});

        ContentValues contentValues = new ContentValues();
        contentValues.put("Estatus", "Cerrado");

        updateQuery(TABLE_NAME,contentValues,"",null);

        Log.i("ViajesDB","::::::::Insertando viaje" + viaje);

        contentValues = new ContentValues();
        contentValues.put("id", viaje.getId());
        contentValues.put("CP", viaje.getCp());
        contentValues.put("Cita_Carga", viaje.getCitaCarga());
        contentValues.put("Cita_Descarga", viaje.getCitaDescarga());
        contentValues.put("Cliente", viaje.getCliente());
        contentValues.put("Convenio", viaje.getConvenio());
        contentValues.put("Destino", viaje.getDestino());
        contentValues.put("Direccion_Carga", viaje.getDireccionCarga());
        contentValues.put("Direccion_Descarga", viaje.getDireccionDescarga());
        contentValues.put("NumeroSolicitud", viaje.getNumeroSolicitud());
        contentValues.put("Origen", viaje.getOrigen());
        contentValues.put("Shipment", viaje.getShipment());

       // contentValues.put("Estatus", "Actual");

        contentValues.put("oLongitud", viaje.getoLongitud());
        contentValues.put("oLatitud", viaje.getoLatitud());
        contentValues.put("dLongitud", viaje.getdLongitud());
        contentValues.put("dLatitud", viaje.getdLatitud());
        contentValues.put("Intermedios", viaje.getIntermedios());
        contentValues.put("Estatus", viaje.getEstatus());

        contentValues.put("doLongitud", viaje.getDoLongitud());
        contentValues.put("doLatitud", viaje.getDoLatitud());
        contentValues.put("ddLongitud", viaje.getDdLongitud());
        contentValues.put("ddLatitud", viaje.getDdLatitud());
        contentValues.put("dIntermedios", viaje.getDintermedios());


        Log.d(":::::::::","intermedios::::" + viaje.getIntermedios());

        long result = this.insertar(TABLE_NAME,contentValues);

        return  result;
    }

    public List<viajeTO> obtenerNotificaciones(String Estatus)
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        if(!Estatus.isEmpty())
            query+=" WHERE Estatus ='" + Estatus + "'";
        query+=" order by id desc";

        List<viajeTO> viajes = new ArrayList<viajeTO>();
        viajeTO viaje = null;

        Cursor res = this.getDataQuery(query);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            viaje = new viajeTO();
            viaje.setId(res.getInt(res.getColumnIndex("id")));
            viaje.setNumeroSolicitud(res.getInt(res.getColumnIndex("NumeroSolicitud")));
            viaje.setCp(res.getString(res.getColumnIndex("CP")));
            viaje.setCitaCarga(res.getString(res.getColumnIndex("Cita_Carga")));
            viaje.setCitaDescarga(res.getString(res.getColumnIndex("Cita_Descarga")));
            viaje.setCliente(res.getString(res.getColumnIndex("Cliente")));
            viaje.setConvenio(res.getString(res.getColumnIndex("Convenio")));
            viaje.setDestino(res.getString(res.getColumnIndex("Destino")));
            viaje.setDireccionEntrega(res.getString(res.getColumnIndex("Direccion_Carga")));
            viaje.setDireccionDescarga(res.getString(res.getColumnIndex("Direccion_Descarga")));
            viaje.setOrigen(res.getString(res.getColumnIndex("Origen")));
            viaje.setShipment(res.getString(res.getColumnIndex("Shipment")));

            viaje.setoLatitud(res.getString(res.getColumnIndex("oLatitud")));
            viaje.setoLongitud(res.getString(res.getColumnIndex("oLongitud")));
            viaje.setdLatitud(res.getString(res.getColumnIndex("dLatitud")));
            viaje.setdLongitud(res.getString(res.getColumnIndex("dLongitud")));
            viaje.setIntermedios(res.getString(res.getColumnIndex("Intermedios")));

            viaje.setDoLatitud(res.getString(res.getColumnIndex("doLatitud")));
            viaje.setDoLongitud(res.getString(res.getColumnIndex("doLongitud")));
            viaje.setDdLatitud(res.getString(res.getColumnIndex("ddLatitud")));
            viaje.setDdLongitud(res.getString(res.getColumnIndex("ddLongitud")));
            viaje.setDintermedios(res.getString(res.getColumnIndex("dIntermedios")));

            viajes.add(viaje);
            res.moveToNext();
        }
        res.close();
        return  viajes;
    }

    public  viajeTO obtenerActual()
    {
        String query = "SELECT * FROM " + TABLE_NAME + " order by id desc limit 1";

        viajeTO viaje = null;

        Cursor res = this.getDataQuery(query);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            viaje = new viajeTO();
            viaje.setId(res.getInt(res.getColumnIndex("id")));
            viaje.setNumeroSolicitud(res.getInt(res.getColumnIndex("NumeroSolicitud")));
            viaje.setCp(res.getString(res.getColumnIndex("CP")));
            viaje.setCitaCarga(res.getString(res.getColumnIndex("Cita_Carga")));
            viaje.setCitaDescarga(res.getString(res.getColumnIndex("Cita_Descarga")));
            viaje.setCliente(res.getString(res.getColumnIndex("Cliente")));
            viaje.setConvenio(res.getString(res.getColumnIndex("Convenio")));
            viaje.setDestino(res.getString(res.getColumnIndex("Destino")));
            viaje.setDireccionEntrega(res.getString(res.getColumnIndex("Direccion_Carga")));
            viaje.setDireccionDescarga(res.getString(res.getColumnIndex("Direccion_Descarga")));
            viaje.setOrigen(res.getString(res.getColumnIndex("Origen")));
            viaje.setShipment(res.getString(res.getColumnIndex("Shipment")));




            viaje.setoLatitud(res.getString(res.getColumnIndex("oLatitud")));
            viaje.setoLongitud(res.getString(res.getColumnIndex("oLongitud")));
            viaje.setdLatitud(res.getString(res.getColumnIndex("dLatitud")));
            viaje.setdLongitud(res.getString(res.getColumnIndex("dLongitud")));
            viaje.setIntermedios(res.getString(res.getColumnIndex("Intermedios")));

/*
            Log.d("::::::::",res.getString(res.getColumnIndex("doLongitud")));
            Log.d("::::::::",res.getString(res.getColumnIndex("doLatitud")));
            Log.d("::::::::",res.getString(res.getColumnIndex("ddLatitud")));
            Log.d("::::::::",res.getString(res.getColumnIndex("ddLongitud")));
*/
            viaje.setDoLatitud(res.getString(res.getColumnIndex("doLatitud")));
            viaje.setDoLongitud(res.getString(res.getColumnIndex("doLongitud")));
            viaje.setDdLatitud(res.getString(res.getColumnIndex("ddLatitud")));
            viaje.setDdLongitud(res.getString(res.getColumnIndex("ddLongitud")));
            viaje.setDintermedios(res.getString(res.getColumnIndex("dIntermedios")));
            /*
            contentValues.put("id", viaje.getId());
        contentValues.put("CP", viaje.getCp());
        contentValues.put("", viaje.getCitaCarga());
        contentValues.put("", viaje.getCitaDescarga());
        contentValues.put("", viaje.getCliente());
        contentValues.put("", viaje.getConvenio());
        contentValues.put("", viaje.getDestino());
        contentValues.put("", viaje.getDireccionDescarga());
        contentValues.put("", viaje.getDireccionEntrega());
        contentValues.put("NumeroSolicitud", viaje.getNumeroSolicitud());
        contentValues.put("", viaje.getOrigen());
        contentValues.put("", viaje.getShipment());
        contentValues.put("Estatus", viaje.getEstatus());
             */

            break;
        }
        res.close();
        return  viaje;
    }

    public viajeTO obtenerViaje(int viajeId)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " where id =" + viajeId;
        viajeTO viaje = null;

        Cursor res = this.getDataQuery(query);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            viaje = new viajeTO();
            viaje.setId(res.getInt(res.getColumnIndex("id")));
            viaje.setNumeroSolicitud(res.getInt(res.getColumnIndex("NumeroSolicitud")));
            viaje.setCp(res.getString(res.getColumnIndex("CP")));
            viaje.setCitaCarga(res.getString(res.getColumnIndex("Cita_Carga")));
            viaje.setCitaDescarga(res.getString(res.getColumnIndex("Cita_Descarga")));
            viaje.setCliente(res.getString(res.getColumnIndex("Cliente")));
            viaje.setConvenio(res.getString(res.getColumnIndex("Convenio")));
            viaje.setDestino(res.getString(res.getColumnIndex("Destino")));
            viaje.setDireccionEntrega(res.getString(res.getColumnIndex("Direccion_Carga")));
            viaje.setDireccionDescarga(res.getString(res.getColumnIndex("Direccion_Descarga")));
            viaje.setOrigen(res.getString(res.getColumnIndex("Origen")));
            viaje.setShipment(res.getString(res.getColumnIndex("Shipment")));
            viaje.setEstatus(res.getString(res.getColumnIndex("Estatus")));
            viaje.setoLatitud(res.getString(res.getColumnIndex("oLatitud")));
            viaje.setoLongitud(res.getString(res.getColumnIndex("oLongitud")));
            viaje.setdLatitud(res.getString(res.getColumnIndex("dLatitud")));
            viaje.setdLongitud(res.getString(res.getColumnIndex("dLongitud")));
            viaje.setIntermedios(res.getString(res.getColumnIndex("Intermedios")));

            viaje.setDoLatitud(res.getString(res.getColumnIndex("doLatitud")));
            viaje.setDoLongitud(res.getString(res.getColumnIndex("doLongitud")));
            viaje.setDdLatitud(res.getString(res.getColumnIndex("ddLatitud")));
            viaje.setDdLongitud(res.getString(res.getColumnIndex("ddLongitud")));
            viaje.setDintermedios(res.getString(res.getColumnIndex("dIntermedios")));

            break;
        }
        res.close();
        return  viaje;
    }


    public void depurarRegistros()
    {
        this.deleteQuery(this.TABLE_NAME,"1=?",new String[] {String.valueOf("1")});
        this.close();
    }
}
