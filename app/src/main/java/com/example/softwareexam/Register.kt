package com.example.softwareexam

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private var auth : FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = Firebase.firestore

        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email2)
        val password = findViewById<EditText>(R.id.password2)
        val button = findViewById<Button>(R.id.register)

        button.setOnClickListener {

            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val userid = user?.uid

                        val users = hashMapOf(
                            "name" to name.text.toString(),
                            "email" to email.text.toString(),
                            "password" to password.text.toString(),
                        )

                        if (userid != null) {
                            db.collection("USERS")
                                .document(userid)
                                .set(users)
                                .addOnSuccessListener {
                                    Log.d(TAG, "DocumentSnapshot successfully written!")
                                    Toast.makeText(baseContext, "Data inserted.Successful registration", Toast.LENGTH_SHORT,).show()
                                }
                                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e)
                                }
                        }

                        startActivity(Intent(this, Home::class.java))



                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

        }

    }
}