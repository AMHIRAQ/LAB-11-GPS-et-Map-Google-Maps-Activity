package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // 1) LocationManager permet d'écouter la localisation
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // 2) Marker initial (exemple)
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        Toast.makeText(applicationContext, "Map Ready", Toast.LENGTH_SHORT).show()

        // 3) Vérifier permission runtime
        val permissionGranted = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            // 4) Demander des mises à jour de position
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000L,   // minTime = 1 seconde
                    50f,     // minDistance = 50 mètres
                    object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            val latitude = location.latitude
                            val longitude = location.longitude

                            // 5) Afficher un toast (debug)
                            Toast.makeText(
                                applicationContext,
                                "$latitude $longitude",
                                Toast.LENGTH_SHORT
                            ).show()

                            // 6) Ajouter ou déplacer le marker pour la nouvelle position
                            val position = LatLng(latitude, longitude)

                            if (currentMarker == null) {
                                currentMarker = mMap.addMarker(
                                    MarkerOptions().position(position).title("Position actuelle")
                                )
                            } else {
                                currentMarker?.position = position
                            }

                            // 7) Zoomer et centrer sur cette position
                            val zoomLevel = 15.0f
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel))
                        }

                        override fun onProviderDisabled(provider: String) {
                            buildAlertMessageNoGps()
                        }

                        @Deprecated("Deprecated in Java")
                        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        }

                        override fun onProviderEnabled(provider: String) {
                        }
                    }
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {
            // 8) Si pas de permission : la demander
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                200
            )
        }

        // 9) Mise en place caméra initiale
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accordée", Toast.LENGTH_SHORT).show()
                // Re-déclencher la logique
                onMapReady(mMap)
            } else {
                Toast.makeText(this, "Permission refusée", Toast.LENGTH_LONG).show()
            }
        }
    }
}
