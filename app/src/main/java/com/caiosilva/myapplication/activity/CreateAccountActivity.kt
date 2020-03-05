package com.caiosilva.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val createAccountButton: Button = findViewById(R.id.create_account_send_btn)
        val userNameEditText: EditText = findViewById(R.id.create_account_username_et)
        val emailEditText: EditText = findViewById(R.id.create_account_email_et)
        val passwordEditText: EditText = findViewById(R.id.create_account_password_et)
        val name = userNameEditText.text.toString()
        val password = passwordEditText.text.toString()
        val email = emailEditText.text.toString()
//
//        val user = User()
//        user.name = name
//        user.email = email
//        user.password = password

        createAccountButton.setOnClickListener {
            Log.d("Caio", "$name $email $password")
//            validateFields(user)
        }
    }

    private fun validateFields(user: User) {
        if (!user.email?.isEmpty()!!) {
            if (!user.password?.isEmpty()!!) {
                if (!user.name?.isEmpty()!!) {

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
                }
            }
    }

    private fun saveUSer(user: User) {
        Log.d("Caio", user.name.toString())
    }

}
