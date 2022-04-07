package org.me.gcu.ivan_todorov_mpd_cw_s1822426;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 *  @author Ivan Todorov [S1822426]
 *  Reference: Google Guides - Maps SDK for Android
 *  https://developers.google.com/maps/documentation/android-sdk/start
 **/

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Roadworks roadworks;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
            this.roadworks = (Roadworks) getIntent().getSerializableExtra("roadworks");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Double[] latlngArray = roadworks.getLatLng();
        LatLng maplocation = new LatLng(latlngArray[0], latlngArray[1]);
        MarkerOptions pointer = new MarkerOptions().position(maplocation).title(roadworks.getTitle());
        mMap.addMarker(pointer).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(maplocation, 14));
    }
}
