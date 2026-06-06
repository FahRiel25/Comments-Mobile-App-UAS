package id.ac.pnm.comments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.R
import id.ac.pnm.comments.ui.model.Post
import android.widget.ImageView
import android.graphics.Color
import android.widget.Toast

class PostAdapter(
    private val posts: List<Post>

) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName: TextView =
            view.findViewById(R.id.tvName)

        val tvUsername: TextView =
            view.findViewById(R.id.tvUsername)

        val tvTime: TextView =
            view.findViewById(R.id.tvTime)

        val tvContent: TextView =
            view.findViewById(R.id.tvContent)

        val tvLikeCount: TextView =
            view.findViewById(R.id.tvLikeCount)

        val tvCommentCount: TextView =
            view.findViewById(R.id.tvCommentCount)

        val iconFavorite: ImageView =
            view.findViewById(R.id.iconFavorite)

        val iconComment: ImageView =
            view.findViewById(R.id.iconComment)
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

        holder.tvName.text = post.nama
        holder.tvUsername.text = post.username
        holder.tvTime.text = "· ${post.time}"
        holder.tvContent.text = post.content
        holder.tvLikeCount.text = post.likes.toString()
        holder.tvCommentCount.text = post.comments.toString()

        if (post.isLiked) {
            holder.iconFavorite.setImageResource(
                R.drawable.ic_favorite_filled
            )
        } else {
            holder.iconFavorite.setImageResource(
                R.drawable.ic_favorite
            )
        }


            holder.iconFavorite.setOnClickListener {

                if (post.isLiked) {

                    post.isLiked = false
                    post.likes--

                    holder.iconFavorite.setImageResource(
                        R.drawable.ic_favorite
                    )

                } else {

                    post.isLiked = true
                    post.likes++

                    holder.iconFavorite.setImageResource(
                        R.drawable.ic_favorite_filled
                    )
                }

                holder.tvLikeCount.text = post.likes.toString()
            }
        holder.iconComment.setOnClickListener {

            Toast.makeText(
                holder.itemView.context,
                "Open Comments",
                Toast.LENGTH_SHORT
            ).show()

        }
        }

        override fun getItemCount(): Int {
            return posts.size
        }
    }
