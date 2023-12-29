package com.example.bostatechnocalassignment.network

import com.example.bostatechnocalassignment.model.albums.AlbumsResponse
import com.example.bostatechnocalassignment.model.photos.PhotosResponse
import com.example.bostatechnocalassignment.model.users.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Apis {
    @GET("users")
    suspend fun getAllUsers(): Response<UsersResponse>

    @GET("albums")
    suspend fun getUserAlbums(
        @Query("userId") userId: Int,
    ): Response<AlbumsResponse>

    @GET("photos")
    suspend fun getPhotos(
        @Query("albumId") albumId: Int,
    ): Response<PhotosResponse>
}