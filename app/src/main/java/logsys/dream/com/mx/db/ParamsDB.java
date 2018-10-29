package logsys.dream.com.mx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JUAHERNA on 2/1/2017.
 */

public class ParamsDB extends DBHelper {

    /*
    Parámetro 1: tiempo de inicio
    Parámetro 2: tiempo a dormir
    Parámetro 3: usuario logeado
     */

    public static final String COLUMN_VALUE = "value";
    public static final String TABLE_NAME = "configuration";

    public ParamsDB(Context context)
    {
        super(context);
    }

    public void onCreate(SQLiteDatabase db) {
       super.onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db,oldVersion,newVersion);
    }

    public String get_Parametro(String id,String userId)
    {
        Cursor cur = getDataQuery("select * from configuration where id=" + id + " AND user_id=" + userId);
        if(!cur.moveToNext())
            return  null;
        String res = cur.getString(cur.getColumnIndex(COLUMN_VALUE));

        cur.close();
        this.close();

        return  res;
    }

    public long insertParam (String id,String userId,String name, String value)
    {
        long res = -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("value", value);
        contentValues.put("user_id", userId);

        contentValues.put("id", id);

        res = this.insertar(TABLE_NAME,contentValues);

        this.close();
        return res;
    }

    public long mergeParam (String id,String userId,String name, String value)
    {
        long res = -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("value", value);
        contentValues.put("user_id", userId);

        contentValues.put("id", id);

        if(existParam(id)) {
            this.updateParam(Integer.parseInt(id), userId, value, name);
            res = Long.parseLong(id);

            this.close();
            return  res;
        }

        res = this.insertar(TABLE_NAME,contentValues);


        this.close();

        return res;
    }

    public boolean updateParam (Integer id, String userId, String value,String name)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", value);
        contentValues.put("name", name);

        return updateQuery(TABLE_NAME,contentValues,"id = ? AND user_id= ?" , new String[]{Integer.toString(id),userId});
    }

    public Integer deleteParam (Integer id)
    {
        return deleteQuery(TABLE_NAME,"id = ?",new String[] { Integer.toString(id) });
    }

    public  boolean existParam(String id,long userId)
    {
        try {
            Cursor cur = getDataQuery("Select count(*) as Existe FROM " + TABLE_NAME + " WHERE Id=" + id + " AND user_Id=" + userId);

            if(!cur.moveToNext()) {
                cur.close();
                this.close();
                return false;
            }

            Boolean res = cur.getInt(0) >= 1;

            cur.close();
            this.close();

            return res;
        }catch(Exception ex) {
            ex.printStackTrace();
            return true ;
    }
    }

    public  boolean existParam(String id)
    {
        try {
            Cursor cur = getDataQuery("Select count(*) as Existe FROM " + TABLE_NAME + " WHERE Id=" + id);

            if(!cur.moveToNext()) {
                cur.close();
                this.close();
                return false;
            }

            boolean res =cur.getInt(0) >= 1;

            cur.close();
            this.close();

            return res;
        }catch(Exception ex) {
            ex.printStackTrace();
            return true ;
        }
    }
}
