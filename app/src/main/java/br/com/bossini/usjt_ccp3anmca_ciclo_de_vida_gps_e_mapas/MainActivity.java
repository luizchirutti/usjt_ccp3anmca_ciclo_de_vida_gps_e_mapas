package br.com.bossini.usjt_ccp3anmca_ciclo_de_vida_gps_e_mapas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView LocalizacaoTextView;
    private Location localizacaoAtual;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_GPS = 1001;

    public MainActivity(TextView localizacaoTextView) {
        LocalizacaoTextView = localizacaoTextView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalizacaoTextView = findViewById(R.id.localizacaoTextView)
    }

    @Override
    public void onClick(View view) {
        double lat = localizacaoAtual.getLatitude();
        double lon = localizacaoAtual.getLongitude();
        Uri gmmIntentUri =
                Uri.parse(String.format("geo:%f,%f?q=restaurantes",
                        latitudeAtual, longitudeAtual));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public LocationManager getLocationManager() {
        final locationManager systemService = (locationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override

            public void onLocationChanged(Location location) {
                localizacaoAtual = location;
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                locationTextView.setText(String.format("Lat: %f, Long: %f", lat,
                        lon));
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle
                    extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
     //a permissão já foi dada?
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
     //somente ativa
    //a localização é obtida via hardware, intervalo de 0
            segundos e 0 metros entre atualizações
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, locationListener);
        }
        else{
    //permissão ainda não foi nada, solicita ao usuário
    //quando o usuário responder, o método
            onRequestPermissionsResult vai ser chamado
            ActivityCompat.requestPermissions(this,
                    new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS);
        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if (requestCode == REQUEST_CODE_GPS){
           if (grantResults.length > 0 &&
                   grantResults[0] == PackageManager.PERMISSION_GRANTED){
               if (ActivityCompat.checkSelfPermission(this,
                       Manifest.permission.ACCESS_FINE_LOCATION) ==
               PackageManager.PERMISSION_GRANTED){
               locationManager.requestLocationUpdates(
                   LocationManager.GPS_PROVIDER,
                   0,
                   0,
                   locationListener
               );
           }


       }
       else{
               Toast.makeText(this,
                       getString(R.string.no_gps_no_app),
                       Toast.LENGTH_SHORT).show();
                       )
           }
    }
}
