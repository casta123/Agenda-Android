package com.example.casta.agenda;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Me guié en:
 * http://elbauldeandroid.blogspot.com.co/2013/02/base-de-datos-sqlite.html
 * http://www.hermosaprogramacion.com/2014/10/android-sqlite-bases-de-datos/
 */
public class Conexion  extends SQLiteOpenHelper {

    private final static String nombreDB="Agenda";
    private final static int versionDB=1;
    private final static String nombreTablaTareas="Tareas";
    private final static String nombreTablaNotas="Notas";
    private final static String nombreTablaEventos="Eventos";
    private final static String id="_id";
    public final static String titulo="titulo";
    private final static String descripcion="descripcion";
    private final static String fecha="fecha";
    private final static String hora="hora";
    private final static String estado="Estado";
    private final static String correo="correo";
    private final static String lugar="lugar";
    private final static String CrearTablaT="CREATE TABLE "+nombreTablaTareas+"("+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+titulo+" TEXT,"+descripcion+" TEXT,"+fecha+" datetime,"+estado+" BOOLEAN)";
    private final static String CrearTablaN="CREATE TABLE "+nombreTablaNotas+"("+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+titulo+" TEXT,"+descripcion+" TEXT,"+fecha+" datetime,"+correo+" TEXT)";
    private final static String CrearTablaE="CREATE TABLE "+nombreTablaEventos+"("+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+lugar+" TEXT,"+descripcion+" TEXT,"+fecha+" datetime,"+hora+" TEXT)";
    private SQLiteDatabase database;

    public Conexion open() {
        database = getWritableDatabase();
        return this;
    }

    public void close(){
        database.close();
    }

    public long CrearTarea(String titulo,String descripcion,String fecha,boolean Estado){
        database= getWritableDatabase();
        if (database != null) {
        // Contenedor de valores
        ContentValues values = new ContentValues();
        values.put(this.titulo,titulo);
        values.put(this.descripcion,descripcion);
        values.put(this.fecha, fecha);
        values.put(this.estado,Estado);
        //Insertando en la base de datos
        database.insert(nombreTablaTareas,null,values);
        }
        return 1;
    }

    public long CrearNota(String titulo,String descripcion,String fecha,String hora,String Correo){
        database= getWritableDatabase();
        if (database != null) {
            // Contenedor de valores
            ContentValues values = new ContentValues();
            values.put(this.titulo,titulo);
            values.put(this.descripcion,descripcion);
            values.put(this.fecha, fecha+hora);
            values.put(this.correo,Correo);
            //Insertando en la base de datos
            database.insert(nombreTablaNotas,null,values);
        }
        return 1;
    }
    public long CrearEvento(String lugar,String descripcion,String fecha,String hora){
        database= getWritableDatabase();
        if (database != null) {
            // Contenedor de valores
            ContentValues values = new ContentValues();
            values.put(this.lugar,lugar);
            values.put(this.descripcion,descripcion);
            values.put(this.fecha, fecha+hora);
            values.put(this.hora, hora);
            //Insertando en la base de datos
            database.insert(nombreTablaEventos,null,values);
        }
        return 1;
    }
    public boolean BorrarEvento(Long idFila,String nombreTabla){
        database= getWritableDatabase();
        String selection = id + " = ? ";
        String selectionArgs[] = new String[]{""+idFila};
        database.delete(nombreTabla, selection, selectionArgs);
        return true;
    }

    public Cursor ObtenerEventoUnico(long idFila,String nombreTabla){
        database= getWritableDatabase();
        String columns[];
        if(nombreTabla==nombreTablaTareas) {
            columns=new String[]{id, titulo, descripcion, fecha, estado};
        }else if(nombreTabla==nombreTablaNotas){
            columns=new String[]{id, titulo, descripcion, fecha, correo};
        }else {
            columns=new String[]{id, lugar, descripcion, fecha, hora};
        }
        String selection = id + " = ? ";
        String selectionArgs[] = new String[]{""+idFila};
        Cursor c = database.query(
                nombreTabla,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return c;
    }

  public Cursor ObtenerTodosEventos(String nombreTabla){
        database= getWritableDatabase();
      String columns[];
      if(nombreTabla==nombreTablaTareas) {
          columns=new String[]{id, titulo, descripcion, fecha, estado};
      }else if(nombreTabla==nombreTablaNotas){
          columns=new String[]{id, titulo, descripcion, fecha, correo};
      }else {
          columns=new String[]{id, lugar, descripcion, fecha, hora};
      }
       Cursor c = database.query(
                nombreTabla,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        return c;
    }

    public boolean ActualizarTarea(long idFila,String titulo,String descripcion,String Fecha,boolean Estado){
        database= getWritableDatabase();
        // Contenedor de valores
        ContentValues values = new ContentValues();
        //Seteando Campos
        values.put(this.titulo,titulo);
        values.put(this.descripcion,descripcion);
        values.put(this.fecha,Fecha);
        values.put(this.estado,Estado);
        //Clausula WHERE
        String selection = this.id+ " = ?";
        String[] selectionArgs = { ""+idFila };
        //Actualizando
        database.update(nombreTablaTareas, values, selection, selectionArgs);
        return true;
    }

    public boolean ActualizarNota(long idFila,String titulo,String descripcion,String Fecha,String Correo){
        database= getWritableDatabase();
        // Contenedor de valores
        ContentValues values = new ContentValues();
        //Seteando Campos
        values.put(this.titulo,titulo);
        values.put(this.descripcion,descripcion);
        values.put(this.fecha,Fecha);
        values.put(this.correo,Correo);
        //Clausula WHERE
        String selection = this.id+ " = ?";
        String[] selectionArgs = { ""+idFila };
        //Actualizando
        database.update(nombreTablaNotas, values, selection, selectionArgs);
        return true;
    }

    public boolean ActualizarEvento(long idFila,String lugar,String descripcion,String Fecha,String hora){
        database= getWritableDatabase();
        // Contenedor de valores
        ContentValues values = new ContentValues();
        //Seteando Campos
        values.put(this.lugar,lugar);
        values.put(this.descripcion,descripcion);
        values.put(this.fecha,Fecha);
        values.put(this.hora,hora);
        //Clausula WHERE
        String selection = this.id+ " = ?";
        String[] selectionArgs = { ""+idFila };
        //Actualizando
        database.update(nombreTablaEventos, values, selection, selectionArgs);
        return true;
    }

        public  Conexion(Context context) {
            super(context, nombreDB, null, versionDB);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CrearTablaT);
            database.execSQL(CrearTablaN);
            database.execSQL(CrearTablaE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + nombreTablaTareas);
            db.execSQL("DROP TABLE IF EXISTS " + nombreTablaNotas);
            db.execSQL("DROP TABLE IF EXISTS " + nombreTablaEventos);
            onCreate(db);
        }
}


