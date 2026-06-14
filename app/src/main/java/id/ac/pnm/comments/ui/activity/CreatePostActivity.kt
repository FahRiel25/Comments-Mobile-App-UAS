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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class CreatePostActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_post)

        val btnClose = findViewById<ImageView>(R.id.btnClose)
        val tvCounter = findViewById<TextView>(R.id.tvCounter)
        btnClose.setOnClickListener { finish() }
        val etCaption = findViewById<EditText>(R.id.etCaption)
        val btnPost = findViewById<Button>(R.id.btnPost)

        if (btnPost == null) {
            throw RuntimeException("btnPost NULL")
        }

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

            val user = FirebaseAuth.getInstance().currentUser
            val username = user?.displayName ?: "User"
            val caption = etCaption.text.toString().trim()

            if (caption.isNotEmpty()) {

                val post = hashMapOf(
                    "name" to username,
                    "username" to "@$username",
                    "content" to caption,
                    "likes" to 0,
                    "comments" to 0,
                    "timestamp" to System.currentTimeMillis(),
                    "likedUsers" to emptyList<String>()
                )

                db.collection("posts")
                    .add(post)
                    .addOnSuccessListener {
                        finish()
                    }
            }
        }
        }
    }
