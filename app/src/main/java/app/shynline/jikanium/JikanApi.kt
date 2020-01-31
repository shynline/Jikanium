package app.shynline.jikanium

import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.requests.bygenre.AnimePageWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/***
 * Retrofit API to access Jikan service
 */
interface JikanApi {
    /***
     * Retrieve an anime by its id
     */
    @GET("anime/{id}/")
    suspend fun getAnime(@Path("id") id: Long): Response<Anime>

    @GET("genre/anime/{genre}/{page}")
    suspend fun getAnimeListByGenre(@Path("genre") genre: Int, @Path("page") page: Int): Response<AnimePageWrapper>
}