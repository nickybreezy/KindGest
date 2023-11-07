package com.example.kindgest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.content.Intent
import android.widget.Button

class Feed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed)
        val buttonListingClick = findViewById<Button>(R.id.add_listing)
        buttonListingClick.setOnClickListener {
            val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_post -> {

                val intent = Intent(this, CreatePost::class.java)
                startActivity(intent)
                return true
            }


            R.id.feed -> {
                val intent = Intent(this, Feed::class.java)
                startActivity(intent)
                return true

            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == R.id.create_post) {
//            val intent = Intent(
//                this, CreatePost ::class.java)
//            startActivity (intent)
//        }
//        return true
//    }

}
