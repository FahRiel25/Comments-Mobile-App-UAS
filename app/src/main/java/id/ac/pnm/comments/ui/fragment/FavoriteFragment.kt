package id.ac.pnm.comments.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.pnm.comments.R
import id.ac.pnm.comments.ui.adapter.FavoriteAdapter
import id.ac.pnm.comments.ui.database.FavoriteDatabase
import id.ac.pnm.comments.ui.model.Post

class FavoriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val rvFavorites = view.findViewById<RecyclerView>(R.id.rvFavorites)
        val favoriteList = mutableListOf<Post>()
        val adapter = FavoriteAdapter(favoriteList)
        rvFavorites.adapter = adapter
        rvFavorites.layoutManager = LinearLayoutManager(requireContext())

        // untuk mengambil data dari room database
        Thread {
            val db = FavoriteDatabase.getInstance(requireContext())
            val data = db.favoriteDao().getAll()

            val list = data.map {
                Post(
                    id = it.id,
                    nama = it.nama,
                    username = it.username,
                    time = it.time,
                    content = it.content,
                    likes = it.likeCount,
                    comments = it.commentCount
                )
            }
            requireActivity().runOnUiThread {
                favoriteList.clear()
                favoriteList.addAll(list)
                adapter.notifyDataSetChanged()
            }
        }.start()
        return view
    }
}