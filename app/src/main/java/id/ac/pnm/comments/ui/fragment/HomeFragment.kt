package id.ac.pnm.comments.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.pnm.comments.R
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initViews(view)

        loadUsername()

        return view
    }

    private fun initViews(view: View) {
        tvUsername = view.findViewById(R.id.tvUsername)
    }

    private fun loadUsername() {
        val user = FirebaseAuth.getInstance().currentUser

        val username = user?.displayName ?: "User"

        tvUsername.text = "Hello, $username"
    }

}