package com.example.bostatechnocalassignment.network.repository

import com.example.bostatechnocalassignment.model.photos.PhotosResponse
import retrofit2.Response


interface PhotosRepo {

    suspend fun getAlbumPhotos(
         albumId:Int,
    ): Response<PhotosResponse>
}