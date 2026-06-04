package id.ac.pnm.comments.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.ui.model.Favorite
import id.ac.pnm.comments.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class FavoriteAdapter (val data: List<Favorite>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
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
        val favorite: Favorite = data[position]
        holder.icon_profile.setImageResource(R.drawable.ic_profile)
        holder.tvName.text = favorite.nama
        holder.tvUsername.text = favorite.username
        holder.tvTime.text = "· ${favorite.time}"
        holder.tvContent.text = favorite.content
        holder.icon_favorite.setImageResource(R.drawable.ic_favorite)
        holder.tvLikeCount.text = favorite.likeCount.toString()
        holder.icon_comment.setImageResource(R.drawable.ic_comment)
        holder.tvCommentCount.text = favorite.commentCount.toString()
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