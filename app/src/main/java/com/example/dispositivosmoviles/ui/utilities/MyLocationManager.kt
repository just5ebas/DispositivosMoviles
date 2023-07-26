package com.example.dispositivosmoviles.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
// Por parametros
//class MyLocationManager {
//
//    var context: Context? = null
//
//    private lateinit var client: SettingsClient
//
//    private fun initVars() {
//        if (context != null) {
//            client = LocationServices.getSettingsClient(context!!)
//        }
//    }
//
//    public fun getUserLocation(){
//        initVars()
//
//    }
//
//}

// Por constructor
class MyLocationManager(var context: Context) {
    private lateinit var client: SettingsClient

    private fun initVars() {
        if (context != null) {
            client = LocationServices.getSettingsClient(context!!)
        }
    }

    public fun getUserLocation() {
        initVars()
    }

}