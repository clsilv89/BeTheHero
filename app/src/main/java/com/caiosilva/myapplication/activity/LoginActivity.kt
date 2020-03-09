package com.caiosilva.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.config.FirebaseConfig
import com.caiosilva.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val createAccount = findViewById<TextView>(R.id.login_create_account_tv)
        val userEmail = findViewById<EditText>(R.id.login_user_et)
        val password = findViewById<EditText>(R.id.login_password_et)
        val loginBtn = findViewById<Button>(R.id.login_send_btn)

        createAccount.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }

        loginBtn.setOnClickListener {
            val user = User()

            user.email = userEmail.text.toString()
            user.password = password.text.toString()

            logUser(user)
        }
    }

    private fun logUser(user: User) {
        authentication = FirebaseConfig().getFirebaseAuth()

        authentication.signInWithEmailAndPassword(
                user.email.toString(),
                user.password.toString()
            )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = authentication.currentUser
                    Log.d("Caio", currentUser?.uid!!)
                } else {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }
}
