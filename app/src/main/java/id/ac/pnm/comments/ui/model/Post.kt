package id.ac.pnm.comments.ui.model


data class Post(
    val id: String = "",
    val nama: String = "",
    val username: String = "",
    val time: String = "",
    val content: String = "",
    var likes: Int = 0,
    var comments: Int = 0,
    var isLiked: Boolean = false
)

