package com.example.kindgest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Categories : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories)

        val HomeButton = findViewById<Button>(R.id.HomeArrowButton)
        val EntertainmentButton = findViewById<Button>(R.id.EntertainmentArrowButton)
        val Clothing = findViewById<Button>(R.id.ClothingArrowButton)
        val Pet_Supplies = findViewById<Button>(R.id.PetSuppliesArrowButton)
        val Hobbies = findViewById<Button>(R.id.HobbiesArrowButton)
        HomeButton.setOnClickListener {
            val intent = Intent(this, HomeCategory::class.java)
            startActivity(intent)
        }
        EntertainmentButton.setOnClickListener {
            val intent = Intent(this, EntertainmentCategory::class.java)
            startActivity(intent)
        }
        Clothing.setOnClickListener {
            val intent = Intent(this, ClothingCategory::class.java)
            startActivity(intent)
        }
        Pet_Supplies.setOnClickListener {
            val intent = Intent(this, PetSuppliesCategory::class.java)
            startActivity(intent)
        }
        Hobbies.setOnClickListener {
            val intent = Intent(this, HobbiesCategory::class.java)
            startActivity(intent)
        }
    }
}