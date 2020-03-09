package com.caiosilva.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.caiosilva.myapplication.R
import com.caiosilva.myapplication.config.FirebaseConfig
import com.caiosilva.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val createAccountButton = findViewById<Button>(R.id.create_account_send_btn)
        val userNameEditText = findViewById<EditText>(R.id.create_account_username_et)
        val emailEditText = findViewById<EditText>(R.id.create_account_email_et)
        val passwordEditText = findViewById<EditText>(R.id.create_account_password_et)

        createAccountButton.setOnClickListener {
            val user = User()
            user.name = userNameEditText.text.toString()
            user.email = emailEditText.text.toString()
            user.password = passwordEditText.text.toString()

            validateFields(user)
        }
    }

    private fun validateFields(user: User) {
        if (user.email?.isNotEmpty()!!) {
            if (user.password?.isNotEmpty()!!) {
                if (user.name?.isNotEmpty()!!) {

                    createUser(user)

                } else {
                    Toast.makeText(this, "Campo NOME obrigatório!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Campo SENHA obrigatório!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Campo EMAIL obrigatório!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUser(user: User) {
        authentication = FirebaseConfig().getFirebaseAuth()

        authentication.createUserWithEmailAndPassword(
                user.email.toString(),
                user.password.toString()
            )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUSer(user)
                } else {
                    Log.d("Authentication", task.exception.toString())
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveUSer(user: User) {
        Log.d("Caio", user.name.toString())
    }
}
