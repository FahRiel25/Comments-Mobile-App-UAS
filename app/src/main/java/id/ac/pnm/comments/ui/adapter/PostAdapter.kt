package id.ac.pnm.comments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.R
import id.ac.pnm.comments.ui.model.Post
import android.widget.ImageView
import android.content.Intent
import id.ac.pnm.comments.ui.activity.CommentActivity
import com.google.firebase.firestore.FirebaseFirestore
import id.ac.pnm.comments.ui.database.FavoriteDatabase
import id.ac.pnm.comments.ui.database.FavoriteEntity

class PostAdapter(
    private val posts: MutableList<Post>

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

        val iconDelete: ImageView =
            view.findViewById(R.id.iconDelete)
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

                Thread {
                    val db = FavoriteDatabase.getInstance(holder.itemView.context)
                    db.favoriteDao().delete(
                        FavoriteEntity(
                            id = post.id,
                            nama = post.nama,
                            username = post.username,
                            time = post.time,
                            content = post.content,
                            likeCount = post.likes,
                            commentCount = post.comments
                        )
                    )
                }.start()

            } else {

                post.isLiked = true
                post.likes++

                holder.iconFavorite.setImageResource(
                    R.drawable.ic_favorite_filled
                )

                Thread {
                    val db = FavoriteDatabase.getInstance(holder.itemView.context)
                    db.favoriteDao().insert(
                        FavoriteEntity(
                            id = post.id,
                            nama = post.nama,
                            username = post.username,
                            time = post.time,
                            content = post.content,
                            likeCount = post.likes,
                            commentCount = post.comments
                        )
                    )
                }.start()
            }

            FirebaseFirestore.getInstance()
                .collection("posts")
                .document(post.id)
                .update(
                    mapOf(
                        "likes" to post.likes,
                        "isLiked" to post.isLiked
                    )
                )
            holder.tvLikeCount.text = post.likes.toString()
        }

            holder.iconComment.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                CommentActivity::class.java
            )

            intent.putExtra(
                "postPosition",
                position
            )

            holder.itemView.context.startActivity(intent)
            }

        holder.iconDelete.setOnClickListener {

            val currentPosition = holder.bindingAdapterPosition

            FirebaseFirestore.getInstance()
                .collection("posts")
                .document(post.id)
                .delete()
                .addOnSuccessListener {

                    posts.removeAt(currentPosition)

                    notifyItemRemoved(currentPosition)
                    notifyItemRangeChanged(
                        currentPosition,
                        posts.size
                    )
                }
        }
        }

        override fun getItemCount(): Int {
            return posts.size
        }
    }
