package com.example.kindgest.activities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kindgest.R
import com.example.kindgest.adapters.PostAdapter
import com.example.kindgest.models.PostModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Feed : AppCompatActivity() {

    private lateinit var postRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var postList: ArrayList<PostModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed)
        val buttonListingClick = findViewById<Button>(R.id.add_listing)
        buttonListingClick.setOnClickListener {
            val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
        }
        postRecyclerView = findViewById(R.id.rvPosts)
        postRecyclerView.layoutManager = GridLayoutManager(this, 2)
        postRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        postList = arrayListOf<PostModel>()
        getPostData()

    }



    private fun getPostData() {

        postRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Posts")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                if (snapshot.exists()){
                    for (postSnap in snapshot.children){
                        val postData = postSnap.getValue(PostModel::class.java)
                        postList.add(postData!!)
                    }
                    val mAdapter = PostAdapter(postList)
                    postRecyclerView.adapter = mAdapter
                    postRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      when (item.itemId) {
          R.id.feed -> {
              Toast.makeText(this, "Menu Item is Pressed", Toast.LENGTH_SHORT).show()
              val intent = Intent(this, CreatePost::class.java)
              startActivity(intent)
              return true
          }
      }
      return super.onOptionsItemSelected(item)
      }
    }

