package logsys.dream.com.mx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JUAHERNA on 1/30/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    //BD   "DB_LogsysDream.db";, rev 1
    public static final String DATABASE_NAME = "DB_LogsysDream.db";
    public static final int revision = 1;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, revision );
        Log.d("DBHelper","-------------------------DBHelper=> Constructor*****" + revision  + DATABASE_NAME);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table configuration " +
                        "(id integer,user_id integer, name text,value text)"
        );
        db.execSQL(
                "create table time_Table " +
                        "(id integer, start_date integer," +
                        "end_date integer,value text,enviado integer,sql_id integer primary key,user_id integer,tipo_actividad_id integer)"
        );

        db.execSQL(
                "create table Usuarios" +
                        "(id, integer,user text, pass text,nombre text,imei text)"
        );

        db.execSQL(
                "create table Notification_Table " +
                        "(id integer primary key,tipoNotificacionId integer, tipoNotificacion text, fecha text," +
                        "comentarios text)"
        );

        db.execSQL(
                "create table Viajes " +
                        "(id integer primary key,CP text, Cita_Carga text, Cita_Descarga text,Cliente text,Convenio text,Destino text,Direccion_Carga text" +
                        ",Direccion_Descarga text,NumeroSolicitud text,Origen text,Shipment text,Estatus text,oLongitud text,oLatitud text,dLatitud text,dLongitud text,Intermedios text," +
                        "doLongitud text,doLatitud text,ddLatitud text,ddLongitud text,dIntermedios text)"
        );

        db.execSQL(
                "create table UserData " +
                        "(id integer primary key, user text, licensia text, vigencia text" +
                        ",unidad text, placas text, marcamodelo text, solicuitud text, tipo text" +
                        ",origen text, destino text, fecha text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS time_Table");
        db.execSQL("DROP TABLE IF EXISTS configuration");
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL("DROP TABLE IF EXISTS Notification_Table");
        db.execSQL("DROP TABLE IF EXISTS Viajes");
        db.execSQL("DROP TABLE IF EXISTS UserData");

        onCreate(db);
    }

    public Cursor getDataQuery(String sql){
        Cursor res = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            res = db.rawQuery( sql, null );
            return res;
        } catch (Exception e) {

        }
        return  res;
    }

    public  long insertar(String tableName,ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        long result = db.insert(tableName, null, values);

        db.close();
        this.close();
        return  result;
    }

    public boolean updateQuery (String tableName,ContentValues values,String whereClause,String[] whereValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(tableName, values, whereClause, whereValues );

        db.close();
        this.close();
        return true;
    }

    public Integer deleteQuery (String tableName, String whereClause, String[] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Integer result = db.delete(tableName,
                whereClause,
                whereArgs);


        db.close();
        this.close();
        return result;
    }

    public int countRows(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,
                tableName);


        db.close();
        this.close();
        return numRows;
    }

    /*
    public static final String DATABASE_NAME = "DreamDB.db";
    public static final String PARAMS_TABLE_NAME = "configuration";
    public static final String PARAMS_COLUMN_ID = "id";
    public static final String PARAM_COLUMN_NAME = "name";
    public static final String VALUE_COLUMN_VALUE = "value";

    private HashMap hp;
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    public  String get_Parametro(String Id)
    {
        Cursor cur = getData(new Integer(Id));
        if(!cur.moveToNext())
            return  null;
        return  cur.getString(cur.getColumnIndex(VALUE_COLUMN_VALUE));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        db.execSQL(
                "create table configuration " +
                        "(id integer primary key, name text,value text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
// TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS configuration");
        onCreate(db);

        System.out.println(":::::::::::::OnUpgrated");
    }
    public boolean insertParam (String name, String value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("value", value);

        db.insert("configuration", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from configuration where id="+id, null );
        return res;
    }



    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,
                PARAMS_TABLE_NAME);
        return numRows;
    }
    public boolean updateParam (Integer id, String value,String name)
    {

        String rs = this.get_Parametro("1");
        System.out.println("Existe::: " + rs);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", value);
        contentValues.put("name", name);
System.out.println(":::::::::::Actualizando a " + value + " Id:" + id);
        db.update("configuration", contentValues, "id = ? ", new String[] {
                Integer.toString(id) } );
        return true;
    }

    public Integer deleteParam (Integer id)
    {
        String rs = this.get_Parametro("1");
        System.out.println("Existe::: " + rs);

        SQLiteDatabase db = this.getWritableDatabase();


        return db.delete("configuration",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public ArrayList getAllCotacts()
    {
        ArrayList array_list = new ArrayList();
//hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from contacts", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    */
}
