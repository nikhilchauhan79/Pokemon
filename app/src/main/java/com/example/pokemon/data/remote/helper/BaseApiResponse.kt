package com.example.pokemon.data.remote.helper

import retrofit2.Response

abstract class BaseApiResponse {
  suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
  ): NetworkResult<T> {
    return try {
      val response = apiCall()
      if (response.isSuccessful) {
        response.body()?.let {
          return NetworkResult.Success(it)
        }
      }
      error("${response.code()} ${response.message()} ${response.errorBody()}")
    } catch (exception: Exception) {
      error("${exception.message} ${exception.localizedMessage}")
    }
  }

  private fun <T> error(message: String): NetworkResult<T> =
    NetworkResult.Error(message = message)
}