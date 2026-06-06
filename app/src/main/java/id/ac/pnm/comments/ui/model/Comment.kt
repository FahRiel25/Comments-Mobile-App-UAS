package id.ac.pnm.comments.ui.model

data class Comment(
    val nama: String,
    val username: String,
    val content: String,
    val time: String,
    var likeCount: Int,
    var isLiked: Boolean = false,
    val replyTo: String? = null
)