package com.example.weather_app.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


object Permission {


    interface Callback {
        fun isPermissionAccepted(isAccepted: Boolean)
    }

    fun checkStoragePermission(activity: Activity?, callback: Callback) {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    callback.isPermissionAccepted(report?.areAllPermissionsGranted() ?: false)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }

    fun hasStoragePermissions(activity: Activity): Boolean {
        val resultExternal =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val resultInternal =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
        return resultExternal == PackageManager.PERMISSION_GRANTED &&
                resultInternal == PackageManager.PERMISSION_GRANTED
    }

    fun checkLocationPermission(activity: Activity?, callback: Callback) {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    callback.isPermissionAccepted(report?.areAllPermissionsGranted() ?: false)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }

    fun hasLocationPermissions(activity: Activity): Boolean {
        val resultExternal =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        val resultInternal =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        return resultExternal == PackageManager.PERMISSION_GRANTED &&
                resultInternal == PackageManager.PERMISSION_GRANTED
    }


}