package com.example.bostatechnocalassignment.ui.profile

import android.app.Application
import com.example.bostatechnocalassignment.baseViewModel.BaseViewModel
import com.example.bostatechnocalassignment.network.repository.AlbumsRepo
import com.example.bostatechnocalassignment.network.repository.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersRepo: UsersRepo,
    private val albumsRepo: AlbumsRepo,
    application: Application
) : BaseViewModel(application) {

    fun getAllUsers() = handleFlowResponse {
        usersRepo.getAllUsers()
    }

    fun getUserAlbums(userId: Int) = handleFlowResponse {
        albumsRepo.getUserAlbums(userId)
    }
}