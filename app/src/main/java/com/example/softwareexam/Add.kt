package com.example.softwareexam

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
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

class Add : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val auth : FirebaseAuth = Firebase.auth
        val db = Firebase.firestore

//        val user = auth.currentUser
//        val userid = user?.uid

        val taskname = findViewById<EditText>(R.id.taskname)
        val desc = findViewById<EditText>(R.id.desc)
        val date = findViewById<EditText>(R.id.date)
        val status = findViewById<EditText>(R.id.status)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {

            val tasks = hashMapOf(
                "task" to taskname.text.toString(),
                "description" to desc.text.toString(),
                "duedate" to date.text.toString(),
                "state" to status.text.toString(),
//                "userid" to userid.toString()
            )

            db.collection("TASK")
                .add(tasks)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    Toast.makeText(baseContext,"task added successfully",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    e -> Log.w(TAG, "Error writing document", e)
                    Toast.makeText(baseContext,"Failed to add task",Toast.LENGTH_SHORT).show()
                }

        }

    }
}