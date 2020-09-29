package com.kavya.locationlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log

class LocationClass(context: Context) {

    lateinit var locationManager: LocationManager
    private var locationGPS: Location? = null
    private var locationNetwork: Location? = null

    var latGPS: Double = 0.0
    var longGPS: Double = 0.0
    var latNetwork: Double = 0.0
    var longNetwork: Double = 0.0

    init {
        latGPS = getLocation(context).latitude
        longGPS = getLocation(context).longitude
        Log.e("CLs", "In init")
    }
    @SuppressLint("MissingPermission")
    //Returns a location object to be used
    fun getLocation(context: Context): Location {

        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object :
            LocationListener {
            override fun onLocationChanged(location: Location?) {
                //If the location changes
                if (location != null) {
                    //Update the location by the GPS
                    locationNetwork = location

                }

            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

            }

            override fun onProviderEnabled(provider: String?) {

            }

            override fun onProviderDisabled(provider: String?) {

            }
        })

        //Check for the last location
        val localNetworkLocation =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (localNetworkLocation != null) {
            locationNetwork = localNetworkLocation
            latNetwork = locationNetwork!!.latitude
            longNetwork = locationNetwork!!.longitude

        }

        val localGpsLocation =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (localGpsLocation != null) {
            locationGPS = localGpsLocation
            latGPS = locationGPS!!.latitude
            longGPS = locationGPS!!.latitude
        }


        //Choose the more accurate location
        if (locationGPS!!.accuracy >= locationNetwork!!.accuracy) {
            return locationGPS!!
        } else {
            return locationNetwork!!
        }
    }




}