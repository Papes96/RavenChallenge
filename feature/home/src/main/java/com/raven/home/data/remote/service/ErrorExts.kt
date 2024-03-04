package com.raven.home.data.remote.service

import retrofit2.HttpException

private const val HTTP_NOT_FOUND = 404
private const val HTTP_UNAUTHORIZED = 401
private const val TOO_MANY_REQUESTS = 429

fun Throwable.mapToHttpError(): Throwable = when (val error = this) {
    is HttpException -> handleHttpException(error)
    else -> UnknownApiException(cause = error)
}

private fun handleHttpException(error: HttpException) = when (error.code()) {
    HTTP_UNAUTHORIZED -> UnauthenticatedException(message = error.message(), cause = error)
    HTTP_NOT_FOUND -> NotFoundException(message = error.message(), cause = error)
    TOO_MANY_REQUESTS -> TooManyRequestsException(message = error.message(), cause = error)

    else -> {
        UnknownApiException(message = error.message(), cause = error)
    }
}


/** The base class for error responses from the server */
abstract class ApiException(
    message: String? = null,
    cause: Throwable? = null
) : Error(message, cause)

/** An api call required an authentication but none was provided in the request */
class UnauthenticatedException(
    message: String = "No authentication provided",
    cause: Throwable? = null
) : ApiException(message, cause)

/** The server has returned an error response that is not handled by the application */
class UnknownApiException(
    message: String? = null,
    cause: Throwable? = null
) : ApiException(message, cause)

/** The requested resource could not be found */
class NotFoundException(
    message: String? = null,
    cause: Throwable? = null
) : ApiException(message, cause)

/** The user has sent too many requests in a given amount of time. Intended for use with rate limiting schemes.*/
class TooManyRequestsException(
    message: String? = null,
    cause: Throwable? = null
) : ApiException(message, cause)