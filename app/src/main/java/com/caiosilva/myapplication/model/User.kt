package com.caiosilva.myapplication.model

import com.caiosilva.myapplication.config.FirebaseConfig
import com.caiosilva.myapplication.helper.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase

class User {

    var name: String? = null
    var email: String? = null
    @get:Exclude
    var password: String? = null
    var id: String? = null
    var photo: String? = null

    fun saveUser() {
        val ref: DatabaseReference = FirebaseConfig().getFirebaseDatabase()
        val user = ref.child("users").child(id!!)

        user.setValue(this)
    }

    fun updateUser() {
        val userId = FirebaseUser().getUserId()
        val firebase = FirebaseConfig().getFirebaseDatabase()

        val userRef = firebase.child("users").child(userId!!)

        val userValues = mapConverter()

        userRef.updateChildren(userValues)
    }

    @Exclude
    fun mapConverter( ): HashMap<String, Any> {
        val usuarioMap = HashMap<String, Any>()
        usuarioMap["email"] = email!!
        usuarioMap["name"] = name!!
        usuarioMap["photo"] = photo!!

        return usuarioMap
    }
}