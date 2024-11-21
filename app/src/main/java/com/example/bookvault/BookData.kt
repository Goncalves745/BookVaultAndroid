data class BookData(
    val id: Int,
    val title: String,
    val authors: List<String>?,
    val coverUrl: String? // The URL of the book cover
) {

}

data class Author(val name: String?)

data class Cover(val medium: String?)