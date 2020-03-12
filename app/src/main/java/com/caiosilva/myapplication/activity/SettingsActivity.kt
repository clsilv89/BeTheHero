package com.caiosilva.myapplication.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.config.FirebaseConfig
import com.caiosilva.myapplication.helper.Permissions
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class SettingsActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA
    )
    private lateinit var profileImageView: CircleImageView
    private lateinit var editNameIcon: ImageView
    private lateinit var storage: StorageReference
    private lateinit var editNameEdit: EditText
    private lateinit var currentUser: com.caiosilva.myapplication.helper.FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        profileImageView = findViewById(R.id.profile_image_iv)
        editNameIcon = findViewById(R.id.edit_icon_iv)
        editNameEdit = findViewById(R.id.settings_username)

        val openGallery = findViewById<ImageButton>(R.id.open_gallery_setting_iv)
        val openCameraBtn = findViewById<ImageButton>(R.id.open_camera_setting_iv)

        storage = FirebaseConfig().getStorage()
        currentUser = com.caiosilva.myapplication.helper.FirebaseUser()

        Permissions().validatePermission(permissions, this, 1)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.title = resources.getString(R.string.settings_menu)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        currentUser.getCurrentUser()

        val displayName = currentUser.getUserName()

        editNameIcon.setOnClickListener {
            if(displayName != null) {
                updateUserName(editNameEdit.text.toString())
            }
        }

        if(displayName != null) {
            Log.d("Caio", "$displayName caio")
            editNameEdit.setText(displayName)
        } else {
            editNameEdit.setText("     ")
        }

        val photoUrl = currentUser.getPhotoUrl()
        if (photoUrl != null) {
            Glide.with(this).load(photoUrl).into(profileImageView)
        } else {
            profileImageView.setImageResource(R.drawable.default_avatar_png)
        }

        openCameraBtn.setOnClickListener {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (camera.resolveActivity(packageManager) != null) {
                startActivityForResult(camera, REQUEST_CAMERA)
            } else {
                Toast.makeText(this, R.string.camera_error_access, Toast.LENGTH_LONG).show()
            }
        }

        openGallery.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            if (gallery.resolveActivity(packageManager) != null) {
                startActivityForResult(gallery, REQUEST_GALLERY)
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
            if (permitResult == PackageManager.PERMISSION_DENIED) {
                permitAlert()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var profileImage: Bitmap? = null
        if (resultCode == Activity.RESULT_OK && data != null) {

            when (requestCode) {
                REQUEST_GALLERY -> {
                    val selectedImageUri = data.data
                    profileImage =
                        MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                }
                REQUEST_CAMERA -> {
                    profileImage = data.extras?.get("data") as Bitmap
                }
            }
            if (profileImage != null) {
                profileImageView.setImageBitmap(profileImage)

                val baos = ByteArrayOutputStream()

                profileImage.compress(Bitmap.CompressFormat.JPEG, 70, baos)
                val imageData = baos.toByteArray()
                val storeImage = storage.child("images")
                    .child("profileImg")
                    .child(currentUser.getUserId()!!)
                    .child("profile.jpg")
                val uploadTask = storeImage.putBytes(imageData)
                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw  it
                        }
                    }
                    storeImage.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val imgUrl = task.result
                        if (imgUrl != null) updateUserPhoto(imgUrl)
                    }
                }

                uploadTask.addOnFailureListener {
                    Toast.makeText(this, "Falha ao carregar imagem", Toast.LENGTH_LONG)
                        .show()
                }.addOnSuccessListener {
                    Toast.makeText(this, "Sucesso ao carregar imagem", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateUserPhoto(url: Uri) {
        currentUser.updateUserPhoto(url)
    }

    private fun updateUserName(name: String) {
        currentUser.updateUserName(name, applicationContext)
    }

    private fun permitAlert() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.alert_title)
        alertDialog.setMessage(R.string.alert_permission_text)
        alertDialog.setPositiveButton(
            R.string.alert_ok_bt
        ) { _, _ ->
            finish()
        }
        alertDialog.create().setCancelable(false)
        alertDialog.show()
    }

    companion object {
        const val REQUEST_CAMERA = 100
        const val REQUEST_GALLERY = 200
    }
}
