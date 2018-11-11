package br.com.alura.william.agenda.maps

import android.content.Context
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class Localizador(var context: Context, googleMapa: GoogleMap) : GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener {

    var googleMap: GoogleMap

    var googleApiClient: GoogleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .build()

    init {
        googleApiClient.connect()
        googleMap = googleMapa
    }

    override fun onConnected(bundle: Bundle?) {
        val locationRequest = LocationRequest()
        locationRequest.smallestDisplacement = 50F
        locationRequest.interval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                this
        )
    }

    override fun onConnectionSuspended(i: Int) {}

    override fun onLocationChanged(location: Location?) {
        val coordenada = LatLng(location?.latitude!!, location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLng(coordenada)
        googleMap.moveCamera(cameraUpdate)
    }
}