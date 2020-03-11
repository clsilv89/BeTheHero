package com.caiosilva.myapplication.model

import com.caiosilva.myapplication.config.FirebaseConfig
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude

class User {

    var name: String? = null
    var email: String? = null
    @get:Exclude
    var password: String? = null
    @get:Exclude
    var id: String? = null

    fun saveUser() {
        val ref: DatabaseReference = FirebaseConfig().getFirebaseDatabase()
        val user = ref.child("users").child(id!!)

        user.setValue(this)

    }
}