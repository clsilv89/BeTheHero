package com.caiosilva.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.config.FirebaseConfig
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val createAccountButton: Button = findViewById(R.id.create_account_send_btn)
        val userNameEditText: EditText = findViewById(R.id.create_account_username_et)
        val emailEditText: EditText = findViewById(R.id.create_account_email_et)
        val passwordEditText: EditText = findViewById(R.id.create_account_password_et)
        val name = userNameEditText.text.toString()
        val password = passwordEditText.text.toString()
        val email = emailEditText.text.toString()

        createAccountButton.setOnClickListener {
            validateFields(name, password, email)
        }
    }

    fun validateFields(name: String, password: String, email: String) {
        if (!name.isEmpty()) {
            return
        } else {
            Toast.makeText(this, "Campo NOME obrigatório!", Toast.LENGTH_SHORT).show()
        }
        if (!password.isEmpty()) {
            return
        } else {
            Toast.makeText(this, "Campo SENHA obrigatório!", Toast.LENGTH_SHORT).show()
        }
        if (!email.isEmpty()) {
            return
        } else {
            Toast.makeText(this, "Campo EMAIL obrigatório!", Toast.LENGTH_SHORT).show()
        }

    }

    fun createUser(name: String, password: String, email: String) {
        authentication = FirebaseConfig().getFirebaseAuth()
        authentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                } else {

                }
            }
    }

    fun saveUSer(name: String, password: String, email: String) {

    }

}
