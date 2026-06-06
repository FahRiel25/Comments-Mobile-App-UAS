package id.ac.pnm.comments.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import id.ac.pnm.comments.R
import id.ac.pnm.comments.ui.model.Post
import id.ac.pnm.comments.ui.model.PostRepository
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView

class CreatePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_post)

        val btnClose = findViewById<ImageView>(R.id.btnClose)
        val tvCounter = findViewById<TextView>(R.id.tvCounter)
        btnClose.setOnClickListener { finish() }
        val etCaption = findViewById<EditText>(R.id.etCaption)
        val btnPost = findViewById<Button>(R.id.btnPost)

        etCaption.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                tvCounter.text = "${s?.length ?: 0}/500"
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnPost.setOnClickListener {

            val caption = etCaption.text.toString()

            if (caption.isNotEmpty()) {
                PostRepository.posts.add(
                    0,
                    Post(
                        nama = "You",
                        username = "@you",
                        time = "now",
                        content = caption,
                        likes = 0,
                        comments = 0
                    )
                )

                finish()
            }
            }
        }
    }
