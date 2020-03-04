package com.caiosilva.myapplication.config

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseConfig {
    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    fun getFirebaseDatabase() : DatabaseReference {
        if(database == null) {
            database = FirebaseDatabase.getInstance().reference
        }
        return database
    }

    fun getFirebaseAuth() : FirebaseAuth {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance()
        }
        return mAuth
    }
}