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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source


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

    private val db = FirebaseFirestore.getInstance()


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

        loadPosts()

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
        fabAddPost.setOnClickListener {
            startActivity(
                Intent(requireContext(), CreatePostActivity::class.java)
            )
        }

        rvPosts.layoutManager =
            LinearLayoutManager(requireContext())

    }

    private fun loadPosts() {

        db.collection("posts")
            .orderBy("timestamp")
            .get(Source.SERVER)
            .addOnSuccessListener { result ->

                PostRepository.posts.clear()

                for (document in result) {

                    val uid = FirebaseAuth.getInstance()
                        .currentUser
                        ?.uid

                    val likedUsers =
                        document.get("likedUsers") as? List<String>
                            ?: emptyList()

                    val isLiked =
                        likedUsers.contains(uid)

                    val content =
                        document.getString("content") ?: ""

                    PostRepository.posts.add(
                        Post(
                            id = document.id,
                            nama = document.getString("name") ?: "User",
                            username = document.getString("username") ?: "@user",
                            time = "now",
                            content = document.getString("content") ?: "",
                            likes = document.getLong("likes")?.toInt() ?: 0,
                            comments = document.getLong("comments")?.toInt() ?: 0,
                            isLiked = isLiked
                        )
                    )
                }

                rvPosts.adapter =
                    PostAdapter(PostRepository.posts)
            }
    }

    override fun onResume() {
        super.onResume()
        loadPosts()
    }
}