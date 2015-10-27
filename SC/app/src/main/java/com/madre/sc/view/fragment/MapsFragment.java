package com.madre.sc.view.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.madre.sc.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private View view;
    private GoogleMap mMap;
    private LatLngBounds.Builder builder;
    private LatLng partnerLatLng;
    private MarkerOptions myOptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsFragment.this);
        builder = new LatLngBounds.Builder();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        Location location = locationManager.getLastKnownLocation(provider);

        locationManager.requestLocationUpdates(provider, 20000, 1, this);

        if (location != null)
            onLocationChanged(location);

        LatLng myLatLng = new LatLng(21.029391,105.8490153);
        partnerLatLng = new LatLng(21.03018, 105.8316663);

        mMap.addMarker(new MarkerOptions().position(partnerLatLng).title("Partner position"));
        mMap.addMarker(new MarkerOptions().position(myLatLng).title("My position"));
        builder.include(partnerLatLng);
        builder.include(myLatLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
    }

    @Override
    public void onLocationChanged(Location location) {
//        double lat = location.getLatitude();
//        double lng = location.getLongitude();
//        LatLng myLatLng = new LatLng(lat, lng);
//        builder.include(myLatLng);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), 200);
//        mMap.animateCamera(cameraUpdate);
//
//        myOptions = new MarkerOptions().position(myLatLng).title("Your position");
//        mMap.addMarker(myOptions);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }


}
