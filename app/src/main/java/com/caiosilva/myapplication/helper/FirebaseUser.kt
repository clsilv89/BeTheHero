package com.caiosilva.myapplication.helper

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.caiosilva.myapplication.config.FirebaseConfig
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

private var firebaseConfig = FirebaseConfig()

class FirebaseUser {

    fun getUserId(): String? {
        return (firebaseConfig.getFirebaseAuth().currentUser!!.uid)
    }

    fun getUserEmail(): String? {
        return (firebaseConfig.getFirebaseAuth().currentUser!!.email)
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseConfig.getFirebaseAuth().currentUser
    }

    fun getPhotoUrl(): Uri? {
        return firebaseConfig.getFirebaseAuth().currentUser!!.photoUrl
    }

    fun updateUserPhoto(url: Uri): Boolean {
        try {
            val user = getCurrentUser()
            val userProfile = UserProfileChangeRequest.Builder().setPhotoUri(url).build()
            user?.updateProfile(userProfile)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    return@addOnCompleteListener
                } else {
                    Log.d("Perfil", "Erro ao atualizar foto")
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}