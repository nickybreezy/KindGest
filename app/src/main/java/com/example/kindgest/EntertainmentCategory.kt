package com.example.kindgest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class EntertainmentCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entertainment_category)

        val closeButton = findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            val closeIntent = Intent(this, Categories::class.java)
            startActivity(closeIntent)
        }
    }
}
