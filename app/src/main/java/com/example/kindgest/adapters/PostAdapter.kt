package com.example.kindgest.adapters
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.kindgest.models.PostModel
import com.example.kindgest.R
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
class PostAdapter (private val postList: ArrayList<PostModel>) : RecyclerView.Adapter <PostAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currentPost = postList[position]
        holder.tvPostTitle.text = currentPost.title
        Picasso.get()
            .load(currentPost.image) // assuming currentPost.image is a URL
            .placeholder(R.drawable.placeholder) // placeholder image while loading
            .error(R.drawable.error) // image to show in case of error
            .into(holder.ivPostImage)
        holder.tvLocation.text = currentPost.location
    }



    override fun getItemCount(): Int {
return  postList.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
val tvPostTitle : TextView = itemView.findViewById(R.id.tvPostTitle)
  val ivPostImage : ImageView = itemView.findViewById(R.id.ivPostImage)
    val tvLocation : TextView = itemView.findViewById(R.id.tvLocation)
    }

}
