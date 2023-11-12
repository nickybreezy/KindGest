package com.example.kindgest.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.kindgest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.gs_button)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Feed::class.java)
            startActivity(intent)
        }

        val user1 = User("user1", "password1")
        val user2 = User("user2", "password2")

        val userManager = UserManager(this)

        userManager.saveUser(user1)
        userManager.saveUser(user2)
    }


    data class User(val username:String, val password: String)

    class UserManager(private val context: Context){
        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        fun saveUser(user: User){
            val editor = sharedPreferences.edit()
            editor.putString("username", user.username)
            editor.putString("password", user.password)
            editor.apply()
        }

        fun getUser(): User?{
            val username = sharedPreferences.getString("username", null)
            val password = sharedPreferences.getString("password", null)

            return if (username != null && password != null){
                User(username, password)
            }else{
                null
            }
        }
    }

    fun onUser1ButtonClick(view: View){
        val userManager = UserManager(this)
        val user1 = userManager.getUser()

        if(user1 != null){
            Toast.makeText(this, "User 1 clicked: ${user1.username}", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "User 1 not found", Toast.LENGTH_SHORT).show()
        }
    }

    fun onUser2ButtonClick(view: View){
        val userManager = UserManager(this)
        val user2 = userManager.getUser()

        if(user2 != null){
            Toast.makeText(this, "User 2 clicked: ${user2.username}", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "User 2 not found", Toast.LENGTH_SHORT).show()
        }
    }
}
