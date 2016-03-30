package com.example.casta.agenda;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class AgregarTarea extends Activity {

    private Conexion BD;
    EditText titulo;
    EditText coment;
    EditText fecha;
    EditText hora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);
        BD= new Conexion(getApplicationContext());
        titulo=(EditText)findViewById(R.id.tbTitulo);
        coment=(EditText)findViewById(R.id.tbComentario);
        fecha=(EditText)findViewById(R.id.tbFecha);
        hora=(EditText)findViewById(R.id.tbHora);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_tarea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Guardar(View view) {
        if(titulo.getText().toString()!="" && coment.getText().toString()!="" && fecha.getText().toString()!="" && hora.getText().toString()!=""){
            BD.open();
            BD.CrearTarea(titulo.getText().toString(), coment.getText().toString(),fecha.getText().toString(),true);
            BD.close();
        }
    }
}
