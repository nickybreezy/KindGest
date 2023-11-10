package com.example.kindgest.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.provider.MediaStore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID
import android.net.Uri
import android.app.Activity
import android.widget.TextView
import android.widget.EditText
import android.provider.OpenableColumns
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import com.example.kindgest.models.PostModel
import com.example.kindgest.R


class CreatePost : AppCompatActivity() {
    private val GALLERY_REQUEST_CODE = 123
    private var selectedPhotoUri: Uri? = null
    private lateinit var selectedImageNameTextView: TextView
    private lateinit var database: DatabaseReference
    private lateinit var btnUploadPost : Button
    private lateinit var TitleText: EditText
    private lateinit var DescText: EditText
    private lateinit var CategoryText: EditText
    private lateinit var LocationText: EditText
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CreatePostActivity", "onCreate() called")
        setContentView(R.layout.create_post)

        val selectImageButton = findViewById<Button>(R.id.selectImageButton)

        selectedImageNameTextView = findViewById(R.id.selectedImageName)
        TitleText = findViewById(R.id.TitleText)
        DescText = findViewById(R.id.DescText)
        CategoryText = findViewById(R.id.CategoryText)
        LocationText = findViewById(R.id.LocationText)
        btnUploadPost = findViewById(R.id.btnUploadPost)
        dbRef = FirebaseDatabase.getInstance("https://kindgest-edabe-default-rtdb.europe-west1.firebasedatabase.app").getReference("Posts")
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        btnUploadPost.setOnClickListener {
      savePostData()
        }

//        database = Firebase.database.reference
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
    private fun savePostData() {
        Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
        Log.d("CreatePostActivity", "Saving post data")
        val image = selectedPhotoUri.toString()
        val title = TitleText.text.toString()
        val desc = DescText.text.toString()
        val category = CategoryText.text.toString()
        val location = LocationText.text.toString()

        if(selectedPhotoUri == null){
            selectedImageNameTextView.error = "Please select an image"
            Log.e("CreatePostActivity", "Image is null")
            return
        }
        Log.d("CreatePostActivity", "Image: $image, Title: $title, Desc: $desc, Category: $category, Location: $location")
        if (title.isEmpty()) {
            TitleText.error = "Please enter a title"
            return
        }
        if (desc.isEmpty()) {
            DescText.error = "Please enter a description"
            return
        }
        if (category.isEmpty()) {
            CategoryText.error = "Please enter a category"
            return
        }
        if (location.isEmpty()) {
            LocationText.error = "Please enter a location"
            return
        }

        val postID = dbRef.push().key!!
        val post = PostModel(postID, image, title, desc, category, location)

        if (postID != null) {
            dbRef.child(postID).setValue(post).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Post added successfully", Toast.LENGTH_LONG).show()

                    // Upload the image to Firebase Storage
                    val filename = UUID.randomUUID().toString()
                    val imageRef = storageRef.child("images/$filename")

                    imageRef.putFile(selectedPhotoUri!!)
                        .addOnSuccessListener {
                            // Get the download URL for the uploaded image
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                val imageUrl = uri.toString()

                                // Update the post with the image URL
                                dbRef.child(postID).child("image").setValue(imageUrl)

                                // Clear UI and reset variables
                                selectedPhotoUri = null
                                TitleText.setText("")
                                DescText.setText("")
                                CategoryText.setText("")
                                LocationText.setText("")
                                TitleText.requestFocus()
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Handle the error
                            Toast.makeText(this, "Failed to upload image: ${exception.message}", Toast.LENGTH_LONG).show()
                        }
                } else {
                    Toast.makeText(this, "Failed to add post: " + task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            println("Error")
            Toast.makeText(this, "Failed to add post", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data

            if (selectedImageUri != null) {
                selectedPhotoUri = selectedImageUri
                val cursor = contentResolver.query(selectedImageUri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val displayNameColumnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)

                        if (displayNameColumnIndex != -1) {
                            val displayName = it.getString(displayNameColumnIndex)
                            selectedImageNameTextView.text = "Selected Image: $displayName"
                        } else {
                            selectedImageNameTextView.text = "Selected Image"
                        }
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
