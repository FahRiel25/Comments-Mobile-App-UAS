package id.ac.pnm.comments.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.R
import id.ac.pnm.comments.ui.adapter.CommentAdapter
import id.ac.pnm.comments.ui.model.Comment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import id.ac.pnm.comments.ui.model.PostRepository
import android.widget.ImageView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth



class CommentActivity : AppCompatActivity() {
    private var replyingTo: String? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val btnBack = findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        val etComment = findViewById<EditText>(R.id.etComment)
        etComment.hint = "Write a comment..."

        val btnSend = findViewById<Button>(R.id.btnSend)

        val rvComments = findViewById<RecyclerView>(R.id.rvComments)

        val postPosition =
            intent.getIntExtra("postPosition", 0)

        val post =
            PostRepository.posts[postPosition]

        val tvPostName =
            findViewById<TextView>(R.id.tvPostName)

        val tvPostUsername =
            findViewById<TextView>(R.id.tvPostUsername)

        val tvPostContent =
            findViewById<TextView>(R.id.tvPostContent)

        val tvLikeCount =
            findViewById<TextView>(R.id.tvLikeCount)

        val tvCommentCountHeader =
            findViewById<TextView>(R.id.tvCommentCountHeader)

        val tvCommentCount =
            findViewById<TextView>(R.id.tvCommentCount)

        tvPostName.text = post.nama

        tvPostUsername.text =
            "${post.username} · ${post.time}"

        tvPostContent.text =
            post.content

        tvLikeCount.text = post.likes.toString()

        tvCommentCountHeader.text =
            post.comments.toString()

        tvCommentCount.text =
            "${post.comments} Comments"

        val comments = mutableListOf<Comment>()

        rvComments.layoutManager = LinearLayoutManager(this)

        val adapter = CommentAdapter(comments) { username ->

            replyingTo = username
            etComment.hint = "Replying to $username"
            etComment.requestFocus()
        }

        rvComments.layoutManager = LinearLayoutManager(this)
        rvComments.adapter = adapter

        db.collection("comments")
            .whereEqualTo("postId", post.id)
            .get()
            .addOnSuccessListener { documents ->

                comments.clear()

                for (document in documents) {

                    comments.add(
                        Comment(
                            id = document.id,
                            document.getString("name") ?: "",
                            document.getString("username") ?: "",
                            document.getString("content") ?: "",
                            "now",
                            document.getLong("likeCount")?.toInt() ?: 0,
                            document.getBoolean("isLiked") ?: false
                        )
                    )
                }

                adapter.notifyDataSetChanged()
            }

        btnSend.setOnClickListener {

            val text = etComment.text.toString()

            if (text.isNotEmpty()) {

                val user = FirebaseAuth.getInstance().currentUser
                val username = user?.displayName ?: "User"

                val commentData = hashMapOf(
                    "postId" to post.id,
                    "username" to "@$username",
                    "name" to username,
                    "content" to text,
                    "timestamp" to System.currentTimeMillis(),
                    "likeCount" to 0,
                    "isLiked" to false
                )

                db.collection("comments")
                    .add(commentData)
                    .addOnSuccessListener {

                        comments.add(

                            Comment(
                                id = "",
                                nama = username,
                                username = "@$username",
                                content = text,
                                time = "now",
                                likeCount = 0,
                                isLiked = false,
                                replyTo = replyingTo
                            )
                        )

                        adapter.notifyItemInserted(
                            comments.size - 1
                        )

                        rvComments.scrollToPosition(
                            comments.size - 1
                        )

                        etComment.text.clear()
                        replyingTo = null
                        etComment.hint = "Write a comment..."

                        db.collection("posts")
                            .document(post.id)
                            .update(
                                "comments",
                                post.comments + 1
                            )
                    }
            }
        }



    }
}