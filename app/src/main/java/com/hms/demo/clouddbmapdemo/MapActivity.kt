package com.hms.demo.clouddbmapdemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var hMap: HuaweiMap

    private lateinit var mMapView: MapView

    private val TAG="MapKit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        if(hasPermissions()){
            getMapViewInstance()
        }
        else{
            requestPermissions()
        }
    }

    private fun getMapViewInstance() {
        mMapView = findViewById(R.id.mapView)
        mMapView.onCreate(null)
        mMapView.getMapAsync(this)
    }

    private fun requestPermissions() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        requestPermissions(permissions,0)
    }

    private fun hasPermissions(): Boolean {
        val afl=checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val acl=checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        if(afl!= PackageManager.PERMISSION_GRANTED&&acl!= PackageManager.PERMISSION_GRANTED){
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(hasPermissions()){
            getMapViewInstance()
        }
    }

    override fun onMapReady(map: HuaweiMap?) {
        Log.d(TAG, "onMapReady: ")
        if (map != null) {
            hMap = map
        }
    }

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onPause() {
        mMapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }
}
