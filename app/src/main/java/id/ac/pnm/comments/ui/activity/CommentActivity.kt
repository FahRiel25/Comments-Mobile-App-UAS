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



class CommentActivity : AppCompatActivity() {
    private var replyingTo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val etComment = findViewById<EditText>(R.id.etComment)
        etComment.hint = "Write a comment..."
        val btnSend = findViewById<Button>(R.id.btnSend)

        val rvComments = findViewById<RecyclerView>(R.id.rvComments)


        val comments = mutableListOf(
            Comment(
                "Alya",
                "@alya",
                "this is so truee ✨",
                "2h",
                18
            ),
            Comment(
                "reza.dev",
                "@reza.dev",
                "where is this place? looks amazing!",
                "1h",
                9
            ),
            Comment(
                "indriii",
                "@indriii",
                "i need this kind of view in my life 🌄",
                "1h",
                7
            )
        )

        rvComments.layoutManager = LinearLayoutManager(this)

        val adapter = CommentAdapter(comments) { username ->

            replyingTo = username
            etComment.hint = "Replying to $username"
            etComment.requestFocus()
        }

        rvComments.layoutManager = LinearLayoutManager(this)
        rvComments.adapter = adapter


        btnSend.setOnClickListener {

            val text = etComment.text.toString()

            if (text.isNotEmpty()) {

                comments.add(
                    Comment(
                        "You",
                        "@you",
                        text,
                        "now",
                        0,
                        false,
                        replyingTo
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
            }
        }
    }
}