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
import id.ac.pnm.comments.ui.model.Favorite


class FavoriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val rvFavorites = view.findViewById<RecyclerView>(R.id.rvFavorites)
        val favoriteList = getFavorite()
        val adapter = FavoriteAdapter(favoriteList)
        rvFavorites.adapter = adapter
        rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    fun getFavorite(): List<Favorite> {
        val data = mutableListOf<Favorite>()
        data.add(
            Favorite(
                nama = "Fahriel",
                username = "@fahriel.dev",
                time = "2h",
                content = "sunset hits different when you're at the right place",
                likeCount = 321,
                commentCount = 23
            )
        )

        data.add(
            Favorite(
                nama = "alyaaa",
                username = "@alyaa",
                time = "2h",
                content = "city lights✨",
                likeCount = 270,
                commentCount = 15
            )
        )
        data.add(
            Favorite(
                nama = "rinda",
                username = "@rien.daa",
                time = "1h",
                content = "i need this kind of view in my life",
                likeCount = 107,
                commentCount = 23
            )
        )
        data.add(
            Favorite(
                nama = "reza",
                username = "@reza.dev",
                time = "15m",
                content = "just finished a late night coding session. coffe + code = peace☕",
                likeCount = 110,
                commentCount = 23
            )
        )
        data.add(
            Favorite(
                nama = "salwa",
                username = "@slwa.iz",
                time = "10m",
                content = "enjoy the beautiful sunset this afternoon",
                likeCount = 704,
                commentCount = 70
            )
        )
        return data
    }
}