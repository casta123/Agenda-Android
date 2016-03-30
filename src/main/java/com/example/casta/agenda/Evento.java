package com.example.casta.agenda;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class Evento extends FragmentActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Conexion BD;
    private EditText titulo;
    private EditText lugar;
    private DatePicker fecha;
    private TimePicker hora;

    Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        setUpMapIfNeeded();
        BD= new Conexion(getApplicationContext());
        titulo=(EditText)findViewById(R.id.TituloE);
        lugar=(EditText)findViewById(R.id.LugarE);
        fecha=(DatePicker)findViewById(R.id.fechaE);
        hora=(TimePicker)findViewById(R.id.HoraE);
        myLocation = mMap.getMyLocation();

mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
    @Override
    public boolean onMyLocationButtonClick() {

        LatLng LatLgn;
        if (myLocation != null) {
            LatLgn = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            lugar.setText(String.format("%s %s", myLocation.getLatitude(), myLocation.getLongitude()));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(LatLgn));
        }
        return false;
    }
});
    }




    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

    }

    public void Guardar(View view) {
        if(titulo.getText().toString()!="" && lugar.getText().toString() != ""){
            BD.open();
            BD.CrearEvento(lugar.getText().toString(), titulo.getText().toString(), fecha.getDayOfMonth() + "/" + fecha.getMonth() + "/" + fecha.getYear(), hora.getCurrentHour() + ":" + hora.getCurrentMinute());
            BD.close();
            Toast.makeText(this, "Se guardó correctamente",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void setLocation(Location loc){
        if(loc.getLongitude()!=0.0 && loc.getLatitude()!=0.0){
            try {
                Geocoder geocoder=new Geocoder(this, Locale.getDefault());
                List<Address> list= geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if(list.isEmpty()){
                    Address address = list.get(0);
                    lugar.setText(""+ address.getAddressLine(0));
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        this.setLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "GPS Activado",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "GPS Desactivado",
                Toast.LENGTH_SHORT).show();
    }
}
