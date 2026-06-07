package id.ac.pnm.comments.ui.model

data class Favorite(
    val id: String = "",
    val nama: String = "",
    val username: String = "",
    val time: String = "",
    val content: String = "",
    val likeCount: Int = 0,
    val commentCount: Int = 0
)
