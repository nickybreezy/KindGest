package com.example.kindgest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.gs_button)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Feed::class.java)
            startActivity(intent)
        }
    }

}
