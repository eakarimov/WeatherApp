package dev.eakarimov.data

sealed class RequestResult<E>(open val data: E?) {

    data class InProgress<E>(override val data: E? = null): RequestResult<E>(data)
    data class Success<E>(override val data: E) : RequestResult<E>(data)
    data class Error<E>(override val data: E? = null) : RequestResult<E>(data)
}

fun <I, O> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O> {
    return when (this) {
        is RequestResult.InProgress -> RequestResult.InProgress()
        is RequestResult.Success -> RequestResult.Success(mapper(data))
        is RequestResult.Error -> RequestResult.Error()
    }
}