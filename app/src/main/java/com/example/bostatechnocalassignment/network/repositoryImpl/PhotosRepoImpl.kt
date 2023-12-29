package com.example.bostatechnocalassignment.network.repositoryImpl

import com.example.bostatechnocalassignment.network.repository.PhotosRepo
import com.example.bostatechnocalassignment.model.photos.PhotosResponse
import com.example.bostatechnocalassignment.network.Apis
import retrofit2.Response
import javax.inject.Inject

class PhotosRepoImpl @Inject constructor(private val apis: Apis) : PhotosRepo {
    override suspend fun getAlbumPhotos(albumId: Int): Response<PhotosResponse> {
        return apis.getPhotos(albumId)
    }
}