package id.ac.pnm.comments.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.pnm.comments.R
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import id.ac.pnm.comments.ui.auth.LoginActivity

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // untuk ambil data user dari firebase auth
        val user = FirebaseAuth.getInstance().currentUser
        val textView1 = view.findViewById<TextView>(R.id.textView1)
        val textView2 = view.findViewById<TextView>(R.id.textView2)
        val textView3 = view.findViewById<TextView>(R.id.textView3)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)

        // menampilkan data user
        user?.let {
            val name = it.displayName?: "User"
            val email = it.email?: "-"
            val username = "@${name.lowercase().replace(" ", "")}"

            textView1.text = name
            textView2.text = username
            textView3.text = username
            tvEmail.text = email
        }
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }
        return view
    }
}