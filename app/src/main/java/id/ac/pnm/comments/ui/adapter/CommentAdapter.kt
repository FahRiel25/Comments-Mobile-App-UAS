package id.ac.pnm.comments.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.R
import id.ac.pnm.comments.ui.model.Comment
import android.widget.ImageView

class CommentAdapter(
    private val comments: List<Comment>,
    private val onReplyClick: (String) -> Unit,
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvContent: TextView = view.findViewById(R.id.tvContent)
        val tvLikeCount: TextView = view.findViewById(R.id.tvLikeCount)

        val ivLike: ImageView = view.findViewById(R.id.ivLike)

        val tvReplyTo: TextView =
            view.findViewById(R.id.tvReplyTo)

        val tvReply: TextView =
            view.findViewById(R.id.tvReply)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)

        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CommentViewHolder,
        position: Int
    ) {
        val comment = comments[position]

        holder.tvName.text = comment.nama
        holder.tvUsername.text = comment.username
        holder.tvTime.text = "· ${comment.time}"
        if (comment.replyTo != null) {

            holder.tvReplyTo.visibility = View.VISIBLE
            holder.tvReplyTo.text =
                "Replying to ${comment.replyTo}"

        } else {

            holder.tvReplyTo.visibility = View.GONE
        }
        holder.tvContent.text = comment.content
        if (comment.isLiked) {
            holder.ivLike.setImageResource(
                R.drawable.ic_favorite_filled
            )
        } else {
            holder.ivLike.setImageResource(
                R.drawable.ic_favorite
            )
        }

        holder.tvLikeCount.text =
            comment.likeCount.toString()


        holder.ivLike.setOnClickListener {

            if (comment.isLiked) {
                comment.likeCount--
                comment.isLiked = false
            } else {
                comment.likeCount++
                comment.isLiked = true
            }

            notifyItemChanged(position)
        }

        holder.tvReply.setOnClickListener {
            onReplyClick(comment.username)
        }


    }


    override fun getItemCount(): Int {
        return comments.size
    }


}