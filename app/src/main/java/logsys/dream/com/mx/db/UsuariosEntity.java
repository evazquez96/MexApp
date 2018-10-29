package logsys.dream.com.mx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import logsys.dream.com.mx.contracts.Usuario;

/**
 * Created by paqti on 11/03/2017.
 */

public class UsuariosEntity  extends DBHelper{

    public static final String TABLE_NAME = "Usuarios";

    public UsuariosEntity(Context context)
    {
        super(context);
    }

    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       super.onUpgrade(db,oldVersion,newVersion);
    }

    public void depurarRegistros()
    {
        this.deleteQuery(this.TABLE_NAME,null,null);
    }

    public  long insertarUsuario(Usuario usuario) {
        //System.out.println("::::::::Insertando usuarios");
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", usuario.getId());
        contentValues.put("nombre", usuario.getNombre());
        contentValues.put("user", usuario.getNombreUsuario());
        contentValues.put("pass", usuario.getPassword());
        contentValues.put("imei", usuario.getImei());
        //System.out.println("*******************Insertando:::" + usuario);

        return this.insertar(TABLE_NAME,contentValues);
    }

}
