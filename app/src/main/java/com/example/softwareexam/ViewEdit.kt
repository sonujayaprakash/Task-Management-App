package com.example.softwareexam

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewEdit : AppCompatActivity() {

    private lateinit var recycler : RecyclerView
    private lateinit var taskList : ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        taskList = arrayListOf()

        val auth : FirebaseAuth = Firebase.auth
        val db = Firebase.firestore

        val user = auth.currentUser
        val userid = user?.uid

        if (userid != null) {
            db.collection("TASK")
                .whereEqualTo("userid", userid)
                .get()
                .addOnSuccessListener { document ->
                    Log.d("ViewEdit", "Query success. Documents: ${document.size()}")
                    if (document != null) {
                        for (data in document.documents) {
                            val tasks: Task? = data.toObject(Task::class.java)
                            if (tasks != null) {
                                val taskid = tasks.copy(docId = data.id)
                                taskList.add(taskid)
                            }
                        }
                        recycler.adapter = Adapter(taskList)

                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                    Log.e("ViewEdit", "Firestore error: ", exception)
                }
        }
    }
}