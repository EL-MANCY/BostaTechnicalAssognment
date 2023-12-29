package com.example.bostatechnocalassignment.network.repositoryImpl

import com.example.bostatechnocalassignment.model.albums.AlbumsResponse
import com.example.bostatechnocalassignment.network.Apis
import com.example.bostatechnocalassignment.network.repository.AlbumsRepo
import retrofit2.Response
import javax.inject.Inject

class AlbumsRepoImpl @Inject constructor(private val apis: Apis) : AlbumsRepo {
    override suspend fun getUserAlbums(userId: Int): Response<AlbumsResponse> {
        return apis.getUserAlbums(userId)
    }
}