package com.example.bostatechnocalassignment.network.repository

import com.example.bostatechnocalassignment.model.albums.AlbumsResponse
import retrofit2.Response


interface AlbumsRepo {

    suspend fun getUserAlbums(
        userId:Int,
    ): Response<AlbumsResponse>
}