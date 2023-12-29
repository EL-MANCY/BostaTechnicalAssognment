package com.example.bostatechnocalassignment.baseViewModel


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.bostatechnocalassignment.network.httpStatus.ApiResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    fun <T> handleFlowResponse(apiRequest: suspend () -> Response<T>) =
        try {
            flow<ApiResult<T>> {
                if (hasInternetConnection()) {
                    emit(ApiResult.Loading(null, true))
                    val response = withContext(Dispatchers.IO) {
                        try {
                            apiRequest()
                        } catch (e: Exception) {
                            Response.error(
                                403,
                                ResponseBody.create(
                                    "application/json".toMediaTypeOrNull(),
                                    "{\"message\":\"Unauthorized User!!! \"}"
                                )
                            );
                        }
                    }
                    if (response.isSuccessful) {
                        emit(ApiResult.Success(response.body()))
                    } else {
                        when {
                            response.code() == 401 -> emit(ApiResult.Error("UnAuthenticated User!!!"))
                            response.code() < 500 -> {
                                val responseObject = JSONObject(response.errorBody()?.string().toString())
                                when {
                                    responseObject.has("errors") -> {
                                        when (val error = responseObject.get("errors")) {
                                            is String -> emit(ApiResult.Error(error))
                                            is JSONObject -> {
                                                error.keys().forEach { key ->
                                                    emit(ApiResult.Error(error.getString(key)))
                                                }
                                            }
                                        }
                                    }
                                    responseObject.has("message") -> {
                                        emit(ApiResult.Error(responseObject.getString("message")))
                                        response.errorBody()?.close()
                                    }
                                }
                            }
                            else -> emit(ApiResult.Error("Server Error!!!"))
                        }
                    }
                } else {
                    emit(ApiResult.Error("No Internet Connection!!!"))
                }
            }.map {
                it
            }.asLiveData()
        } catch (ex: Exception) {
            flow<ApiResult<T>> {
                emit((ApiResult.Error("no internet")))
            }.asLiveData()
        }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}