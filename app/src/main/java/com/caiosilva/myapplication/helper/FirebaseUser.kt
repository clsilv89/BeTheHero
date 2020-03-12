package com.caiosilva.myapplication.helper

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.caiosilva.myapplication.config.FirebaseConfig
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

private var firebaseConfig = FirebaseConfig().getFirebaseAuth()

class FirebaseUser {

    fun getUserId(): String? {
        return (firebaseConfig  .currentUser!!.uid)
    }

    fun getUserEmail(): String? {
        return (firebaseConfig.currentUser!!.email)
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseConfig.currentUser
    }

    fun getUserName(): String? {
        return firebaseConfig.currentUser!!.displayName
    }

    fun getPhotoUrl(): Uri? {
        return firebaseConfig.currentUser!!.photoUrl
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

    fun updateUserName(name: String, context: Context): Boolean {
        try{
            val user = getCurrentUser()
            val userProfile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
            user?.updateProfile(userProfile)?.addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(context, "Nome atualizado com sucesso!", Toast.LENGTH_LONG).show()
                    return@addOnCompleteListener
                } else {
                    Log.d("Perfil", "Erro ao atualizar perfil")
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}