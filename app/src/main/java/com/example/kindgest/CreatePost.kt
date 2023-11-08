package com.example.kindgest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.provider.MediaStore
import android.widget.ImageView
import android.net.Uri
import android.app.Activity
import android.widget.TextView
import android.provider.OpenableColumns

class CreatePost : AppCompatActivity() {
    private val GALLERY_REQUEST_CODE = 123
    private var selectedPhotoUri: Uri? = null
    private lateinit var selectedImageNameTextView: TextView
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CreatePostActivity", "onCreate() called")
        setContentView(R.layout.create_post)
        selectedImageNameTextView = findViewById(R.id.selectedImageName)
        val selectImageButton = findViewById<Button>(R.id.selectImageButton)

        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }

        var closeButton = findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            val closeIntent = Intent(this, Feed::class.java)
            startActivity(closeIntent)
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data

            if (selectedImageUri != null) {
                // Get the filename from the URI
                val cursor = contentResolver.query(selectedImageUri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        selectedImageNameTextView.text = "Selected Image: $displayName"
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu,menu)
        return true
    }




}
