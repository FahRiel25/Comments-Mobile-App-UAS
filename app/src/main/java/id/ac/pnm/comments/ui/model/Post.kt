package id.ac.pnm.comments.ui.model


data class Post(
    val nama: String,
    val username: String,
    val time: String,
    val content: String,
    var likes: Int,
    var comments: Int,
    var isLiked: Boolean = false
)

