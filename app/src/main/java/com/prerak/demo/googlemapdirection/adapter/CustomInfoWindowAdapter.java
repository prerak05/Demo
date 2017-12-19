package com.prerak.demo.googlemapdirection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.prerak.demo.R;
import com.prerak.demo.googlemapdirection.MapsActivity;

/**
 * Created by emxcel on 13/12/17.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private LatLng latLng;

    public CustomInfoWindowAdapter(MapsActivity mapsActivity, LatLng sydney) {
        this.context = mapsActivity;
        this.latLng = sydney;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.custominfowindow, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvSubTitle = (TextView) view.findViewById(R.id.tv_subtitle);
        TextView tvLatLang = (TextView) view.findViewById(R.id.tv_latLng);
        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(marker.getSnippet());
        tvLatLang.setText(latLng.latitude + " " + latLng.longitude);
        return view;
    }
}
