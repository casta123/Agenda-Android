package com.example.casta.agenda;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class NotasActivity extends AppCompatActivity {

    private final int NOTIFICATION_ID = 1010;
    private Conexion BD;
    private final static String nombreTablaNotas="Notas";
    private EditText titulo;
    private EditText descripcion;
    private EditText correo;
    private DatePicker fecha;
    private TimePicker hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        BD=new Conexion(getApplicationContext());
        titulo=(EditText)findViewById(R.id.TituloN);
        descripcion=(EditText)findViewById(R.id.descripcionN);
        correo=(EditText)findViewById(R.id.correoN);
        fecha=(DatePicker)findViewById(R.id.fechaN);
        hora=(TimePicker)findViewById(R.id.horaN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notas, menu);
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

    public void GuardaDatos(View view) {

        BD.CrearNota(titulo.getText().toString(),descripcion.getText().toString(),fecha.getDayOfMonth()+"/"+fecha.getMonth()+"/"+fecha.getYear(),hora.getCurrentHour()+":"+hora.getCurrentMinute(),correo.getText().toString());

        java.util.Date cuando = new java.util.Date(fecha.getYear(),fecha.getMonth(),fecha.getDayOfMonth(),hora.getCurrentHour(),hora.getCurrentMinute());
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                triggerNotification();
            }
        };
        timer.scheduleAtFixedRate(timerTask, cuando,1000);


        /* Intent itSend = new Intent(android.content.Intent.ACTION_SEND);
        itSend.setType("plain/text");
        itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{correo.getText().toString()});
        itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, titulo.getText().toString());
        itSend.putExtra(android.content.Intent.EXTRA_TEXT, descripcion.getText().toString()+" "+fecha.getDayOfMonth()+"/"+fecha.getMonth()+"/"+fecha.getYear()+" "+hora.getCurrentHour()+":"+hora.getCurrentMinute());
        startActivity(itSend); */
    }



    private void triggerNotification(){

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.androideity, titulo.getText().toString(), System.currentTimeMillis());

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_layout);
        contentView.setTextViewText(R.id.txt_notification, descripcion.getText().toString());

        notification.contentView = contentView;

        Intent notificationIntent = new Intent(this, NotasActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.contentIntent = contentIntent;

        notificationManager.notify(NOTIFICATION_ID, notification);

        Intent itSend = new Intent(android.content.Intent.ACTION_SEND);
        itSend.setType("plain/text");
        itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{correo.getText().toString()});
        itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, titulo.getText().toString());
        itSend.putExtra(android.content.Intent.EXTRA_TEXT, descripcion.getText().toString()+" "+fecha.getDayOfMonth()+"/"+fecha.getMonth()+"/"+fecha.getYear()+" "+hora.getCurrentHour()+":"+hora.getCurrentMinute());
        startActivity(itSend);
    }
}
