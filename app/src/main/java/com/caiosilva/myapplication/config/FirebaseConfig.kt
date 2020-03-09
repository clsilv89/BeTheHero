package com.caiosilva.myapplication.config

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseConfig {
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    fun getFirebaseDatabase(): DatabaseReference {
        mDatabase = FirebaseDatabase.getInstance().reference
        return mDatabase
    }

    fun getFirebaseAuth(): FirebaseAuth {
        mAuth = FirebaseAuth.getInstance()
        return mAuth
    }
}