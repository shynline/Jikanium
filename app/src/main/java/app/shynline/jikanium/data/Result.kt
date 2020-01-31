package app.shynline.jikanium.data


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    /***
     * Result class for the cases in which data is available
     */
    data class Success<out T>(val data: T) : Result<T>()
    /***
     * Result class for the cases which API doesn't have new data
     */
    object NotModified : Result<Nothing>()

    /***
     * Result class for the cases which an error occurred
     */
    data class Error(val exception: Exception) : Result<Nothing>()
    /***
     * Result class for the cases which data is loading from remote server
     */
    object Loading : Result<Nothing>()

    /***
     * Render this object to string
     */
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is NotModified -> "NotModified"
            Loading -> "Loading"
        }
    }
}


/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null
