package logsys.dream.com.mx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import logsys.dream.com.mx.contracts.notifications.Notification_TO;

/**
 * Created by JUAHERNA on 2/22/2018.
 */

public class NotificationsDB extends DBHelper {

    public static final String TABLE_NAME = "Notification_Table";

    public NotificationsDB(Context context) {
        super(context);
    }

    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db,oldVersion,newVersion);
    }

    public long insertarNotificacion(Notification_TO notificacion)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", notificacion.getId());
        contentValues.put("tipoNotificacionId", notificacion.getTipoNotificacion_id());
        contentValues.put("tipoNotificacion", notificacion.getTipoNotificacion());
        contentValues.put("fecha", notificacion.getFecha());
        contentValues.put("comentarios", notificacion.getComentarios());

        return this.insertar(TABLE_NAME,contentValues);
    }

    public List<Notification_TO> obtenerNotificaciones()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        List<Notification_TO> notificaciones = new ArrayList<Notification_TO>();
        Notification_TO notificacion = null;

        Cursor res = this.getDataQuery(query);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            notificacion = new Notification_TO();
            notificacion.setId(res.getInt(res.getColumnIndex("id")));
            notificacion.setComentarios(res.getString(res.getColumnIndex("comentarios")));
            notificaciones.add(notificacion);
            res.moveToNext();
        }
        return  notificaciones;
    }

}
