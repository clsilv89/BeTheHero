package com.caiosilva.myapplication.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.helper.Permissions

class SettingsActivity : AppCompatActivity() {

    private val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    android.Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val openGallery = findViewById<ImageButton>(R.id.open_gallery_setting_iv)
        val openCameraBtn = findViewById<ImageButton>(R.id.open_camera_setting_iv)

        Permissions().validatePermission(permissions, this, 1)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.title = resources.getString(R.string.settings_menu)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        openCameraBtn.setOnClickListener {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(camera.resolveActivity(packageManager) != null) {
                startActivityForResult(camera, REQUEST_CAMERA)
            } else {
                Toast.makeText(this, R.string.camera_error_access, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (permitResult in grantResults) {
            if(permitResult == PackageManager.PERMISSION_DENIED) {
                permitAlert()
            }
        }
    }

    private fun permitAlert() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.alert_title)
        alertDialog.setMessage(R.string.alert_permission_text)
        alertDialog.setPositiveButton(R.string.alert_ok_bt, DialogInterface.OnClickListener{
            dialog, which -> finish()
        })

        alertDialog.create().setCancelable(false)
        alertDialog.show()
    }

    companion object {
        const val REQUEST_CAMERA = 100
        const val REQUEST_GALLERY = 200
    }
}
