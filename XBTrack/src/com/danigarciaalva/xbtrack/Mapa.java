package com.danigarciaalva.xbtrack;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Mapa extends Fragment{

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap map;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	 
		  View v = inflater.inflate(R.layout.maps_fragment, null, false);
		  map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
	
		  Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
		  Marker kiel = map.addMarker(new MarkerOptions().position(KIEL).title("Kiel").snippet("Kiel is cool").icon(BitmapDescriptorFactory
		              .fromResource(R.drawable.ic_launcher)));
		  map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
		  map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	  return v;
	}

	private Object getSupportFragmentManager() {
		// TODO Auto-generated method stub
		return null;
	}
}