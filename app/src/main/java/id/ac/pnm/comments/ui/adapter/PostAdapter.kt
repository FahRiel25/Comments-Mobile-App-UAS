package id.ac.pnm.comments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.R
import id.ac.pnm.comments.ui.model.Post

class PostAdapter(
    private val posts: List<Post>

) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView =
            view.findViewById(R.id.tvPostUsername)

        val tvContent: TextView =
            view.findViewById(R.id.tvPostContent)

        val tvLikes: TextView =
            view.findViewById(R.id.tvLikes)

        val tvComments: TextView =
            view.findViewById(R.id.tvComments)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)

        return PostViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {

        val post = posts[position]

        holder.tvUsername.text = post.username
        holder.tvContent.text = post.content
        holder.tvLikes.text = "💜 ${post.likes}"
        holder.tvComments.text = "💬 ${post.comments}"
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}