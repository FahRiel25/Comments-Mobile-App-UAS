package id.ac.pnm.comments.ui.adapter

import android.content.Intent
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.graphics.Color
import android.content.res.ColorStateList
import com.google.firebase.firestore.FirebaseFirestore
import id.ac.pnm.comments.ui.activity.CommentActivity
import id.ac.pnm.comments.ui.database.FavoriteDatabase
import id.ac.pnm.comments.ui.database.FavoriteEntity
import id.ac.pnm.comments.ui.model.Post

class FavoriteAdapter (val data: MutableList<Post>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FavoriteViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(layout)
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int,
    ) {
        val favorite: Post = data[position]
        holder.icon_profile.setImageResource(R.drawable.ic_profile)
        holder.tvName.text = favorite.nama
        holder.tvUsername.text = favorite.username
        holder.tvTime.text = "· ${favorite.time}"
        holder.tvContent.text = favorite.content
        holder.icon_favorite.setImageResource(R.drawable.ic_favorite_filled)
        holder.tvLikeCount.text = favorite.likes.toString()
        holder.icon_comment.setImageResource(R.drawable.ic_comment)
        holder.tvCommentCount.text = favorite.comments.toString()

        holder.icon_favorite.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition

            // untuk menghapus dari room db
            Thread {
                val db = FavoriteDatabase.getInstance(holder.itemView.context)
                db.favoriteDao().delete(
                    FavoriteEntity(
                        id = favorite.id,
                        nama = favorite.nama,
                        username = favorite.username,
                        time = favorite.time,
                        content = favorite.content,
                        likeCount = favorite.likes,
                        commentCount = favorite.comments
                    )
                )
            }.start()

            //untuk update firestore
            FirebaseFirestore.getInstance()
                .collection("posts")
                .document(favorite.id)
                .update(
                    mapOf(
                        "isLiked" to false,
                        "likes" to favorite.likes -1
                    )
                )

            //  untuk menghapus dari list dan update rv
            data.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
            notifyItemRangeChanged(currentPosition, data.size)
        }

        holder.icon_comment.setOnClickListener {
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
    }

    override fun getItemCount(): Int = data.size
    class FavoriteViewHolder(val row: View): RecyclerView.ViewHolder(row){
        val icon_profile: ImageView = row.findViewById<ImageView>(R.id.icon_profile)
        val tvName: TextView = row.findViewById< TextView>(R.id.tvName)
        val tvUsername: TextView = row.findViewById<TextView>(R.id.tvUsername)
        val tvTime: TextView = row.findViewById<TextView>(R.id.tvTime)
        val tvContent: TextView = row.findViewById<TextView>(R.id.tvContent)
        val icon_favorite: ImageView = row.findViewById<ImageView>(R.id.icon_favorite)
        val tvLikeCount: TextView = row.findViewById<TextView>(R.id.tvLikeCount)
        val icon_comment: ImageView = row.findViewById<ImageView>(R.id.icon_comment)
        val tvCommentCount: TextView = row.findViewById<TextView>(R.id.tvCommentCount)
    }
}