package com.caiosilva.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.config.FirebaseConfig
import com.caiosilva.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var authentication: FirebaseAuth
    private lateinit var currentUser : FirebaseUser

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

            validateFields(user)
        }
    }

    private fun validateFields(user: User) {
        if (user.email?.isNotEmpty()!!) {
            if (user.password?.isNotEmpty()!!) {

                logUser(user)

            } else {
                Toast.makeText(this, "Campo SENHA obrigatório!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Campo EMAIL obrigatório!", Toast.LENGTH_SHORT).show()
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
                    currentUser = authentication.currentUser!!

                    callLoggedScreen(currentUser)
                } else {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun callLoggedScreen(currentUser: FirebaseUser) {
        Log.d("Caio", currentUser.uid)
        startActivity(Intent(this, MainActivity::class.java))
    }
}
