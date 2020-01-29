package app.shynline.jikanium

import app.shynline.jikanium.data.anime.Anime
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
}