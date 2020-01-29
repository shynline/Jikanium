package app.shynline.jikanium.data

import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T> {
    return when (this.code()) {
        200 -> {
            if (this.body() == null) {
                Result.Error(EmptyBody())
            } else
                Result.Success(this.body()!!)
        }
        304 -> {
            Result.NotModified
        }
        400 -> Result.Error(BadRequest())
        404 -> Result.Error(NotFound())
        405 -> Result.Error(MethodNotAllowed())
        429 -> Result.Error(TooManyRequest())
        500 -> Result.Error(InternalServerError())

        else -> {
            Result.Error(Exception("Something went wrong!"))
        }
    }
}

/***
 *
 */
class EmptyBody : Exception("Empty body.")

/***
 *
 */
class BadRequest : Exception("Bad request.")

/***
 *
 */
class NotFound : Exception("Not found.")

/***
 *
 */
class MethodNotAllowed : Exception("Method not allowed.")

/***
 *
 */
class TooManyRequest : Exception("Too many request.")

/***
 *
 */
class InternalServerError : Exception("Internal server error.")