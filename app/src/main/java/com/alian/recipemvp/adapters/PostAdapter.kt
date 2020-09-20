package com.alian.recipemvp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alian.recipemvp.R
import com.alian.recipemvp.RecipeActivity
import com.alian.recipemvp.models.Post
import kotlinx.android.synthetic.main.list_item_post.view.*

class PostAdapter(private var posts: List<Post>, private var context: Context) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_post, parent, false)
    )

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(posts[position], context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post, context: Context) {
            itemView.post_title.text = post.title
            itemView.post_detail.text = post.content
            itemView.setOnClickListener {
                context.startActivity(Intent(context, RecipeActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("POST", post)
                })
            }
        }
    }
}