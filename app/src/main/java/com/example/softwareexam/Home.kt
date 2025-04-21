package com.example.softwareexam

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val profile = findViewById<TextView>(R.id.profile)
        val add = findViewById<TextView>(R.id.add)
        val view = findViewById<TextView>(R.id.tasks)

        profile.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }

        add.setOnClickListener {
            startActivity(Intent(this,Add::class.java))
        }

        view.setOnClickListener {
            startActivity(Intent(this,ViewEdit::class.java))
        }

    }
}