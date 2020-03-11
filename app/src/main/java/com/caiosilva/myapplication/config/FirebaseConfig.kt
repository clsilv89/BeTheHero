package com.caiosilva.myapplication.config

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseConfig {
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: StorageReference

    fun getFirebaseDatabase(): DatabaseReference {
        mDatabase = FirebaseDatabase.getInstance().reference
        return mDatabase
    }

    fun getFirebaseAuth(): FirebaseAuth {
        mAuth = FirebaseAuth.getInstance()
        return mAuth
    }

    fun getFirestore(): StorageReference {
        mFirestore = FirebaseStorage.getInstance().reference
        return mFirestore
    }
}