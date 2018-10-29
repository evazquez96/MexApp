package logsys.dream.com.mx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import logsys.dream.com.mx.contracts.Usuario;
import logsys.dream.com.mx.models.ServiceData;

public class ServiceUsuarios extends DBHelper {

    public static final String TABLE_NAME = "UserData";

    public ServiceUsuarios(Context context) {super(context);}
    public void onCreate(SQLiteDatabase db) {super.onCreate(db);}

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db,oldVersion,newVersion);
    }

    public void depurarRegistros()
    {
        this.deleteQuery(this.TABLE_NAME,null,null);
    }

    public  long insertarUsuarios(ServiceData usuario) {
        //System.out.println("::::::::Insertando usuarios");
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", usuario.getId());
        contentValues.put("user", usuario.getUser());
        contentValues.put("licensia", usuario.getLicensia());
        contentValues.put("vigencia", usuario.getVigencia());
        contentValues.put("unidad", usuario.getUnidad());
        contentValues.put("placas", usuario.getPlacas());
        contentValues.put("marcamodelo", usuario.getMarcamodelo());
        contentValues.put("solicuitud", usuario.getSolicitud());
        contentValues.put("tipo", usuario.getTipo());
        contentValues.put("origen", usuario.getOrigen());
        contentValues.put("destino", usuario.getDestino());
        contentValues.put("fecha", usuario.getFecha());
        //System.out.println("*******************Insertando:::" + usuario);

        return this.insertar(TABLE_NAME,contentValues);
    }

}
