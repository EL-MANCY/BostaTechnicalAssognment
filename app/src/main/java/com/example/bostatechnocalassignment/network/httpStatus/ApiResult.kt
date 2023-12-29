package com.example.bostatechnocalassignment.network.httpStatus

sealed class ApiResult <out T> (val status: ApiStatus, val data: T?, val message:String?) {

    data class Success<out S>(val _data: S?) : ApiResult<S>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )
    data class Error(val exception: String) : ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception
    )
    data class Loading<out S>(val _data: S?, val isLoading: Boolean): ApiResult<S>(
        status = ApiStatus.LOADING,
        data = _data,
        message = null
    )
}