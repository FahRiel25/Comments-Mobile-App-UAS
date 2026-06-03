package id.ac.pnm.comments.ui.model

data class Post (
    val username : String,
    val content : String,
    var likes : Int,
    var comments : Int

)

