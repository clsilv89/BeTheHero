package com.caiosilva.myapplication.helper

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions {

    fun validatePermission(permited: Array<String>, activity: Activity, requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val permissions: MutableList<String> = ArrayList()
            for (i in permited) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    activity.applicationContext,
                    i
                ) == PackageManager.PERMISSION_GRANTED
                if (!hasPermission) {
                    permissions.add(i)
                }
            }
            if (permissions.isEmpty()) return true

            ActivityCompat.requestPermissions(activity, permited, requestCode)
        }
        return true
    }
}