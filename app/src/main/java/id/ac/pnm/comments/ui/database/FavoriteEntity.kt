package id.ac.pnm.comments.ui.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val id: String = "",
    val nama: String = "",
    val username: String = "",
    val time: String = "",
    val content: String = "",
    val likeCount: Int = 0,
    val commentCount: Int = 0
)
