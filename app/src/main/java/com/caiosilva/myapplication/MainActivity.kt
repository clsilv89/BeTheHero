package com.caiosilva.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        var login = "email@email.com"
        var password = "12345678"

        mAuth.createUserWithEmailAndPassword(login, password)
        mAuth.signInWithEmailAndPassword(login, password)

        var user = mAuth.currentUser
        var uid = user?.uid

        if(user != null) {
            Log.d("Caio", uid.toString())
        } else {
            Log.d("Caio", "nulo")
        }
    }
}
