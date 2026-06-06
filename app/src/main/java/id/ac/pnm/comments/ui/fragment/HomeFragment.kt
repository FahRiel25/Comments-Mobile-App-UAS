package id.ac.pnm.comments.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.pnm.comments.R
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.adapter.PostAdapter
import id.ac.pnm.comments.ui.model.Post
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.content.Intent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.pnm.comments.ui.activity.CreatePostActivity
import id.ac.pnm.comments.ui.model.PostRepository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var tvUsername: TextView
    private lateinit var rvPosts: RecyclerView

    private lateinit var fabAddPost: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initViews(view)

        loadUsername()

        setupRecyclerView()

        return view
    }

    private fun initViews(view: View) {
        tvUsername = view.findViewById(R.id.tvUsername)
        rvPosts = view.findViewById(R.id.rvPosts)
        fabAddPost = view.findViewById(R.id.fabAddPost)
    }

    private fun loadUsername() {
        val user = FirebaseAuth.getInstance().currentUser

        val username = user?.displayName ?: "User"

        val text = "Hello, $username"

        val spannable = SpannableString(text)

        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#A855F7")),
            7,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvUsername.text = spannable
    }
    private fun setupRecyclerView() {

        if (PostRepository.posts.isEmpty()) {

            PostRepository.posts.add(
                Post(
                    nama = "Fahriel",
                    username = "@fahriel.dev",
                    time = "2h",
                    content = "sunset hits different when you're at the right place.",
                    likes = 321,
                    comments = 23
                )
            )

            PostRepository.posts.add(
                Post(
                    nama = "Fahriel",
                    username = "@fahriel.dev",
                    time = "1h",
                    content = "coffee + code = peace ☕",
                    likes = 198,
                    comments = 12
                )
            )
        }

        fabAddPost.setOnClickListener {

            val intent =
                Intent(requireContext(), CreatePostActivity::class.java)

            startActivity(intent)
        }

        rvPosts.layoutManager =
            LinearLayoutManager(requireContext())

        rvPosts.adapter =
            PostAdapter(PostRepository.posts)
    }
    override fun onResume() {
        super.onResume()

        rvPosts.adapter =
            PostAdapter(PostRepository.posts)
    }
}