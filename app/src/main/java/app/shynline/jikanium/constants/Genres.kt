package app.shynline.jikanium.constants

data class AnimeGenre(val name: String, val value: Int)

/***
 * List of Anime genres and their value
 */
val animeGenres: List<AnimeGenre> = listOf(
    AnimeGenre("Action", 1),
    AnimeGenre("Adventure", 2),
    AnimeGenre("Cars", 3),
    AnimeGenre("Comedy", 4),
    AnimeGenre("Dementia", 5),
    AnimeGenre("Demons", 6),
    AnimeGenre("Mystery", 7),
    AnimeGenre("Drama", 8),
    AnimeGenre("Ecchi", 9),
    AnimeGenre("Fantasy", 10),
    AnimeGenre("Game", 11),
    AnimeGenre("Hentai", 12),
    AnimeGenre("Historical", 13),
    AnimeGenre("Horror", 14),
    AnimeGenre("Kids", 15),
    AnimeGenre("Magic", 16),
    AnimeGenre("Martial Arts", 17),
    AnimeGenre("Mecha", 18),
    AnimeGenre("Music", 19),
    AnimeGenre("Parody", 20),
    AnimeGenre("Samurai", 21),
    AnimeGenre("Romance", 22),
    AnimeGenre("School", 23),
    AnimeGenre("Sci Fi", 24),
    AnimeGenre("Shoujo", 25),
    AnimeGenre("Shoujo Ai", 26),
    AnimeGenre("Shounen", 27),
    AnimeGenre("Shounen Ai", 28),
    AnimeGenre("Space", 29),
    AnimeGenre("Sports", 30),
    AnimeGenre("Super Power", 31),
    AnimeGenre("Vampire", 32),
    AnimeGenre("Yaoi", 33),
    AnimeGenre("Yuri", 34),
    AnimeGenre("Harem", 35),
    AnimeGenre("Slice Of Life", 36),
    AnimeGenre("Supernatural", 37),
    AnimeGenre("Military", 38),
    AnimeGenre("Police", 39),
    AnimeGenre("Psychological", 40),
    AnimeGenre("Thriller", 41),
    AnimeGenre("Seinen", 42),
    AnimeGenre("Josei", 43)
)