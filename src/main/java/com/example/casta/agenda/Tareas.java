package com.example.casta.agenda;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.ContextMenu.ContextMenuInfo;

public class Tareas extends Activity {
    private final static String nombreTablaTareas="Tareas";
    private Conexion BD;
    private String[] lista;
    ListView Lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        BD=new Conexion(getApplicationContext());
        Lista= (ListView) findViewById(R.id.listView);
        registerForContextMenu(Lista);
        LlenarDatos();
    }

    private void LlenarDatos(){

        Cursor mCursor=BD.ObtenerTodosEventos(nombreTablaTareas);
        startManagingCursor(mCursor);
        String[] dataColumns = { BD.titulo };
        int[] viewIDs = {android.R.id.text1 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,                // The Activity context
                android.R.layout.simple_list_item_1,  // Points to the XML for a list item
                mCursor,              // Cursor that contains the data to display
                dataColumns,         // Bind the data in column "text_column"...
                viewIDs);
        Lista.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tareas, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater m= getMenuInflater();
        m.inflate(R.menu.context_menu_tareas,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addTarea) {
            Intent i = new Intent(this, AgregarTarea.class );
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.eliminar:
                System.out.println(Lista.getSelectedItemId());


                return true;
            case R.id.editar:
                Cursor c=BD.ObtenerEventoUnico(Lista.getSelectedItemId(),nombreTablaTareas);
                return true;
        }
        return super.onContextItemSelected(item);
    }

}
