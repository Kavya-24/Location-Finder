package com.example.location

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kavya.locationlibrary.LocationClass

class MainActivity : AppCompatActivity() {

    private val permissionRequestCode = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {
            getPermissions()
        }


    }


    private fun getPermissions() {
        val p = this.let {
            ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        if (p == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Checking if allowed", Toast.LENGTH_SHORT).show()

            getLocation()
        }

        if (p != PackageManager.PERMISSION_GRANTED) {
            //Not permitted
            Toast.makeText(this, "Permissions needed", Toast.LENGTH_SHORT).show()


        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this as Activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            //Tell user what we are going to do with this permission
            val b = AlertDialog.Builder(this)
            b.setMessage("Permission to access location")
            b.setTitle("Permission required")
            b.setPositiveButton("Ok") { dialog: DialogInterface?, which: Int ->
                makeRequest()
            }
            val dialog = b.create()
            dialog.show()
        } else {
            makeRequest()
        }

    }

    private fun getLocation() {
        //Create an object


        findViewById<TextView>(R.id.tvLatitutde).text = LocationClass().latGPS.toString()
        findViewById<TextView>(R.id.tvLongitude).text = LocationClass().longGPS.toString()


    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this as Activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            permissionRequestCode
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionRequestCode ->
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission denied by user", Toast.LENGTH_SHORT).show()

                } else {

                }

        }


    }

}
